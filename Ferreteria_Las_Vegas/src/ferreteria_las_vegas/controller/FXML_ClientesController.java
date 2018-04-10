
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.ClienteJpaController;
import ferreteria_las_vegas.model.controller.PersonaJpaController;
import ferreteria_las_vegas.model.entities.Cliente;
import ferreteria_las_vegas.model.entities.Contacto;
import ferreteria_las_vegas.model.entities.Direccion;
import ferreteria_las_vegas.model.entities.Persona;
import ferreteria_las_vegas.model.entities.Usuario;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.GeneralUtils;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private Button btnEliminarCliente;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void SalirClick(ActionEvent event) {
        try {
            ScenesManager.getInstance().LoadSceneMenu();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo volver a la pantalla de menú.");
            LoggerManager.Logger().info(ex.toString());
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
        try {
            LimpiarControles();
            LanzarBusqueda();
            Cliente cliente = (Cliente) AppContext.getInstance().get("selected-Cliente");
            if (cliente != null) {
                CargarDatosUsuario(cliente);
                btnEditarClientes.setDisable(false);
                btnEliminarCliente.setDisable(false);
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo agregar la factura de Inventario. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }

    }

    @FXML
    void TxtDireccionClienteTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, TxtDireccionCliente.getText().length(), 200, event);
    }

    @FXML
    void txtCedClienteTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtCedCliente.getText().length(), 30, event);
    }

    @FXML
    void txtCorreoClienteTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtCorreoCliente.getText().length(), 30, event);
    }

    @FXML
    void txtNombreClienteTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtNombreCliente.getText().length(), 30, event);
    }

    @FXML
    void txtPApellidoClienteTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtPApellidoCliente.getText().length(), 30, event);
    }

    @FXML
    void txtSApellidoClienteTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtSApellidoCliente.getText().length(), 30, event);
    }

    @FXML
    void txtTelefono1ClienteTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtTelefono1Cliente.getText().length(), 20, event);
    }

    @FXML
    void txtTelefono2ClienteTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtTelefono2Cliente.getText().length(), 20, event);
    }

    @FXML
    private void EditarCliente(ActionEvent event) {
        try {
            Cliente cliente = (Cliente) AppContext.getInstance().get("selected-Cliente");
            Persona persona = PersonaJpaController.getInstance().ConsultarPersonaCedula(txtCedCliente.getText());
            if (persona != null) {

                persona.setPerNombre(txtNombreCliente.getText());
                persona.setPerPApellido(txtPApellidoCliente.getText());
                persona.setPerSApellido(txtSApellidoCliente.getText());

                Contacto contactoTel = BuscarContactoTipo(persona, "TEL");
                Contacto contactoTel2 = BuscarContactoTipo(persona, "TEL2");
                Contacto contactoEma = BuscarContactoTipo(persona, "EMAIL");

                Direccion direcion = persona.getDireccionList().get(0);

                contactoTel.setConContacto(txtTelefono1Cliente.getText());

                if (contactoTel2 != null) {
                    contactoTel2.setConContacto(txtTelefono2Cliente.getText());
                }

                if (contactoEma != null) {
                    contactoEma.setConContacto(txtCorreoCliente.getText());
                }
                direcion.setDirDirExacta(TxtDireccionCliente.getText());
                persona = PersonaJpaController.getInstance().ModificarPersona(persona);
                AppContext.getInstance().delete("selected-Cliente");
                LimpiarControles();
                if (persona != null) {
                    new Alert(Alert.AlertType.INFORMATION, "Cliente editado corectamente.", ButtonType.OK).showAndWait();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Ocurrio un error y no se pudo no editar el Cliente.", ButtonType.OK).showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "No existe un Cliente con la cedula ingresada.", ButtonType.OK).showAndWait();
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo agregar la factura de Inventario. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    Contacto BuscarContactoTipo(Persona pPersona, String Tipo) {
        for (Contacto con : pPersona.getContactoList()) {
            if (con.getConTipoContacto().equalsIgnoreCase(Tipo)) {
                return con;
            }
        }
        return null;
    }

    @FXML
    private void EliminarCliente(ActionEvent event) {
        try {
            Cliente cliente = (Cliente) AppContext.getInstance().get("selected-Cliente");
            Persona persona = PersonaJpaController.getInstance().ConsultarPersonaCedula(txtCedCliente.getText());
            if (persona != null) {

                persona.setPerEstado("I");
                persona = PersonaJpaController.getInstance().ModificarPersona(persona);
                cliente = new ClienteJpaController().ModificarCliente(new Cliente(txtCedCliente.getText(), new java.sql.Date(new java.util.Date().getTime()), "I"));
                AppContext.getInstance().delete("selected-Cliente");
                LimpiarControles();
                if (persona != null) {
                    new Alert(Alert.AlertType.INFORMATION, "Cliente fue eliminado corectamente.", ButtonType.OK).showAndWait();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Ocurrio un error y no se pudo eliminar el Cliente.", ButtonType.OK).showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "No existe un Cliente con la cedula ingresada.", ButtonType.OK).showAndWait();
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo agregar la factura de Inventario. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    private void LimpiarCamposClick(ActionEvent event) {
        LimpiarControles();
    }

    public void ProcesoAgregar() {
        try {
            Persona persona = PersonaJpaController.getInstance().ConsultarPersonaCedula(txtCedCliente.getText());
            if (persona == null) {
                persona = new Persona(txtCedCliente.getText(), txtNombreCliente.getText(), txtPApellidoCliente.getText(), "A");
                persona.setPerSApellido(txtSApellidoCliente.getText());

                Usuario user = (Usuario) AppContext.getInstance().get("user");
                persona.setPerFerreteria(user.getPersona().getPerFerreteria());

                Contacto contactoTel1 = new Contacto(Integer.SIZE, txtTelefono1Cliente.getText(), "TEL");
                Contacto contactoTel2 = null;
                if (!txtTelefono2Cliente.getText().isEmpty()) {
                    contactoTel2 = new Contacto(Integer.SIZE, txtTelefono2Cliente.getText(), "TEL2");
                } else {
                    contactoTel2 = new Contacto(Integer.SIZE, "N/A", "TEL2");
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
            } else {
                Message.getInstance().Warning("Cliente Existente", "Ya existe un cliente registrado con el mismo numero de cedula");
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo agregar la factura de Inventario. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    public void CargarDatosUsuario(Cliente persona) {
        txtCedCliente.setText(persona.getPersona().getPerCedula());
        txtNombreCliente.setText(persona.getPersona().getPerNombre());
        txtPApellidoCliente.setText(persona.getPersona().getPerPApellido());
        txtSApellidoCliente.setText(persona.getPersona().getPerSApellido());
        txtTelefono1Cliente.setText(persona.getPersona().getContactoList().get(0).getConContacto());
        txtTelefono2Cliente.setText(persona.getPersona().getContactoList().get(1).getConContacto());
        txtCorreoCliente.setText(persona.getPersona().getContactoList().get(2).getConContacto());
        TxtDireccionCliente.setText(persona.getPersona().getDireccionList().get(0).getDirDirExacta());
    }

    public void LanzarBusqueda() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Clientes.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initOwner(btnBuscarCliente.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo agregar la factura de Inventario. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
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
        btnEditarClientes.setDisable(true);
        btnEliminarCliente.setDisable(true);
    }

}
