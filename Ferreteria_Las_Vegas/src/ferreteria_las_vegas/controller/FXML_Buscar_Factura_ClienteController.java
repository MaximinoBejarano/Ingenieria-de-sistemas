/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

/**
 * FXML Controller class
 *
 * @author sanwi
 */
public class FXML_Buscar_Factura_ClienteController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnSelecionar;
    @FXML
    private TableColumn<?, ?> tcNumFactura;
    @FXML
    private TableColumn<?, ?> tcCliente;
    @FXML
    private TableColumn<?, ?> tcTipoFactura;
    @FXML
    private TableColumn<?, ?> tcFechaCompra;
    @FXML
    private TableColumn<?, ?> tcMontoTotal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSalirClick(ActionEvent event) {
    }

    @FXML
    private void btnSelecionarClick(ActionEvent event) {
    }
    
}
