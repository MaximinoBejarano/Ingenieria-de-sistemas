/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.ProveedorJpaController;
import ferreteria_las_vegas.model.entities.Contacto;
import ferreteria_las_vegas.model.entities.Direccion;
import ferreteria_las_vegas.model.entities.Proveedor;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.GeneralUtils;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Johan
 */
public class FXML_ProveedoresController implements Initializable {

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnEliminar;

    @FXML
    private Tooltip totEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private Tooltip totEditar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Tooltip totAgregar;

    @FXML
    private TextField txtCedulaJuridica;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtTelefono2;

    @FXML
    private TextArea txtDireccion;
    
    /*--------------------------------------------------------------------------------------------------------------*/        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        proFlag = false;
        comFlag = false;
        aboFlag = false;
    }

    /*--------------------------------------------------------------------------------------------------------------*/        
    @FXML
    void btnAgregarClick(ActionEvent event) {

    }

    @FXML
    void btnBuscarClick(ActionEvent event) {

    }

    @FXML
    void btnEditarClick(ActionEvent event) {

    }

    @FXML
    void btnEliminarClick(ActionEvent event) {

    }

    @FXML
    void btnSalirClick(ActionEvent event) {
        ScenesManager.getInstance().LoadSceneMenu();
    }

    @FXML
    void txtCedulaJuridicaTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtCedulaJuridica.getText().length(), 30, event);
    }
    
    @FXML
    void txtNombreTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtNombre.getText().length(), 30, event);
    }

    @FXML
    void txtCorreoEmpTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtCorreo.getText().length(), 30, event);
    }    

    @FXML
    void txtTelefonoTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtTelefono.getText().length(), 30, event);
    }
    
    @FXML
    void txtTelefono2Typed(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtTelefono2.getText().length(), 30, event);
    }    
    
    @FXML
    void txtDireccionTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtDireccion.getText().length(), 30, event);
    }    
    
    @FXML
    void tabProveedoresChange(Event event) {
        
    }
    
    @FXML
    void tabComprasChange(Event event) {
        
    }
    
    @FXML
    void tabAbonosChange(Event event) {
        
    }
    
    /*--------------------------------------------------------------------------------------------------------------*/
    void ProcesoAgregarProveedor(){
        try {
            Proveedor proveedor = ProveedorJpaController.getInstance().ConsultarProveedorCedulaJuridica(txtCedulaJuridica.getText());
            if (proveedor == null) {
                proveedor = new Proveedor(Integer.SIZE,txtNombre.getText(),txtCedulaJuridica.getText(),"A");
                
                
                String mail = "No tiene";
                String tel2 = "No tiene";

                if (!txtTelefono2.getText().isEmpty()) {
                    tel2 = txtTelefono2.getText();
                }

                if (!txtCorreo.getText().isEmpty()) {
                    mail = txtCorreo.getText();
                }

                Contacto contactoTel = new Contacto(Integer.SIZE, txtTelefono.getText(), "TEL");
                Contacto contactoTel2 = new Contacto(Integer.SIZE, tel2, "TEL2");
                Contacto contactoEma = new Contacto(Integer.SIZE, mail, "EMAIL");
                Direccion direcion = new Direccion(Integer.SIZE, txtDireccion.getText());
                

                proveedor = ProveedorJpaController.getInstance().AgregarProveedor(proveedor, direcion, contactoTel, contactoTel2, contactoEma);
                if (proveedor != null) {
                    Message.getInstance().Information("Acción exitosa", "Empleado agregado corectamente.");
                    LimpiarControlesGUI();
                } else {
                    Message.getInstance().Error("Accion no exitosa", "Ocurrió un error y no se pudo agregar el empleado.");
                }
            } else {
                if(Message.getInstance().Confirmation("Empleado Existente", "Ya existe un empleado registrado con el mismo numero de cedula.\n"
                        + "¿Desea sobre actualizar la informacón?")){
                    ProcesoEditarProveedor();
                }                
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo agregar el proveedor. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }
    
    void ProcesoBuscarProveedor(){
        LanzarBusquedaProveedores();
    }
    
    void ProcesoEditarProveedor(){
        try {
            
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo editar el proveedor. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }
    
    void ProcesoEliminarProveedor(){
        try {
            
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo eliminar el proveedor. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }
    
    /*--------------------------------------------------------------------------------------------------------------*/
    void ProveedoresGUI(){
        
    }
    
    void ComprasGUI(){
        
    }
    
    void AbonosGUI(){
        
    }
    
    void LimpiarControlesGUI(){
        
    }
    /*--------------------------------------------------------------------------------------------------------------*/
    void LanzarBusquedaProveedores(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Proveedores.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initOwner(btnBuscar.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de búsqueda."
                    + "El código de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }
    
    /*--------------------------------------------------------------------------------------------------------------*/
    private boolean proFlag;
    private boolean comFlag;
    private boolean aboFlag;
}
