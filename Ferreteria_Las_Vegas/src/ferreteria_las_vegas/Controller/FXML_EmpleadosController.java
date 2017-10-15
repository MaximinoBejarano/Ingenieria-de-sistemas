/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_EmpleadosController implements Initializable {

    @FXML
    private TextField txtCedEmpleado;

    @FXML
    private void buscarEmpleados(ActionEvent e) {
        try {
            if (txtCedEmpleado.getText().equals("")) {
                Parent root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Empleados.fxml"));
                Stage stage = (Stage) ((Node) (e.getSource())).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            
            
            } else if (!txtCedEmpleado.getText().equals("")) {
                //rellenar los datos del formulario
            }
        } catch (Exception ex) {
            // mandar al servidor al log de errores
            JOptionPane.showMessageDialog(null, ex.toString());
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
