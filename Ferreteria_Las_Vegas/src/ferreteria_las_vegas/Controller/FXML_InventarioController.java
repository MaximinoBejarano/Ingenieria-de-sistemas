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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_InventarioController implements Initializable {

   @FXML
    private TableColumn CodigoProducto;

   @FXML
   private Button btnNuevoProducto;
   
    @FXML
    private void AgregarProducto(ActionEvent e) {
        try {
            if ( true) {
                Parent root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Productos.fxml"));
                Stage stage = new Stage(StageStyle.UTILITY);

                stage.setScene(new Scene(root));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnNuevoProducto.getScene().getWindow());
                stage.showAndWait();
            
            
            } else {
                //rellenar los datos del formulario
            }
        } catch (Exception ex) {
            // mandar al servidor al log de errores
            System.out.println(ex);
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
