/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.ProveedorJpaController;
import ferreteria_las_vegas.model.entities.Proveedor;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.LoggerManager;
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
 * @author johan
 */
public class FXML_Buscar_ProveedoresController implements Initializable {

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnSelecionar;

    @FXML
    private TextField txtFiltro;

    @FXML
    private TableView<Proveedor> tblProveedores;

    /*--------------------------------------------------------------------------------------------------------------*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CargarTablaProveedores();
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    @FXML
    void btnSalirClick(ActionEvent event) {
        AppContext.getInstance().set("selected-proveedor", null);
        Stage stageAct = (Stage) btnSalir.getScene().getWindow();
        stageAct.close();
    }

    @FXML
    void btnSelecionarClick(ActionEvent event) {
        if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
            AppContext.getInstance().set("selected-proveedor", tblProveedores.getSelectionModel().getSelectedItem());

            Stage stageAct = (Stage) btnSalir.getScene().getWindow();
            stageAct.close();
        } else {
            Message.getInstance().Warning("Aviso", "Debe seleccionar una fila de la tabla.");
        }
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    void CargarTablaProveedores() {
        try {
            TableColumn<Proveedor, String> colInfoProveedor = new TableColumn<>("Información de Proveedores");
            TableColumn<Proveedor, String> colCedulaJuriducaProveedor = new TableColumn<>("Cédula Jurídica");
            TableColumn<Proveedor, String> colNombreProveedor = new TableColumn<>("Nombre");
            colInfoProveedor.getColumns().addAll(colCedulaJuriducaProveedor, colNombreProveedor);
            tblProveedores.getColumns().add(colInfoProveedor);

            colCedulaJuriducaProveedor.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getProCedulaJuridica()));
            colNombreProveedor.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getProNombre()));

            List<Proveedor> ProveedorList = ProveedorJpaController.getInstance().ConsultarProveedoresTodos().stream().filter(e -> e.getProEstado().equalsIgnoreCase("A")).collect(Collectors.toList());
            ObservableList<Proveedor> OProveedorList = FXCollections.observableArrayList(ProveedorList);
            tblProveedores.setItems(OProveedorList);

            FiltroDatosTabla(OProveedorList);
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo cargar la información de la tabla.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    void FiltroDatosTabla(ObservableList<Proveedor> OList) {
        FilteredList<Proveedor> filteredData = new FilteredList<>(OList, p -> true);
        txtFiltro.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredData.setPredicate((Proveedor proveedor) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (proveedor.getProCedulaJuridica().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (proveedor.getProNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Proveedor> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblProveedores.comparatorProperty());
        tblProveedores.setItems(sortedData);
    }
}
