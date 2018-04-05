/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.ClienteJpaController;
import ferreteria_las_vegas.model.entities.Cliente;
import ferreteria_las_vegas.utils.AppContext;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_Buscar_ClientesController implements Initializable {

    @FXML
    private Button btnSalirCliente;
    @FXML
    private Button btnSeleccionarCliente;
    @FXML
    private TextField txtCedualCliente;
    @FXML
    private TableView<Cliente> tblClientes;
    @FXML
    private TableColumn<Cliente, String> tcCedulaCliente;
    @FXML
    private TableColumn<Cliente, String> tcNombreCliente;
    @FXML
    private TableColumn<Cliente, String> tcPApellidoCliente;
    @FXML
    private TableColumn<Cliente, String> tcSApellidoCliente;
    @FXML
    private TableColumn<Cliente, String> tcTelefono1Cliente;
    @FXML
    private TableColumn<Cliente, String> tcTelefono2Cliente;
    @FXML
    private TableColumn<Cliente, String> tcCorreoElectronicoCliente;
    @FXML
    private TableColumn<Cliente, String> tcDireccionCliente;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RecargarTblClientes();

        FilteredList<Cliente> filteredData = new FilteredList<>(FXCollections.observableArrayList(new ClienteJpaController().ConsultarPersonasTodos().stream().filter(e -> e.getCliEstado().equalsIgnoreCase("A")).collect(Collectors.toList())), p -> true);

        txtCedualCliente.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredData.setPredicate((Cliente client) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (client.getPersona().getPerCedula().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (client.getPersona().getPerNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (client.getPersona().getPerPApellido().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (client.getPersona().getPerSApellido().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Cliente> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblClientes.comparatorProperty());
        tblClientes.setItems(sortedData);

    }

    @FXML
    private void CerrarPantallaBusqueda(ActionEvent event) {
        Stage stageAct = (Stage) btnSalirCliente.getScene().getWindow();
        stageAct.close();
    }

    @FXML
    private void SeleccionCliente(ActionEvent event) {
        
        if (tblClientes.getSelectionModel().getSelectedItem() != null) {
            AppContext.getInstance().set("selected-Cliente", tblClientes.getSelectionModel().getSelectedItem());

            Stage stageAct = (Stage) btnSalirCliente.getScene().getWindow();
            stageAct.close();
        } else {
            new Alert(Alert.AlertType.WARNING, "Debe selecionar una fila de la tabla.", ButtonType.OK).showAndWait();
        }

    }

    /*----------------------------------------------------------------------------------------------------*/

    private void RecargarTblClientes() {
        tblClientes.getItems().clear();
        tcCedulaCliente.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getPersona().getPerCedula()));
        tcNombreCliente.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getPersona().getPerNombre()));
        tcPApellidoCliente.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getPersona().getPerPApellido()));
        tcSApellidoCliente.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getPersona().getPerSApellido()));
        tblClientes.setItems(FXCollections.observableArrayList(new ClienteJpaController().ConsultarPersonasTodos().stream().filter(e -> e.getCliEstado().equals("A")).collect(Collectors.toList())));
    }
}
