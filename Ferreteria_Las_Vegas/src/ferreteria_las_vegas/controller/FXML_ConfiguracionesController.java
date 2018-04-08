/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author sanwi
 */
public class FXML_ConfiguracionesController implements Initializable {

    @FXML
    private VBox DataPanel;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnLimpiarPantalla;
    @FXML
    private Button btnBuscarConfiguraciones;
    @FXML
    private Button btnEditarConfiguraciones;
    @FXML
    private Button btnEliminarConfiguraciones;
    @FXML
    private TextField txtCedulaJuridica;
    @FXML
    private TextField txtNombreFerreteria;
    @FXML
    private TextField txtImpuestoVenta;
    @FXML
    private TextField txtCambioDolar;
    @FXML
    private TableColumn<?, ?> tcCedulaJuridica;
    @FXML
    private TableColumn<?, ?> tcNombreFerreteria;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void SalirClick(ActionEvent event) {
        try {
            ScenesManager.getInstance().LoadSceneMenu();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo volver a la pantalla de menú.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    private void btnLimpiarCampos(ActionEvent event) {
    }

    @FXML
    private void AgregarConfiguraciones(ActionEvent event) {
    }

    @FXML
    private void EditarConfiguraciones(ActionEvent event) {
    }

    @FXML
    private void EliminarConfiguraciones(ActionEvent event) {
    }

    @FXML
    private void txtCedulaJuridica(KeyEvent event) {
    }

    @FXML
    private void txtNombreFerreteria(KeyEvent event) {
    }

    @FXML
    private void txtImpuestoVenta(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtImpuestoVenta.getText().length(), 7, event);
    }

    @FXML
    private void txtCambioDolar(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtCambioDolar.getText().length(), 7, event);
    }

}
