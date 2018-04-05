/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import java.net.URL;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;
import java.time.LocalDate;
import javafx.stage.Modality;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TabPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ferreteria_las_vegas.utils.Message;
import javafx.scene.control.PasswordField;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.GeneralUtils;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.model.entities.Cliente;
import ferreteria_las_vegas.model.entities.Permiso;
import ferreteria_las_vegas.model.entities.Persona;
import ferreteria_las_vegas.model.entities.Usuario;
import ferreteria_las_vegas.model.entities.Contacto;
import ferreteria_las_vegas.model.entities.Direccion;
import ferreteria_las_vegas.model.controller.PermisoJpaController;
import ferreteria_las_vegas.model.controller.PersonaJpaController;
import ferreteria_las_vegas.model.controller.UsuarioJpaController;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class FXML_EmpleadosController implements Initializable {

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
    private Button btnLimpiar;

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
    private TextField txtTelefonoEmp2;

    @FXML
    private PasswordField txtContraseñaEmp;

    @FXML
    private TextArea txtDireccionEmp;

    @FXML
    private ListView<Permiso> lvDisponibles;

    @FXML
    private ListView<Permiso> lvAsignados;

    @FXML
    private Label lblUsuario;

    @FXML
    private TabPane tabPane;

    /*--------------------------------------------------------------------------------------------------------------*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lvDisponibles.setCellFactory(param -> new ListCell<Permiso>() {
            @Override
            protected void updateItem(Permiso item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getPerDescripcion() == null) {
                    setText(null);
                } else {
                    setText(item.getPerDescripcion());
                }
            }
        });

        lvAsignados.setCellFactory(param -> new ListCell<Permiso>() {
            @Override
            protected void updateItem(Permiso item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getPerDescripcion() == null) {
                    setText(null);
                } else {
                    setText(item.getPerDescripcion());
                }
            }
        });
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    @FXML
    void btnAgregarClick(ActionEvent event) {
        if (txtCedulaEmp.getText().isEmpty() || txtNombreEmp.getText().isEmpty() || txtPrimerAEmp.getText().isEmpty()
                || txtSegundoAEmp.getText().isEmpty()
                || txtTelefonoEmp.getText().isEmpty()
                || txtContraseñaEmp.getText().isEmpty() || txtDireccionEmp.getText().isEmpty()) {
            Message.getInstance().Warning("Información requerida", "Debe completar todos los campos requeridos.");
        } else {
            ProcesoAgregar();
        }
    }

    @FXML
    void btnBuscarClick(ActionEvent event) {
        ProcesoBuscar();
    }

    @FXML
    void btnEditarClick(ActionEvent event) {
        if (txtCedulaEmp.getText().isEmpty() || txtNombreEmp.getText().isEmpty() || txtPrimerAEmp.getText().isEmpty()
                || txtTelefonoEmp.getText().isEmpty()
                || txtContraseñaEmp.getText().isEmpty() || txtDireccionEmp.getText().isEmpty()) {
            Message.getInstance().Warning("Información requerida", "Debe completar todos los campos requeridos.");
        } else {
            ProcesoEditar();
        }
    }

    @FXML
    void btnEliminarClick(ActionEvent event) {
        if (txtCedulaEmp.getText().isEmpty()) {
            Message.getInstance().Warning("Información requerida", "Debe ingresar como mínimo el campo cédula.");
        } else {
            ProcesoEliminar();
        }
    }

    @FXML
    void btnSalirClick(ActionEvent event) {
        ScenesManager.getInstance().LoadSceneMenu();
    }

    @FXML
    void btnLimpiarClick(ActionEvent event) {
        LimpiarControlesGUI();
    }

    @FXML
    void txtCedulaAction(ActionEvent event) {
        Persona persona = PersonaJpaController.getInstance().ConsultarPersonaCedula(txtCedulaEmp.getText());
        if (persona != null) {
            CargarDatosUsuario(persona);
        }
    }

    @FXML
    void btnPasarClick(ActionEvent event) {
        Permiso permiso = lvDisponibles.getSelectionModel().getSelectedItem();
        if (permiso != null) {
            Persona persona = PersonaJpaController.getInstance().ConsultarPersonaCedula(txtCedulaEmp.getText());
            if (persona != null) {
                ProcesoAgregarPermiso(persona);
            }
        } else {
            Message.getInstance().Warning("Acción requerida", "Debe selecionar un permiso de la lista de permisos disponibles.");
        }
    }

    @FXML
    void btnQuitarClick(ActionEvent event) {
        Permiso permiso = lvAsignados.getSelectionModel().getSelectedItem();
        if (permiso != null) {
            Persona persona = PersonaJpaController.getInstance().ConsultarPersonaCedula(txtCedulaEmp.getText());
            if (persona != null) {
                ProcesoQuitarPermiso(persona);
            }
        } else {
            Message.getInstance().Warning("Acción requerida", "Debe selecionar un permiso de la lista de permisos asignados.");
        }
    }

    @FXML
    void btnPasarTodosClick(ActionEvent event) {
        List<Permiso> permisos = lvDisponibles.getItems();
        if (permisos != null) {
            Persona persona = PersonaJpaController.getInstance().ConsultarPersonaCedula(txtCedulaEmp.getText());
            if (persona != null) {
                ProcesoAgregarTodosPermisos(persona);
            }
        } else {
            Message.getInstance().Warning("Permisos disponibles", "No hay más permisos disponibles.");
        }
    }

    @FXML
    void btnQuitarTodosClick(ActionEvent event) {
        List<Permiso> permisos = lvAsignados.getItems();
        if (permisos != null) {
            Persona persona = PersonaJpaController.getInstance().ConsultarPersonaCedula(txtCedulaEmp.getText());
            if (persona != null) {
                ProcesoQuitarTodosPermisos(persona);
            }
        } else {
            Message.getInstance().Warning("Permisos asignados", "No hay más permisos asignados.");
        }
    }

    @FXML
    void txtCedulaEmpTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtCedulaEmp.getText().length(), 30, event);
    }

    @FXML
    void txtNombreEmpTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtNombreEmp.getText().length(), 30, event);
    }

    @FXML
    void txtPrimerAEmpTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtPrimerAEmp.getText().length(), 30, event);
    }

    @FXML
    void txtSegundoAEmpTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtSegundoAEmp.getText().length(), 30, event);
    }

    @FXML
    void txtCorreoEmpTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtCorreoEmp.getText().length(), 30, event);
    }

    @FXML
    void txtTelefonoEmpTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtTelefonoEmp.getText().length(), 30, event);
    }

    @FXML
    void txtTelefonoEmp2Typed(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtTelefonoEmp2.getText().length(), 30, event);
    }

    @FXML
    void txtContraseñaEmpTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtContraseñaEmp.getText().length(), 30, event);
    }

    @FXML
    void txtDireccionEmpTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtDireccionEmp.getText().length(), 500, event);
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    void ProcesoAgregar() {
        try {
            Persona persona = PersonaJpaController.getInstance().ConsultarPersonaCedula(txtCedulaEmp.getText());
            if (persona == null) {
                persona = new Persona(txtCedulaEmp.getText(), txtNombreEmp.getText(), txtPrimerAEmp.getText(), "A");

                persona.setPerSApellido(txtSegundoAEmp.getText());
                Usuario user = (Usuario) AppContext.getInstance().get("user");
                persona.setPerFerreteria(user.getPersona().getPerFerreteria());

                Usuario usuario = new Usuario(persona.getPerCedula(), persona.getPerCedula(), String.valueOf(txtContraseñaEmp.getText()), "A");
                Cliente cliente = new Cliente(persona.getPerCedula(), java.sql.Date.valueOf(LocalDate.now()), "A");

                String mail = "No tiene";
                String tel2 = "No tiene";

                if (!txtTelefonoEmp2.getText().isEmpty()) {
                    tel2 = txtTelefonoEmp2.getText();
                }

                if (!txtCorreoEmp.getText().isEmpty()) {
                    mail = txtCorreoEmp.getText();
                }

                Contacto contactoTel = new Contacto(Integer.SIZE, txtTelefonoEmp.getText(), "TEL");
                Contacto contactoTel2 = new Contacto(Integer.SIZE, tel2, "TEL2");
                Contacto contactoEma = new Contacto(Integer.SIZE, mail, "EMAIL");
                Direccion direcion = new Direccion(Integer.SIZE, txtDireccionEmp.getText());

                persona.setCliente(cliente);
                persona.setUsuario(usuario);

                persona = PersonaJpaController.getInstance().AgregarPersona(persona, direcion, contactoTel, contactoTel2, contactoEma);
                if (persona != null) {
                    Message.getInstance().Information("Acción exitosa", "Empleado agregado correctamente.");
                    LimpiarControlesGUI();
                } else {
                    Message.getInstance().Error("Accion no exitosa", "Ocurrió un error y no se pudo agregar el empleado.");
                }
            } else {
                if (Message.getInstance().Confirmation("Empleado existente", "Ya existe un empleado registrado con el mismo número de cédula.\n"
                        + "¿Desea actualizar la informacón?")) {
                    ProcesoEditar();
                }
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo agregar el empleado.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void ProcesoEditar() {
        try {
            Persona persona = PersonaJpaController.getInstance().ConsultarPersonaCedula(txtCedulaEmp.getText());
            if (persona != null) {

                persona.setPerNombre(txtNombreEmp.getText());
                persona.setPerPApellido(txtPrimerAEmp.getText());
                persona.setPerSApellido(txtSegundoAEmp.getText());

                Contacto contactoTel = BuscarContactoTipo(persona, "TEL");
                Contacto contactoTel2 = BuscarContactoTipo(persona, "TEL2");
                Contacto contactoEma = BuscarContactoTipo(persona, "EMAIL");

                Direccion direcion = persona.getDireccionList().get(0);

                contactoTel.setConContacto(txtTelefonoEmp.getText());

                if (contactoTel2 != null) {
                    contactoTel2.setConContacto(txtTelefonoEmp2.getText());
                }

                if (contactoEma != null) {
                    contactoEma.setConContacto(txtCorreoEmp.getText());
                }
                direcion.setDirDirExacta(txtDireccionEmp.getText());

                persona.getUsuario().setUsuContraseña(String.valueOf(txtContraseñaEmp.getText()));

                persona.setPerEstado("A");
                persona.getUsuario().setUsuEstado("A");
                persona.getCliente().setCliEstado("A");

                persona = PersonaJpaController.getInstance().ModificarPersona(persona);
                if (persona != null) {
                    Message.getInstance().Information("Acción exitosa", "Empleado editado corectamente.");
                } else {
                    Message.getInstance().Error("Accion no exitosa", "Ocurrió un error y no se pudieron editar los datos del empleado.");
                }
            } else {
                Message.getInstance().Warning("Empleado no existente", "No existe un empleado con la cédula ingresada.");
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo editar el empleado. ");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void ProcesoBuscar() {
        LanzarBusqueda();
        Persona persona = (Persona) AppContext.getInstance().get("selected-persona");
        if (persona != null) {
            CargarDatosUsuario(persona);
        }
    }

    void ProcesoEliminar() {
        try {
            Persona persona = PersonaJpaController.getInstance().ConsultarPersonaCedula(txtCedulaEmp.getText());
            if (persona != null) {
                persona.setPerEstado("I");
                persona.getUsuario().setUsuEstado("I");
                persona.getCliente().setCliEstado("I");
                PersonaJpaController.getInstance().ModificarPersona(persona);
                Message.getInstance().Information("Acción exitosa", "Empleado eliminado corectamente.");
                LimpiarControlesGUI();
            } else {
                Message.getInstance().Warning("Empleado no existente", "No existe un empleado con la cédula ingresada.");
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo eliminar el empleado.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void ProcesoAgregarPermiso(Persona persona) {
        try {
            persona.getUsuario().getPermisoList().add(lvDisponibles.getSelectionModel().getSelectedItem());
            UsuarioJpaController.getInstance().ModificarUsuario(persona.getUsuario());
            CargarDatosUsuario(persona);
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo agregar el permiso.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void ProcesoQuitarPermiso(Persona persona) {
        try {
            persona.getUsuario().getPermisoList().remove(lvAsignados.getSelectionModel().getSelectedItem());
            UsuarioJpaController.getInstance().ModificarUsuario(persona.getUsuario());
            CargarDatosUsuario(persona);
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo quitar el permiso.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void ProcesoAgregarTodosPermisos(Persona persona) {
        try {
            List<Permiso> permisoAsignadosList = PermisoJpaController.getInstance().ConsultarPermisosAsignados(persona.getUsuario());
            List<Permiso> permisoDisponibleList = PermisoJpaController.getInstance().ConsultarPermisosDisponibles(permisoAsignadosList);
            persona.getUsuario().getPermisoList().addAll(permisoDisponibleList);
            UsuarioJpaController.getInstance().ModificarUsuario(persona.getUsuario());
            CargarDatosUsuario(persona);
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudieron agregar los permisos.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void ProcesoQuitarTodosPermisos(Persona persona) {
        try {
            persona.getUsuario().getPermisoList().clear();
            UsuarioJpaController.getInstance().ModificarUsuario(persona.getUsuario());
            CargarDatosUsuario(persona);
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudieron quitar los permisos.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    /*-----------------------------------------------------------------------------*/
    void CargarDatosUsuario(Persona persona) {
        try {
            txtCedulaEmp.setText(persona.getPerCedula());
            txtNombreEmp.setText(persona.getPerNombre());
            txtPrimerAEmp.setText(persona.getPerPApellido());
            txtSegundoAEmp.setText(persona.getPerSApellido());
            txtTelefonoEmp.setText(BuscarContactoContacto(persona, "TEL"));
            txtTelefonoEmp2.setText(BuscarContactoContacto(persona, "TEL2"));
            txtCorreoEmp.setText(BuscarContactoContacto(persona, "EMAIL"));

            txtContraseñaEmp.setText(persona.getUsuario().getUsuContraseña());

            txtDireccionEmp.setText(persona.getDireccionList().get(0).getDirDirExacta());

            lblUsuario.setText(persona.getPerCedula() + " " + persona.getPerNombre() + " " + persona.getPerPApellido());

            List<Permiso> permisoAsignadosList = PermisoJpaController.getInstance().ConsultarPermisosAsignados(persona.getUsuario());
            ObservableList<Permiso> oPermisoAsignadosList = FXCollections.observableArrayList(permisoAsignadosList);

            List<Permiso> permisoDisponibleList = PermisoJpaController.getInstance().ConsultarPermisosDisponibles(permisoAsignadosList);
            ObservableList<Permiso> oPermisoDisponibleList = FXCollections.observableArrayList(permisoDisponibleList);

            lvAsignados.setItems(oPermisoAsignadosList);
            lvDisponibles.setItems(oPermisoDisponibleList);
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo cargar la información del empleado.");
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

    String BuscarContactoContacto(Persona pPersona, String Tipo) {
        for (Contacto con : pPersona.getContactoList()) {
            if (con.getConTipoContacto().equalsIgnoreCase(Tipo)) {
                return con.getConContacto();
            }
        }
        return "";
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    void LimpiarControlesGUI() {
        txtCedulaEmp.setText("");
        txtNombreEmp.setText("");
        txtPrimerAEmp.setText("");
        txtSegundoAEmp.setText("");
        txtTelefonoEmp.setText("");
        txtTelefonoEmp2.setText("");
        txtCorreoEmp.setText("");
        txtContraseñaEmp.setText("");
        txtDireccionEmp.setText("");
        lblUsuario.setText("Busque y selecione un empleado en la pantalla anterior.");
        lvAsignados.setItems(null);
        lvDisponibles.setItems(null);
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    void LanzarBusqueda() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Empleados.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initOwner(btnBuscar.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de búsqueda.");                    
            LoggerManager.Logger().info(ex.toString());
        }
    }
}
