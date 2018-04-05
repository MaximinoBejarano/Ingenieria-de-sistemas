/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.concurrent.Task;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import ferreteria_las_vegas.utils.Message;
import ferreteria_las_vegas.utils.AppContext;
import javafx.scene.control.ProgressIndicator;
import ferreteria_las_vegas.utils.GeneralUtils;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.model.entities.Usuario;
import ferreteria_las_vegas.model.controller.UsuarioJpaController;
import java.io.IOException;

public class FXML_LoginController implements Initializable {

    /*--------------------------------------------------------------------------------------------------------------*/
    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private Button btnLogin;

    @FXML
    private ProgressIndicator progresbar;

    /*--------------------------------------------------------------------------------------------------------------*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    @FXML
    void btnLoginClick(ActionEvent event) {
        if (txtUsuario.getText().isEmpty() || txtContraseña.getText().isEmpty()) {
            Message.getInstance().Warning("Información requerida", "Debe completar todos los campos requeridos.");
        } else {
            ProcesoLogeo();
        }
    }

    @FXML
    void txtContraseñaAction(ActionEvent event) {
        if (txtUsuario.getText().isEmpty() || txtContraseña.getText().isEmpty()) {
            Message.getInstance().Warning("Información requerida", "Debe completar todos los campos requeridos.");
        } else {
            ProcesoLogeo();
        }
    }

    @FXML
    void txtUsuarioTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtUsuario.getText().length(), 30, event);
    }

    @FXML
    void txtContraseñaTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtContraseña.getText().length(), 30, event);
    }

    /*--------------------------------------------------------------------------------------------------------------*/
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
                    if (loginTask.getValue().getUsuEstado().equalsIgnoreCase("A")) {
                        AppContext.getInstance().set("user", loginTask.getValue());
                        PermisosManager.getInstance().setUsario(loginTask.getValue());
                        LanzarMenu();
                    } else {
                        Message.getInstance().Warning("Usuario inválido", "Su usuario esta desactivado.");
                    }
                } else {
                    Message.getInstance().Warning("Credenciales inválidas", "Usuario o Contraseña inválida.");
                }
                txtUsuario.setDisable(false);
                txtContraseña.setDisable(false);
                btnLogin.setDisable(false);
                btnLogin.setText("Acceder");
                progresbar.setVisible(false);
            });

            loginTask.setOnFailed(e -> {
                txtUsuario.setDisable(false);
                txtContraseña.setDisable(false);
                btnLogin.setDisable(false);
                btnLogin.setText("Acceder");
                progresbar.setVisible(false);
                Message.getInstance().Error("Error", "Ocurrió un error al intentar acceder al sistema.");
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
            Message.getInstance().Error("Error", "Ocurrió un error al intentar acceder al sistema.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    void LanzarMenu() {
        try {
            ScenesManager.getInstance().LoadSceneMenu();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo ir a la pantalla de menú.");
            LoggerManager.Logger().info(ex.toString());
        }
    }
}
