/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author MaxBejarano
 */
public class FXML_InventarioController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnAgregarFactura;
    @FXML
    private Button btnBuscarFactura;
    @FXML
    private Button btnEditarFactura;
    @FXML
    private Button btnEliminar;
    @FXML
    private TextField txtCedCliente;
    @FXML
    private TextField txtNumFactura;
    @FXML
    private TextField txtVnombre;
    @FXML
    private DatePicker pickFeccha;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnNuevoProducto;
    @FXML
    private Button btnEditar;
    @FXML
    private VBox DataPanel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void FinalizarProceso(ActionEvent event) {
        try {
            setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Menu.fxml"));
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    private void AgregarProducto(ActionEvent event) {
        try {
            if (true) {
                CargarProductos();
                
            } else {
                //rellenar los datos del formulario
            }
        } catch (Exception ex) {
            // mandar al servidor al log de errores
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    public void CargarProductos(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Productos.fxml"));
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnNuevoProducto.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXML_InventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDataPane(Node node) {
        DataPanel.getChildren().setAll(node);
    }

    public VBox cargarScena(String url) throws IOException {
        VBox v = (VBox) FXMLLoader.load(getClass().getResource(url));
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(v);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

        return v;
    }

}
