/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import ferreteria_las_vegas.model.controller.UsuarioJpaController;
<<<<<<< HEAD
import ferreteria_las_vegas.utils.AppContext;
=======
import ferreteria_las_vegas.model.entities.Usuario;
import ferreteria_las_vegas.utils.AppContext;
import javafx.fxml.FXML;
>>>>>>> a37f8fa45708c33a54156166ea5c88040c0cee3b
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

<<<<<<< HEAD
    void LoginProgress() {
        
        if (UsuarioJpaController.getInstance().SolicitarAcceso(txtUsuario.getText(), String.valueOf(txtContraseña.getText())) != null) {
            LoginProgressLanzarMenu();
=======
    void ProcesoLogeo() {
        Usuario usuario = UsuarioJpaController.getInstance().SolicitarAcceso(txtUsuario.getText(), String.valueOf(txtContraseña.getText()));
        if (usuario != null) {
            //if(true){
            AppContext.getInstance().set("user", usuario);
            LanzarMenu();
>>>>>>> a37f8fa45708c33a54156166ea5c88040c0cee3b
        } else {
            new Alert(Alert.AlertType.WARNING, "Usuario o Contraseña invalido.", ButtonType.OK).showAndWait();
        }
    }

<<<<<<< HEAD
    void LoginProgressLanzarMenu() {
        try {
            if (!"".equals(txtUsuario.getText()) && !"".equals(txtContraseña.getText())) {
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
=======
    void LanzarMenu() {
        try {            
            
            Stage stageAct = (Stage) btnLogin.getScene().getWindow();
            stageAct.close();
            
            Parent root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Menu.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.resizableProperty().set(true);
            stage.setMaximized(true);
            stage.show();                                    
>>>>>>> a37f8fa45708c33a54156166ea5c88040c0cee3b
        } catch (Exception ex) {
            // mandar al servidor al log de errores
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
