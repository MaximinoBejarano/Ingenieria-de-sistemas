/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.FerreteriaJpaController;
import ferreteria_las_vegas.model.entities.Ferreteria;
import ferreteria_las_vegas.model.entities.Parametro;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Johan
 */
public class FXML_ConfiguracionesController implements Initializable {

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnSalir;

    @FXML
    private TextField txtCedulaJuridica;

    @FXML
    private TextField txtNombreFerreteria;

    @FXML
    private TextField txtImpuestoVenta;

    @FXML
    private TextField txtTipoCambio;

    @FXML
    private TextField txtSaldoCajaChica;

    /*--------------------------------------------------------------------------------------------------------------*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarDatos();
    }
    
    /*--------------------------------------------------------------------------------------------------------------*/
    @FXML
    void btnEditarClick(ActionEvent event) {
        if (txtCedulaJuridica.getText().isEmpty() || txtNombreFerreteria.getText().isEmpty()
                || txtTipoCambio.getText().isEmpty() || txtImpuestoVenta.getText().isEmpty() || txtSaldoCajaChica.getText().isEmpty()) {
            Message.getInstance().Warning("Datos Requeridos", "Debe ingesar todos los campos requeridos.");
        } else {
            ProcesoEditar();
        }
    }

    @FXML
    void btnSalirClick(ActionEvent event) {
        try {
            ScenesManager.getInstance().LoadSceneMenu();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo volver a la pantalla de menú.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    void txtCedulaJuridicaTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtCedulaJuridica.getText().length(), 30, event);
    }

    @FXML
    void txtNombreFerreteriaTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtNombreFerreteria.getText().length(), 30, event);
    }

    @FXML
    void txtTipoCambioTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtTipoCambio.getText().length(), 8, event);
    }

    @FXML
    void txtImpuestoVentaTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtImpuestoVenta.getText().length(), 8, event);
    }

    @FXML
    void txtSaldoCajaChicaTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtSaldoCajaChica.getText().length(), 8, event);
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    void ProcesoEditar() {
        try {
            if(fer!=null){
                fer.setFerNombre(txtNombreFerreteria.getText());
                par.setParTipoCambio(Double.parseDouble(txtTipoCambio.getText()));
                par.setParImpuestoVenta(Double.parseDouble(txtImpuestoVenta.getText()));
                par.setParSaldoCajaChica(Double.parseDouble(txtSaldoCajaChica.getText()));
                Ferreteria ferr = FerreteriaJpaController.getInstance().ModificarFerreteriaParametro(fer, par);
                if(ferr!=null){
                    Message.getInstance().Information("Acción exitosa", "Datos editados corectamente.");
                    CargarDatos();
                } else {
                    Message.getInstance().Error("Error", "Ocurrió un error y no se pudo editar los datos.");
                }
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo editar los datos.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    void CargarDatos() {
        try {
            Usuario user = (Usuario) AppContext.getInstance().get("user");
            fer = user.getPersona().getPerFerreteria();
            par = fer.getParametro();
            txtCedulaJuridica.setText(fer.getFerCedula());
            txtNombreFerreteria.setText(fer.getFerNombre());
            txtTipoCambio.setText(String.valueOf(par.getParTipoCambio()));
            txtImpuestoVenta.setText(String.valueOf(par.getParImpuestoVenta()));
            txtSaldoCajaChica.setText(String.valueOf(par.getParSaldoCajaChica()));
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo cargar los datos de la ferreteria.");
            LoggerManager.Logger().info(ex.toString());
        }
    }
    
    /*--------------------------------------------------------------------------------------------------------------*/
    private Ferreteria fer;
    private Parametro par;
}
