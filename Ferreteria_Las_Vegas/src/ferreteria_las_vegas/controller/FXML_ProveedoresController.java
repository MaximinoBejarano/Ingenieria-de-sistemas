package ferreteria_las_vegas.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class FXML_ProveedoresController {

    @FXML
    private TextField txtCedulaJuridica;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreoEmp;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtTelefono2;

    @FXML
    private TextArea txtDireccion;

 

    @FXML
    void txtCedulaEmpTyped(KeyEvent event) {

    }

    @FXML
    void txtCorreoEmpTyped(KeyEvent event) {

    }

    @FXML
    void txtDireccionEmpTyped(KeyEvent event) {

    }

    @FXML
    void txtNombreEmpTyped(KeyEvent event) {

    }

    @FXML
    void txtTelefonoEmp2Typed(KeyEvent event) {

    }

    @FXML
    void txtTelefonoEmpTyped(KeyEvent event) {

    }

    @FXML
    void SalirClick(ActionEvent event) {
        ScenesManager.getInstance().LoadSceneMenu();
    }
}
