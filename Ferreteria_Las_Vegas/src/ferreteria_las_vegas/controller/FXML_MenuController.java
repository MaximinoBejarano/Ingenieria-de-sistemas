/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.entities.Usuario;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_MenuController implements Initializable {

    @FXML
    private Button btnFacturacion;

    @FXML
    private Button btnAbonos;

    @FXML
    private Button btnNotaCredito;

    @FXML
    private Button btnInventario;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnEmpleados;

    @FXML
    private Button btnProveedores;

    @FXML
    private Button btnAnulacion;

    @FXML
    private Button btnEstadisticas;

    @FXML
    private Button btnConfiguraciones;

    @FXML
    private Button btnSalir;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblUserName;
    
    /*--------------------------------------------------------------------------------------------------------------*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Usuario usuario = (Usuario) AppContext.getInstance().get("user");
        lblUserName.setText(lblUserName.getText() + usuario.getPersona().getPerNombre() + " " + usuario.getPersona().getPerPApellido());
        lblDate.setText("Bienvenido");
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    @FXML
    void btnFacturacionClick(ActionEvent e) {
        try {
            ScenesManager.getInstance().LoadSceneFacturacion();
        } catch (IOException ex) {            
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de facturación.");            
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    void btnAbonosClick(ActionEvent e) {
        try {
            ScenesManager.getInstance().LoadSceneAbonos();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de abonos.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    void btnNotaCreditoClick(ActionEvent e) {
        try {
            ScenesManager.getInstance().LoadSceneVales();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de vales.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    void btnInventarioClick(ActionEvent e) {
        try {
            ScenesManager.getInstance().LoadSceneInventario();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de inventario.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    void btnClientesClick(ActionEvent e) {
        try {
            ScenesManager.getInstance().LoadSceneClientes();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de clientes.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    void btnEmpleadosClick(ActionEvent e) {
        try {
            ScenesManager.getInstance().LoadSceneEmpleados();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de empleados.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    void btnProveedoresClick(ActionEvent e) {
        try {
            ScenesManager.getInstance().LoadSceneProveedores();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de proveedores.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    void btnAnulacionClick(ActionEvent e) {
        try {
            ScenesManager.getInstance().LoadSceneAnulacion();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de anulaciones.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    void btnSalirClick(ActionEvent event) {        
        try {
            ScenesManager.getInstance().LoadSceneLogin();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de login.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    void btnEstadisticasClick(ActionEvent event) {
        /*try {
            
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de estadisticas.");
            LoggerManager.Logger().info(ex.toString());
        }*/
    }

    @FXML
    void btnConfiguracionesClick(ActionEvent event) {
        /*try {
            
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de configuraciones.");
            LoggerManager.Logger().info(ex.toString());
        }*/
    }    
}
