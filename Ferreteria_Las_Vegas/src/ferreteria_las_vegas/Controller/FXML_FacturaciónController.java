/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_FacturaciónController implements Initializable {

    @FXML
    private Button btnNuevoProducto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    void SalirClick(ActionEvent event) {
        ScenesManager.getInstance().LoadSceneMenu();
    }

    @FXML
    private void CobrarFactura(ActionEvent e) {
       LanzarBusqueda();
    }
    
    public void LanzarBusqueda(){
     try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Pagos.fxml"));
           
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage(StageStyle.UTILITY);
                stage.setScene(new Scene(root1));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnNuevoProducto.getScene().getWindow());
//                stage.initOwner(btnNuevoProducto.getScene().getWindow());
                stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(FXML_FacturaciónController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
