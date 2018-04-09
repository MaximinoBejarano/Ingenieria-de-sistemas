/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.entities.Factura;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.Message;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Maximino
 */
public class FXML_Buscar_FacturaPendiente_Controller implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnSelecionar;
    @FXML
    private TextField txtBuscarFactura;
    @FXML
    private TableView<Factura> tblListaFacturas;
    @FXML
    private TableColumn<Factura, String> tcCliente;
    @FXML
    private TableColumn<Factura, String> tcSubtotal;
    @FXML
    private TableColumn<Factura, String> tcIVA;
    @FXML
    private TableColumn<Factura, String> tcMontoTotal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CargasDatosTabla();
    }

    @FXML
    private void btnSalirClick(ActionEvent event) {
        AppContext.getInstance().set("seleccion-Factura",null);
        Stage stageAct = (Stage) btnSalir.getScene().getWindow();
        stageAct.close();
    }

    @FXML
    private void btnSelecionarClick(ActionEvent event) {
         if (tblListaFacturas.getSelectionModel().getSelectedItem() != null) {
                AppContext.getInstance().set("seleccion-Factura", tblListaFacturas.getSelectionModel().getSelectedItem());
                Stage stageAct = (Stage) btnSalir.getScene().getWindow();
                stageAct.close();
            } else {
                Message.getInstance().Warning("Cuidado", "Debe seleccionar una fila de la tabla.");
            }
    }

    //++++++++++++++++++++++++++++++++++++++  Area de procesos en GUI ++++++++++++++++++++++++++++++++++++++++++++
    /**
     * Se encarga de cargar los datos de todas las facturas pendientes tabla
     *
     */
    public void CargasDatosTabla() {
        tcCliente.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().getFacCliente().getCliPersona())));
        tcIVA.setCellValueFactory((cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFacImpVentas()))));
        tcSubtotal.setCellValueFactory((cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFatSubtotal()))));
        tcMontoTotal.setCellValueFactory((cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFacTotal()))));

        List<Factura> List = (List<Factura>) AppContext.getInstance().get("ListPedidos");

        List = List.stream().filter(x -> x.getFacEstado().equals("A")).collect(Collectors.toList());

        ObservableList<Factura> FacturasList = FXCollections.observableArrayList(List);
        tblListaFacturas.setItems(FacturasList);
        FiltroDatosTabla(FacturasList);
    }

    /**
     * Se encarga de filtrar la lista de facturas ingresado por el usuario
     *
     * @param Obs_FacturasList
     */
    void FiltroDatosTabla(ObservableList<Factura> Obs_FacturasList) {

        FilteredList<Factura> filteredData = new FilteredList<>(Obs_FacturasList, p -> true);
        txtBuscarFactura.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredData.setPredicate((Factura pFact) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(pFact.getFacCliente().getPersona().getPerCedula().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Factura> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblListaFacturas.comparatorProperty());
        tblListaFacturas.setItems(sortedData);
    }
}
