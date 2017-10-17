/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import ferreteria_las_vegas.model.controller.PersonaJpaController;
import ferreteria_las_vegas.model.entities.Persona;
import ferreteria_las_vegas.utils.AppContext;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private Button btnSalir;
    @FXML
    private Button btnSeleccionarCliente;
    @FXML
    private TextField txtCedualCliente;
    @FXML
    private TextField txtNombreCliente;
    @FXML
    private TableColumn<?, ?> colCedula;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colPApellido;
    @FXML
    private TableColumn<?, ?> colSApellido;
    @FXML
    private TableColumn<?, ?> colTelefono1;
    @FXML
    private TableColumn<?, ?> colTelefono2;
    @FXML
    private TableColumn<?, ?> colCorreo;
    @FXML
    private TableColumn<?, ?> colDierccion;

      @FXML
    private TableView<Persona> tblCliente;

    @FXML
    void SalirClick(ActionEvent event) {
        Stage stageAct = (Stage) btnSalir.getScene().getWindow();
        stageAct.close();
    }

    @FXML
    void SelecionarClick(ActionEvent event) {

        if (tblCliente.getSelectionModel().getSelectedItem() != null) {
            AppContext.getInstance().set("selected-persona", tblCliente.getSelectionModel().getSelectedItem());

            Stage stageAct = (Stage) btnSalir.getScene().getWindow();
            stageAct.close();
        } else {
            new Alert(Alert.AlertType.WARNING, "Debe selecionar una fila de la tabla.", ButtonType.OK).showAndWait();
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    void CargarDatosTabla() {
        TableColumn<Persona, String> colInfoPersona = new TableColumn<>("Información de Empleados");
        TableColumn<Persona, String> colCedulaPersona = new TableColumn<>("Cedula");
        TableColumn<Persona, String> colNombrePersona = new TableColumn<>("Nombre");
        TableColumn<Persona, String> colAppellidoPersona = new TableColumn<>("Apellido");
        colInfoPersona.getColumns().addAll(colCedulaPersona, colNombrePersona, colAppellidoPersona);
        tblCliente.getColumns().add(colInfoPersona);

        /*colCedulaPersona.setCellValueFactory(new PropertyValueFactory<>("perCedula"));
        colNombrePersona.setCellValueFactory(new PropertyValueFactory<>("perNombre"));
        colAppellidoPersona.setCellValueFactory(new PropertyValueFactory<>("perPApellido"));*/

        List<Persona> PersonaList = PersonaJpaController.getInstance().ConsultarPersonasTodos();
        ObservableList<Persona> OLecturaList = FXCollections.observableArrayList(PersonaList);
        tblCliente.setItems(OLecturaList);
        
        colCedulaPersona.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getPerCedula()));
        colNombrePersona.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getPerNombre()));
        colAppellidoPersona.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getPerPApellido()));        
    }

    void FiltroDatosTabla() {                      
        
        FilteredList<Persona> filteredData = new FilteredList<>(FXCollections.observableArrayList(), p -> true);
        txtCedualCliente.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredData.setPredicate((Persona persona) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (persona.getPerCedula().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (persona.getPerNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (persona.getPerSApellido().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Persona> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblCliente.comparatorProperty());
        tblCliente.setItems(sortedData);
    }
    
    
}
