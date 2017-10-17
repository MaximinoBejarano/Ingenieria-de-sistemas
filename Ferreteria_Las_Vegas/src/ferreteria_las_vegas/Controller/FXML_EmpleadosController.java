/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import ferreteria_las_vegas.model.controller.DireccionJpaController;
import ferreteria_las_vegas.model.controller.PersonaJpaController;
import ferreteria_las_vegas.model.entities.Contacto;
import ferreteria_las_vegas.model.entities.Direccion;
import ferreteria_las_vegas.model.entities.Persona;
import ferreteria_las_vegas.model.entities.Usuario;
import ferreteria_las_vegas.utils.AppContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class FXML_EmpleadosController {

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnAgregar;

    @FXML
    private TextField txtCedulaEmp;

    @FXML
    private TextField txtNombreEmp;

    @FXML
    private TextField txtPrimerAEmp;

    @FXML
    private TextField txtSegundoAEmp;

    @FXML
    private TextField txtCorreoEmp;

    @FXML
    private TextField txtTelefonoEmp;

    @FXML
    private PasswordField txtContraseñaEmp;

    @FXML
    private TextArea txtDireccionEmp;

    @FXML
    void AgregarEmpleadoClick(ActionEvent event) {
        if (txtCedulaEmp.getText().isEmpty() || txtNombreEmp.getText().isEmpty() || txtPrimerAEmp.getText().isEmpty()
                || txtCorreoEmp.getText().isEmpty() || txtTelefonoEmp.getText().isEmpty()
                || txtContraseñaEmp.getText().isEmpty() || txtDireccionEmp.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Advertencia: Debe completar todos los campos requeridos.", ButtonType.OK).showAndWait();
        } else {
            ProcesoAgregar();
        }
    }

    @FXML
    void BuscarEmpleadosClick(ActionEvent event) {
        ProcesoBuscar();
    }

    @FXML
    void EditarEmpleadoClick(ActionEvent event) {
        
    }

    @FXML
    void EliminarEmpleadoClicl(ActionEvent event) {

    }

    @FXML
    void SalirClick(ActionEvent event) {

        try {
            /*Parent root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Menu.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.resizableProperty().set(true);
                stage.setMaximized(true);                
                stage.show();*/

            Stage stage = (Stage) btnSalir.getScene().getWindow();
            stage.close();

        } catch (Exception ex) {
            // mandar al servidor al log de errores
        }

    }

    /*-----------------------------------------------------------------------------*/
    void ProcesoAgregar() {
        Persona persona = new Persona(txtCedulaEmp.getText(), txtNombreEmp.getText(), txtPrimerAEmp.getText());
        persona.setPerSApellido(txtSegundoAEmp.getText());
        Usuario usuario = new Usuario(persona.getPerCedula(), persona.getPerCedula(), String.valueOf(txtContraseñaEmp.getText()));
        Contacto contactoTel = new Contacto(Integer.SIZE, txtTelefonoEmp.getText(), "TEL");
        Contacto contactoEma = new Contacto(Integer.SIZE, txtCorreoEmp.getText(), "EMAIL");
        Direccion direcion = new Direccion(Integer.SIZE, txtDireccionEmp.getText());
        persona.setUsuario(usuario);

        persona = PersonaJpaController.getInstance().AgregarPersona(persona, direcion, contactoTel, contactoEma);
        if (persona != null) {
            new Alert(Alert.AlertType.INFORMATION, "Empleado Agregado Corectamente.", ButtonType.OK).showAndWait();
            LimpiarControles();
        } else {
            new Alert(Alert.AlertType.ERROR, "No se pudo Agregar al Empleado.", ButtonType.OK).showAndWait();
        }
    }

    void ProcesoEditar() {

    }

    void ProcesoBuscar() {
        LanzarBusqueda();
        Persona persona = (Persona) AppContext.getInstance().get("selected-persona");
        
        if(persona!=null){
             new Alert(Alert.AlertType.INFORMATION, "Editando", ButtonType.OK).showAndWait();
        }
    }

    void ProcesoEliminar() {

    }

    /*-----------------------------------------------------------------------------*/
    void LimpiarControles() {
        txtCedulaEmp.setText("");
        txtNombreEmp.setText("");
        txtPrimerAEmp.setText("");
        txtSegundoAEmp.setText("");
        txtTelefonoEmp.setText("");
        txtCorreoEmp.setText("");
        txtContraseñaEmp.setText("");
        txtDireccionEmp.setText("");
    }
    void ControlesModoEditar(){
        
    }

    /*-----------------------------------------------------------------------------*/
    void LanzarBusqueda() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Empleados.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initOwner(btnBuscar.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

        } catch (Exception ex) {
            // mandar al servidor al log de errores
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
}
