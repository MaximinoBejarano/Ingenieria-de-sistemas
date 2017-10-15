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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_ClientesController implements Initializable {

    @FXML
    private TextField txtCedCliente;

    @FXML
    private void buscarCliente(ActionEvent e) {
        try {
            if (txtCedCliente.getText().equals("")) {
                Parent root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Clientes.fxml"));
                Stage stage = new Stage(StageStyle.UTILITY);

                stage.setScene(new Scene(root));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(txtCedCliente.getScene().getWindow());
                stage.showAndWait();
            
            
            } else if (!txtCedCliente.getText().equals("")) {
                //rellenar los datos del formulario
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
