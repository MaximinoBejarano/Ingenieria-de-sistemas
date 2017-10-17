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
import java.util.Date;
import javafx.fxml.FXML;
import java.util.List;
import java.util.ResourceBundle;
import static javafx.application.ConditionalFeature.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author wili
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CargarDatosTabla();
    }

    void CargarDatosTabla() {
        TableColumn<Persona, String> colInfoPersona = new TableColumn<>("Información de Empleados");
        TableColumn<Persona, String> colCedulaPersona = new TableColumn<>("Cedula");
        TableColumn<Persona, String> colNombrePersona = new TableColumn<>("Nombre");
        TableColumn<Persona, String> colAppellidoPersona = new TableColumn<>("Apellido");
        colInfoPersona.getColumns().addAll(colCedulaPersona, colNombrePersona, colAppellidoPersona);
        tblUsuarios.getColumns().add(colInfoPersona);

        colCedulaPersona.setCellValueFactory(new PropertyValueFactory<>("perCedula"));
        colNombrePersona.setCellValueFactory(new PropertyValueFactory<>("perNombre"));
        colAppellidoPersona.setCellValueFactory(new PropertyValueFactory<>("perPApellido"));

        List<Persona> PersonaList = PersonaJpaController.getInstance().ConsultarPersonasTodos();
        ObservableList<Persona> OLecturaList = FXCollections.observableArrayList(PersonaList);
        tblUsuarios.setItems(OLecturaList);
    }
}
