/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 *
 * @author wili
 */
public class FXML_LoginController implements Initializable {

    @FXML
    private Button btnLogin;
    @FXML
    private Label lblError;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtContraseña;

    
    @FXML
    private void verificarAcceso(ActionEvent e) {
        try {
            if (txtUsuario.getText().equals("") && txtContraseña.getText().equals("")) {
                Parent root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Menu.fxml"));
                Stage stage = (Stage) ((Node) (e.getSource())).getScene().getWindow();
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
