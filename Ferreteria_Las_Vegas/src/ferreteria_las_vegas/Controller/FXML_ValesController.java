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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_ValesController implements Initializable {

    @FXML
    private Button btnBuscarCliente;
    @FXML
    private ToggleGroup tipoDevolucion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buscarCliente(ActionEvent event) {
    }
    
}
