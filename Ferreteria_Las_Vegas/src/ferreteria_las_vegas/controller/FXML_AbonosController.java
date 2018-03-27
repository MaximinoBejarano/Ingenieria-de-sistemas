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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Maximino
 */
public class FXML_AbonosController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnAgregarAbono;
    @FXML
    private Button btnEditarAbono;
    @FXML
    private Button btnEliminarAbono;
    @FXML
    private ComboBox<?> cmbCliente;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Label lblNumFactura;
    @FXML
    private Label lblSaldoFact;
    @FXML
    private Label lblSaldoTotal;
    @FXML
    private DatePicker pickFecha;
    @FXML
    private TextField txtAbono;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnSalir_Click(ActionEvent event) {
        ScenesManager.getInstance().LoadSceneMenu();
    }

    @FXML
    private void btnAgregarAbono_Click(ActionEvent event) {
    }

    @FXML
    private void btnEditarAbono_Click(ActionEvent event) {
    }

    @FXML
    private void btnEliminarAbono_Click(ActionEvent event) {
    }

    @FXML
    private void cmbCliente_Click(ActionEvent event) {
    }

    @FXML
    private void btnFiltrar_Click(ActionEvent event) {
    }

}
