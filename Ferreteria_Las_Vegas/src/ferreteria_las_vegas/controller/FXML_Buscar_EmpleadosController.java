
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.PersonaJpaController;
import ferreteria_las_vegas.model.entities.Persona;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import java.net.URL;
import javafx.fxml.FXML;
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
 * @author Usuario
 */
public class FXML_Buscar_EmpleadosController implements Initializable {

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnSelecionar;

    @FXML
    private TextField txtFiltro;

    @FXML
    private TableView<Persona> tblUsuarios;

    @FXML
    void SalirClick(ActionEvent event) {
        AppContext.getInstance().set("selected-persona", null);
        Stage stageAct = (Stage) btnSalir.getScene().getWindow();
        stageAct.close();
    }

    @FXML
    void SelecionarClick(ActionEvent event) {

        if (tblUsuarios.getSelectionModel().getSelectedItem() != null) {
            AppContext.getInstance().set("selected-persona", tblUsuarios.getSelectionModel().getSelectedItem());

            Stage stageAct = (Stage) btnSalir.getScene().getWindow();
            stageAct.close();
        } else {
            new Alert(Alert.AlertType.WARNING, "Debe selecionar una fila de la tabla.", ButtonType.OK).showAndWait();
        }
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CargarDatosTabla();
    }

    void CargarDatosTabla() {
        try {
            TableColumn<Persona, String> colInfoPersona = new TableColumn<>("Información de Empleados");
            TableColumn<Persona, String> colCedulaPersona = new TableColumn<>("Cedula");
            TableColumn<Persona, String> colNombrePersona = new TableColumn<>("Nombre");
            TableColumn<Persona, String> colAppellidoPersona = new TableColumn<>("Apellido");
            colInfoPersona.getColumns().addAll(colCedulaPersona, colNombrePersona, colAppellidoPersona);
            tblUsuarios.getColumns().add(colInfoPersona);

            colCedulaPersona.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getPerCedula()));
            colNombrePersona.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getPerNombre()));
            colAppellidoPersona.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getPerPApellido()));

            List<Persona> PersonaList = PersonaJpaController.getInstance().ConsultarPersonasEmpleados().stream().filter(e -> e.getPerEstado().equalsIgnoreCase("A")).collect(Collectors.toList());
            ObservableList<Persona> OLecturaList = FXCollections.observableArrayList(PersonaList);
            tblUsuarios.setItems(OLecturaList);

            FiltroDatosTabla(OLecturaList);
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo cargar la información de la tabla. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void FiltroDatosTabla(ObservableList<Persona> OLecturaList) {
        FilteredList<Persona> filteredData = new FilteredList<>(OLecturaList, p -> true);
        txtFiltro.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
        sortedData.comparatorProperty().bind(tblUsuarios.comparatorProperty());
        tblUsuarios.setItems(sortedData);
    }
}
