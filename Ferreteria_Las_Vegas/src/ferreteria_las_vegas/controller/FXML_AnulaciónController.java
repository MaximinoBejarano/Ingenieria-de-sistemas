/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_AnulaciónController implements Initializable {

    @FXML
    private Button btnLimpiarPantalla;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblCliente;
    @FXML
    private Label lblMontoTotal;
    @FXML
    private Label lblUsuario;
    @FXML
    private TableColumn<?, ?> tcCodigo;
    @FXML
    private TableColumn<?, ?> tcNombre;
    @FXML
    private TableColumn<?, ?> tcDescripcion;
    @FXML
    private TableColumn<?, ?> tcCantidad;
    @FXML
    private TableColumn<?, ?> tcPrecio;
    @FXML
    private Button btnAnularFactura;
    @FXML
    private Button btnBuscarFactura;
    @FXML
    private Button btnReimprimirFactura;
    @FXML
    private Label lblCodigoFactura;

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
    private void btnLimpiarCampos(ActionEvent event) {
    }

    @FXML
    private void btnAnularFactura(ActionEvent event) {
    }

    @FXML
    private void btnBuscarFactura(ActionEvent event) {
    }

    @FXML
    private void btnReimprimirFactura(ActionEvent event) {
    }
}
