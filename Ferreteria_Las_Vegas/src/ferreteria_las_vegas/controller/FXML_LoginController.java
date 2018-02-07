/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.UsuarioJpaController;

import ferreteria_las_vegas.model.entities.Usuario;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.LoggerManager;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
    private ProgressIndicator progresbar;

    @FXML
    private void verificarAcceso(ActionEvent e) {
        if (txtUsuario.getText().isEmpty() || txtContraseña.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Debe completar todos los campos requeridos.", ButtonType.OK).showAndWait();
        } else {
            ProcesoLogeo();
        }
    }

    @FXML
    void txtContraseñaAction(ActionEvent event) {
        if (txtUsuario.getText().isEmpty() || txtContraseña.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Debe completar todos los campos requeridos.", ButtonType.OK).showAndWait();
        } else {
            ProcesoLogeo();
        }
    }

    void ProcesoLogeo() {
        try {
            Task<Usuario> loginTask = new Task<Usuario>() {
            @Override
            protected Usuario call() throws Exception {
                return UsuarioJpaController.getInstance().SolicitarAcceso(txtUsuario.getText(), String.valueOf(txtContraseña.getText()));
            }
        };

        loginTask.setOnSucceeded(e -> {
            if (loginTask.getValue() != null) {
                AppContext.getInstance().set("user", loginTask.getValue());
                PermisosManager.getInstance().setUsario(loginTask.getValue());
                LanzarMenu();
            } else {
                new Alert(Alert.AlertType.WARNING, "Usuario o Contraseña invalida.", ButtonType.OK).showAndWait();
            }
            txtUsuario.setDisable(false);
            txtContraseña.setDisable(false);
            btnLogin.setDisable(false);
            btnLogin.setText("Acceder");
        });

        loginTask.setOnFailed(e -> {
            txtUsuario.setDisable(false);
            txtContraseña.setDisable(false);
            btnLogin.setDisable(false);
            btnLogin.setText("Acceder");
            new Alert(Alert.AlertType.ERROR, "Ocurrio un error al intentar logear", ButtonType.OK).showAndWait();
        });

        progresbar.setVisible(true);
        btnLogin.setDisable(true);
        btnLogin.setText("Accediendo...");
        
        Thread thread = new Thread(loginTask);
        thread.setDaemon(true);
        
        txtUsuario.setDisable(true);
        txtContraseña.setDisable(true);
        btnLogin.setDisable(true);
        btnLogin.setText("Accediendo...");
        thread.start();        
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
            new Alert(Alert.AlertType.ERROR, "Ocurrio un error al intentar logear. El codigo de error es "
                    + "el siguiente: " + ex, ButtonType.OK).showAndWait();
        }                
    }

    void LanzarMenu() {
        ScenesManager.getInstance().LoadSceneMenu();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
