
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import ferreteria_las_vegas.model.controller.ClienteJpaController;
import ferreteria_las_vegas.model.controller.ContactoJpaController;
import ferreteria_las_vegas.model.controller.PersonaJpaController;
import ferreteria_las_vegas.model.entities.Cliente;
import ferreteria_las_vegas.model.entities.Contacto;
import ferreteria_las_vegas.model.entities.Direccion;
import ferreteria_las_vegas.model.entities.Persona;
import ferreteria_las_vegas.utils.AppContext;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_ClientesController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnAgregarCliente;
    @FXML
    private Button btnBuscarCliente;
    @FXML
    private Button btnEditarClientes;
    @FXML
    private Button EliminarCliente;
    @FXML
    private TextField txtCedCliente;
    @FXML
    private TextField txtNombreCliente;
    @FXML
    private TextField txtPApellidoCliente;
    @FXML
    private TextField txtSApellidoCliente;
    @FXML
    private TextField txtTelefono1Cliente;
    @FXML
    private TextField txtTelefono2Cliente;
    @FXML
    private TextField txtCorreoCliente;
    @FXML
    private TextArea TxtDireccionCliente;
    @FXML
    private VBox dataPane;

    public void setDataPane(Node node) {
        dataPane.getChildren().setAll(node);
    }

    public VBox cargarScena(String url) throws IOException {
        VBox v = (VBox) FXMLLoader.load(getClass().getResource(url));
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(v);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

        return v;
    }
    void LanzarBusqueda() {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Clientes.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage(StageStyle.UTILITY);
                stage.initOwner(btnBuscarCliente.getScene().getWindow());
                stage.setScene(new Scene(root));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.showAndWait();


            } catch (Exception ex) {
                // mandar al servidor al log de errores
                System.err.println(ex);
            }
        }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void RegresarMenu(ActionEvent event) {
        try {
            setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Menu.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXML_ClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void AgregarCliente(ActionEvent event) {
        if (txtCedCliente.getText().isEmpty() || txtNombreCliente.getText().isEmpty() || txtPApellidoCliente.getText().isEmpty()
                || txtSApellidoCliente.getText().isEmpty() || txtTelefono1Cliente.getText().isEmpty() || TxtDireccionCliente.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Advertencia: Debe completar todos los campos requeridos.", ButtonType.OK).showAndWait();
        } else {
            ProcesoAgregar();
        }
    }

    
    @FXML
    private void buscarCliente(ActionEvent event) {
        Cliente cliente =null;
        LanzarBusqueda();
        cliente = (Cliente) AppContext.getInstance().get("selected-Cliente");
        if (cliente != null) {
            CargarDatosUsuario(cliente);
            btnEditarClientes.setDisable(false);
        }

    }

    
    
    void ProcesoAgregar() {
        Persona persona = new Persona(txtCedCliente.getText(), txtNombreCliente.getText(), txtPApellidoCliente.getText(),"A");
        persona.setPerSApellido(txtSApellidoCliente.getText());

        Contacto contactoTel1 = new Contacto(Integer.SIZE, txtTelefono1Cliente.getText(), "TEL");
        Contacto contactoTel2 = null;
        if (!txtTelefono2Cliente.getText().isEmpty()) {
            contactoTel2 = new Contacto(Integer.SIZE, txtTelefono2Cliente.getText(), "TEL");
        } else {
            contactoTel2 = new Contacto(Integer.SIZE, "N/A", "TEL");
        }
        Contacto contactoEma = null;
        if (!txtCorreoCliente.getText().isEmpty()) {
            contactoEma = new Contacto(Integer.SIZE, txtCorreoCliente.getText(), "EMAIL");
        } else {
            contactoEma = new Contacto(Integer.SIZE, "N/A", "EMAIL");
        }
        Direccion direcion = new Direccion(Integer.SIZE, TxtDireccionCliente.getText());

        persona = PersonaJpaController.getInstance().AgregarPersona(persona, direcion, contactoTel1, contactoTel2, contactoEma);
        Cliente cliente;
        cliente = new ClienteJpaController().AgregarCliente(new Cliente(persona.getPerCedula(), new java.sql.Date(new java.util.Date().getTime()), "A"));

        if (persona != null && cliente != null) {
            new Alert(Alert.AlertType.INFORMATION, "Cliente Agregado Correctamente.", ButtonType.OK).showAndWait();
            LimpiarControles();
        } else {
            new Alert(Alert.AlertType.ERROR, "No se pudo Modificar el Cliente.", ButtonType.OK).showAndWait();
        }
    }

    void CargarDatosUsuario(Cliente persona) {
        txtCedCliente.setText(persona.getPersona().getPerCedula());
        txtNombreCliente.setText(persona.getPersona().getPerNombre());
        txtPApellidoCliente.setText(persona.getPersona().getPerPApellido());
        txtSApellidoCliente.setText(persona.getPersona().getPerSApellido());
        txtTelefono1Cliente.setText(persona.getPersona().getContactoList().get(0).getConContacto());
        txtTelefono2Cliente.setText(persona.getPersona().getContactoList().get(1).getConContacto());
        txtCorreoCliente.setText(persona.getPersona().getContactoList().get(2).getConContacto());
        TxtDireccionCliente.setText(persona.getPersona().getDireccionList().get(0).getDirDirExacta());
    }

    

    @FXML
    private void EditarCliente(ActionEvent event) {
        Cliente cliente = (Cliente) AppContext.getInstance().get("selected-Cliente");
        Persona persona = new Persona(txtCedCliente.getText(), txtNombreCliente.getText(), txtPApellidoCliente.getText(),"A");
        persona.setPerSApellido(txtSApellidoCliente.getText());

        
        Contacto contactoTel1 = new Contacto(cliente.getPersona().getContactoList().get(0).getConID(), txtTelefono1Cliente.getText(), "TEL");
        Contacto contactoTel2 = null;
        if (!txtTelefono2Cliente.getText().isEmpty()) {
            contactoTel2 = new Contacto(cliente.getPersona().getContactoList().get(1).getConID(), txtTelefono2Cliente.getText(), "TEL");
        } 
        Contacto contactoEma = null;
        if (!txtCorreoCliente.getText().isEmpty()) {
            contactoEma = new Contacto(cliente.getPersona().getContactoList().get(2).getConID(), txtCorreoCliente.getText(), "EMAIL");
        } 
        
        Direccion direcion = new Direccion(cliente.getPersona().getDireccionList().get(0).getDirID(), TxtDireccionCliente.getText());
        contactoTel1= new ContactoJpaController().ModificarContactoPersona(cliente.getPersona(), contactoTel1);
        contactoTel2= new ContactoJpaController().ModificarContactoPersona(cliente.getPersona(), contactoTel2);
//        persona.getContactoList().add(contactoTel1);
//        persona.getContactoList().add(contactoTel1);
////        persona.getContactoList().add(contactoEma);
//        persona = PersonaJpaController.getInstance().ModificarPersona(persona);
//        cliente= new Cliente(persona.getPerCedula(),new java.sql.Date(new java.util.Date().getTime()), "A");
//        cliente = new ClienteJpaController().ModificarCliente(cliente);

        if (persona != null && cliente != null) {
            new Alert(Alert.AlertType.INFORMATION, "Cliente se Modifico Correctamente.", ButtonType.OK).showAndWait();
            LimpiarControles();
        } else {
            new Alert(Alert.AlertType.ERROR, "No se pudo Agregar el Cliente.", ButtonType.OK).showAndWait();
        }

    }

    @FXML
    private void EliminarCliente(ActionEvent event) {
    }

    private void LimpiarControles() {
        txtCedCliente.setText("");
        txtNombreCliente.setText("");
        txtPApellidoCliente.setText("");
        txtSApellidoCliente.setText("");
        txtTelefono1Cliente.setText("");
        txtTelefono2Cliente.setText("");
        TxtDireccionCliente.setText("");
        txtCorreoCliente.setText("");
    }

}

