/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.CuentasXCobrarJPAController;
import ferreteria_las_vegas.model.entities.Factura;
import ferreteria_las_vegas.model.entities.CuentaXCobrar;
import ferreteria_las_vegas.model.entities.Cliente;

import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.Message;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Maximino
 */
public class FXML_Buscar_CuentaXCobrar implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnConfirmar;
    @FXML
    private TextField txtFiltro;
    @FXML
    private CheckBox ckbCancelados;
    @FXML
    private TableView<CuentaXCobrar> tblCuentaxCobrar;
    @FXML
    private TableColumn<CuentaXCobrar, Cliente> colCliente;
    @FXML
    private TableColumn<CuentaXCobrar, Factura> colFactura;
    @FXML
    private TableColumn<CuentaXCobrar, Double> colSaldoAct;
    @FXML
    private TableColumn<CuentaXCobrar, Double> colSaldoFactura;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CargarDatosTabla();
    }

    @FXML
    private void btnSalir_Click(ActionEvent event) {
        Stage stageAct = (Stage) btnSalir.getScene().getWindow();
        stageAct.close();
    }

    @FXML
    private void btnConfirmar_Click(ActionEvent event) {
        if (!ckbCancelados.isSelected()) {
            if (tblCuentaxCobrar.getSelectionModel().getSelectedItem() != null) {
                AppContext.getInstance().set("seleccion-Cuenta", tblCuentaxCobrar.getSelectionModel().getSelectedItem());
                Stage stageAct = (Stage) btnConfirmar.getScene().getWindow();
                stageAct.close();
            } else {
                Message.getInstance().Warning("Cuidado:", "Debe selecionar una fila de la tabla");
            }
        }else{
           Message.getInstance().Information("Informacón:","No se pueden modificar registros cancelados");
        }
    }

    @FXML
    void ckbCancelados_Click(ActionEvent event) {
        CargarDatosTabla();

    }

    //++++++++++++++++++++++++++++++++++++++  Area de procesos en GUI ++++++++++++++++++++++++++++++++++++++++++++
    /**
     * Se encarga de cargar los datos de todas las cuentas por cobrar en la
     * tabla
     *
     */
    void CargarDatosTabla() {
        colCliente.setCellValueFactory((cellData -> new SimpleObjectProperty(cellData.getValue().getCueCliente().getPersona().getPerCedula())));
        colFactura.setCellValueFactory((cellData -> new SimpleObjectProperty(cellData.getValue().getCueFactura().getFacCodigo())));
        colSaldoAct.setCellValueFactory((cellData -> new SimpleObjectProperty<Double>(cellData.getValue().getCueSaldo())));
        colSaldoFactura.setCellValueFactory((cellData -> new SimpleObjectProperty<Double>(cellData.getValue().getCueSaldoFac())));

        List<CuentaXCobrar> List = CuentasXCobrarJPAController.getInstance().ConsultarCuentasXCobrar();
        if (!ckbCancelados.isSelected()) {
            List = List.stream().filter(x -> x.getCueEstado().equals("A")).collect(Collectors.toList());
        } else {
            List = List.stream().filter(x -> x.getCueEstado().equals("I")).collect(Collectors.toList());
        }
        ObservableList<CuentaXCobrar> CuentasList = FXCollections.observableArrayList(List);
        tblCuentaxCobrar.setItems(CuentasList);
        FiltroDatosTabla(CuentasList);
    }

    /**
     * Se encarga de filtrar la lista de cuentas por cobrar segun el dato
     * ingresado por el usuario
     *
     * @param Obs_CuentasList
     */
    void FiltroDatosTabla(ObservableList<CuentaXCobrar> Obs_CuentasList) {

        FilteredList<CuentaXCobrar> filteredData = new FilteredList<>(Obs_CuentasList, p -> true);
        txtFiltro.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredData.setPredicate((CuentaXCobrar pCXC) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (pCXC.getCueCliente().getPersona().getPerCedula().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (pCXC.getCueFactura() != null) {
                    if (pCXC.getCueFactura().getFacCodigo().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
        });
        SortedList<CuentaXCobrar> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblCuentaxCobrar.comparatorProperty());
        tblCuentaxCobrar.setItems(sortedData);
    }
}
