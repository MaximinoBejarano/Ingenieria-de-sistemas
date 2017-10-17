/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import ferreteria_las_vegas.model.controller.UsuarioJpaController;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXML_LoginController implements Initializable {

    @FXML
    private StackPane mainPane;

    @FXML
    private VBox dataPane;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblError;

    @FXML
    private void verificarAcceso(ActionEvent e) {
        if (txtUsuario.getText().isEmpty() || txtContraseña.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Debe completar todos los campos requeridos.", ButtonType.OK).showAndWait();
        } else {
            ProcesoLogeo();
        }
    }

    void ProcesoLogeo() {
        //if (UsuarioJpaController.getInstance().SolicitarAcceso(txtUsuario.getText(), String.valueOf(txtContraseña.getText())) != null) {
        if(true){
            LanzarMenu();
        } else {
            new Alert(Alert.AlertType.WARNING, "Usuario o Contraseña invalido.", ButtonType.OK).showAndWait();
        }
    }
    
    void LanzarMenu() {
        try {
            if (txtUsuario.getText()!="" && txtContraseña.getText()!="") {
                Parent root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Menu.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.resizableProperty().set(true);
                stage.setMaximized(true);
                stage.show();
            } else {
                lblError.setVisible(true);
            }
        } catch (Exception ex) {
            // mandar al servidor al log de errores
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
//, @NamedQuery(name = "Usuario.findByUsuLogin", query = "SELECT u FROM Usuario u WHERE u.usuNombre = :usuUsuario AND u.usuContrase\u00f1a = :usuContrase\u00f1a")