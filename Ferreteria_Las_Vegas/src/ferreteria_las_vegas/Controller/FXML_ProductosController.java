/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import ferreteria_las_vegas.model.controller.ArticuloJpaController;
import ferreteria_las_vegas.model.entities.Articulo;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author MaxBejarano
 */
public class FXML_ProductosController implements Initializable {

    @FXML
    private Button btnEliminarProducto;
    @FXML
    private Button btnEditarProducto;
    @FXML
    private Button btnBuscarProducto;
    @FXML
    private Button btnAgregarProducto;
    @FXML
    private TextField txtCodigoProducto;
    @FXML
    private TextField txtCodBarras;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtUndMedida;
    @FXML
    private TextField txtDescripcion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void EliminarProductos(ActionEvent event) {
    }

    @FXML
    private void EditarProductos(ActionEvent event) {
    }

    @FXML
    private void BusquedadProductos(ActionEvent event) {
      ProcesoBusquedad();
    }

    @FXML
    private void AgregarProducto(ActionEvent e) {
        if (txtCodigoProducto.getText().isEmpty() || txtNombre.getText().isEmpty()
                || txtDescripcion.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Advertencia: Es necesario completar los campos requeridos", ButtonType.OK).showAndWait();
        } else {
            GuardarProducto();
        }
    }

    private void GuardarProducto() {
        BigDecimal Precio = new BigDecimal(txtPrecio.getText());
        Articulo articulo = new Articulo(Integer.SIZE, txtNombre.getText(), txtDescripcion.getText(), txtMarca.getText(), txtUndMedida.getText(), Precio, "A");        
        articulo = ArticuloJpaController.getInstance().InsertarArticulo(articulo);

        if (articulo != null) {
            new Alert(Alert.AlertType.INFORMATION, "Información: Se han ingresado los datos de forma exitosa ", ButtonType.OK).showAndWait();
            LimpiarCampos();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error: No se han guardado los datos", ButtonType.OK).showAndWait();
        }
    }

    public void LimpiarCampos() {
        txtCodigoProducto.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtCodBarras.setText("");
        txtMarca.setText("");
        txtUndMedida.setText("");
        txtPrecio.setText("");
    }
    void ProcesoBusquedad(){
    
    LanzarBusqueda();
    
    }
    void LanzarBusqueda() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Productos.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initOwner(btnBuscarProducto.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

        } catch (Exception ex) {
            // mandar al servidor al log de errores
            System.out.println(ex);
        }
    }
}
