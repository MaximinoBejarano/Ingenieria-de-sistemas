/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.CuentasXCobrarJPAController;
import ferreteria_las_vegas.model.entities.CuentaXCobrar;
import ferreteria_las_vegas.model.entities.Abono;
import ferreteria_las_vegas.utils.AppContext;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Maximino
 */
public class FXML_AbonosController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnAgregarAbono;
    @FXML
    private Button btnEditarAbono;
    @FXML
    private Button btnEliminarAbono;
    @FXML
    private ComboBox<?> cmbCliente;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Label lblNumFactura;
    @FXML
    private Label lblSaldoFact;
    @FXML
    private Label lblSaldoTotal;
    @FXML
    private DatePicker pickFecha;
    @FXML
    private TextField txtAbono;
    
    @FXML
    private TableView<Abono> tblAbonos;

    @FXML
    private TableColumn<Abono, Integer> colNumAbono;

    @FXML
    private TableColumn<Abono, Date> colFecha;

    @FXML
    private TableColumn<Abono, Double> colAbono;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnSalir_Click(ActionEvent event) {
        ScenesManager.getInstance().LoadSceneMenu();
    }

    @FXML
    private void btnAgregarAbono_Click(ActionEvent event) {
    }

    @FXML
    private void btnEditarAbono_Click(ActionEvent event) {
    }

    @FXML
    private void btnEliminarAbono_Click(ActionEvent event) {
    }

    @FXML
    private void cmbCliente_Click(ActionEvent event) {
    }

    @FXML
    private void btnFiltrar_Click(ActionEvent event) {
        LanzarBusqudad_CuentaxCobrar();
    }

    //++++++++++++++++++++++++++++++++++++++  Area de procesos en GUI ++++++++++++++++++++++++++++++++++++++++++++/
    public void CargarInformacion() {
        Limpiar_Vista();
        CuentaXCobrar Cuenta = (CuentaXCobrar) AppContext.getInstance().get("seleccion-Cuenta");
        lblNumFactura.setText(Cuenta.getCueFactura().getFacCodigo().toString());
        lblSaldoFact.setText(String.valueOf(Cuenta.getCueSaldoFac()));
        lblSaldoTotal.setText(String.valueOf(Cuenta.getCueSaldo()));
        CargarTabla(Cuenta);
    }

    public void CargarTabla( CuentaXCobrar Cuenta) {
        colAbono.setCellValueFactory((cellData -> new SimpleObjectProperty<Double>(cellData.getValue().getAboMonto())));
        colFecha.setCellValueFactory((cellData -> new SimpleObjectProperty<Date>(cellData.getValue().getAboFecha())));
        colNumAbono.setCellValueFactory((cellData -> new SimpleObjectProperty(cellData.getValue().getAboCodigo())));
       
        List<Abono> abonosList = Cuenta.getAbonoList();
        abonosList = abonosList.stream().filter(x -> x.getAboEstado().equals("A")).collect(Collectors.toList());
        ObservableList<Abono> ListAbonos = FXCollections.observableArrayList(abonosList);
        tblAbonos.setItems(ListAbonos);
    }
    
    public void Limpiar_Vista(){
      lblNumFactura.setText("");
      lblSaldoFact.setText("");
      lblSaldoTotal.setText("");
    
    }

    //++++++++++++++++++++++++++++++++++++++  Area de Otros Procesos ++++++++++++++++++++++++++++++++++++++++++++/
    public void LanzarBusqudad_CuentaxCobrar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_CuentaXCobrar.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initOwner(btnFiltrar.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
            CargarInformacion();

        } catch (Exception ex) {
            System.err.print(ex);
        }
    }
}
