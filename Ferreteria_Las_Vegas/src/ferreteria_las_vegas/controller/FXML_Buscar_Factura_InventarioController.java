/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.ArticuloJpaController;
import ferreteria_las_vegas.model.controller.CompraJpaController;
import ferreteria_las_vegas.model.entities.Articulo;
import ferreteria_las_vegas.model.entities.Compra;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sanwi
 */
public class FXML_Buscar_Factura_InventarioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarTabla();
    }
    @FXML
    private TextField txtBusquedaFactura;

    @FXML
    private TableView<Compra> tblClientes;

    @FXML
    private TableColumn<Compra, String> tcNumFactura;

    @FXML
    private TableColumn<Compra, String> tcNombreVendedorProv;

    @FXML
    private TableColumn<Compra, String> tcFecha;

    @FXML
    private TableColumn<Compra, String> tcSubTotal;

    @FXML
    private TableColumn<Compra, String> tcTotal;

    @FXML
    private Button btnSalirCliente;

    @FXML
    private Button btnSeleccionarFacturaProv;

    @FXML
    void CerrarPantallaBusquedaFacturas(ActionEvent event) {
        try {
            AppContext.getInstance().set("seleccion-FacInventario", null);
            Stage stageAct = (Stage) btnSalirCliente.getScene().getWindow();
            stageAct.close();
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo cerrar la pantalla. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    void SeleccionFactura(ActionEvent event) {
        try {
            if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                AppContext.getInstance().set("seleccion-FacInventario", tblClientes.getSelectionModel().getSelectedItem());
                Stage stageAct = (Stage) btnSeleccionarFacturaProv.getScene().getWindow();
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

        tcNumFactura.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().getComNumeroFact())));
        tcNombreVendedorProv.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().getComProveedor().getProNombre())));
        tcFecha.setCellValueFactory((cellData -> new SimpleStringProperty(formateador.format(cellData.getValue().getComFecha()))));
        tcSubTotal.setCellValueFactory((cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getComSubTotal()))));
        tcTotal.setCellValueFactory((cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getComTotal()))));

        List<Compra> pListCompras = new CompraJpaController().ConsultarCompras().stream().filter(x -> x.getComEstado().equals("A")).collect(Collectors.toList());
        ObservableList<Compra> pCompras = FXCollections.observableArrayList(pListCompras);
        tblClientes.setItems(pCompras);
        FiltroDatosTabla(pCompras);
    }

    void FiltroDatosTabla(ObservableList<Compra> OLecturaList) {

        FilteredList<Compra> filteredData = new FilteredList<>(OLecturaList, p -> true);
        txtBusquedaFactura.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredData.setPredicate((Compra pCompra) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (pCompra.getComNumeroFact().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (pCompra.getComProveedor().getProNombre() != null && !pCompra.getComProveedor().getProNombre().equals("")) {
                    if (pCompra.getComProveedor().getProNombre().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                } else if (formateador.format(pCompra.getComFecha()) != null && !formateador.format(pCompra.getComFecha()).equals("")) {
                    if (formateador.format(pCompra.getComFecha()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
        });
        SortedList<Compra> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblClientes.comparatorProperty());
        tblClientes.setItems(sortedData);
    }
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yy");

}
