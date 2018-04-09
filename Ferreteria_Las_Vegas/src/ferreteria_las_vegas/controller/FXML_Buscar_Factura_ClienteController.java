/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.FacturaJPAController;
import ferreteria_las_vegas.model.entities.Compra;
import ferreteria_las_vegas.model.entities.Factura;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
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
 * @author sanwi
 */
public class FXML_Buscar_Factura_ClienteController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private TextField txtBuscarFactura;
    @FXML
    private Button btnSelecionar;
    @FXML
    private TableView<Factura> tblListaFacturas;
    @FXML
    private TableColumn<Factura, String> tcNumFactura;
    @FXML
    private TableColumn<Factura, String> tcCliente;
    @FXML
    private TableColumn<Factura, String> tcTipoFactura;
    @FXML
    private TableColumn<Factura, String> tcFechaCompra;
    @FXML
    private TableColumn<Factura, String> tcMontoTotal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarTabla();
    }

    @FXML
    private void btnSalirClick(ActionEvent event) {
        try {
            Stage stageAct = (Stage) btnSalir.getScene().getWindow();
            stageAct.close();
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo Cerrar la pantalla. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    private void btnSelecionarClick(ActionEvent event) {
        try {
            if (tblListaFacturas.getSelectionModel().getSelectedItem() != null) {
                AppContext.getInstance().set("seleccion-FacCliente", tblListaFacturas.getSelectionModel().getSelectedItem());
                Stage stageAct = (Stage) btnSelecionar.getScene().getWindow();
                stageAct.close();
            } else {
                Message.getInstance().Warning("Cuidado:", "Debe selecionar una fila de la tabla");
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo seleccionar un registro de la tabla. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    public void CargarTabla() {

        tcNumFactura.setCellValueFactory((cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFacCodigo()))));
        tcCliente.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().getFacCliente().getPersona().getPerNombre() + " "
                + cellData.getValue().getFacCliente().getPersona().getPerPApellido() + " "
                + cellData.getValue().getFacCliente().getPersona().getPerSApellido())));
        tcFechaCompra.setCellValueFactory((cellData -> new SimpleStringProperty(formateador.format(cellData.getValue().getFacFecha()))));
        tcTipoFactura.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().getFactTipoFact().replace("E", "Cóntado").replace("K", "Crédito"))));

        tcMontoTotal.setCellValueFactory((cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFacTotal()))));

        List<Factura> pListCompras = new FacturaJPAController().ConsultarFacturas().stream().filter(x -> x.getFacEstado().equals("A")).collect(Collectors.toList());
        ObservableList<Factura> pCompras = FXCollections.observableArrayList(pListCompras);

        tblListaFacturas.setItems(pCompras);
        FiltroDatosTabla(pCompras);
    }

    private void FiltroDatosTabla(ObservableList<Factura> pCompras) {

        FilteredList<Factura> filteredData = new FilteredList<>(pCompras, p -> true);
        txtBuscarFactura.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredData.setPredicate((Factura pCompra) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(pCompra.getFacCodigo()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (pCompra.getFacCliente().getPersona().getPerNombre() != null && !pCompra.getFacCliente().getPersona().getPerNombre().equals("")) {
                    if (pCompra.getFacCliente().getPersona().getPerNombre().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                } else if (pCompra.getFacCliente().getPersona().getPerPApellido() != null && !pCompra.getFacCliente().getPersona().getPerPApellido().equals("")) {
                    if (pCompra.getFacCliente().getPersona().getPerPApellido().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                } else if (pCompra.getFacCliente().getPersona().getPerSApellido() != null && !pCompra.getFacCliente().getPersona().getPerSApellido().equals("")) {
                    if (pCompra.getFacCliente().getPersona().getPerSApellido().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                } else if (pCompra.getFactTipoFact() != null && !pCompra.getFactTipoFact().equals("")) {
                    if (pCompra.getFactTipoFact().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
        });
        SortedList<Factura> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblListaFacturas.comparatorProperty());
        tblListaFacturas.setItems(sortedData);
    }

    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yy");
}
