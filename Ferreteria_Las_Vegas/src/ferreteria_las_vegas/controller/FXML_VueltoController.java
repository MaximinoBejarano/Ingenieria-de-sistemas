/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Maximino
 */
public class FXML_VueltoController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnConfirmar;
    @FXML
    private Label lblVuelto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lblVuelto.setText("");
        CargarVueto();
    }    

    @FXML
    private void btnSalir_Click(ActionEvent event) {
        Stage stageAct = (Stage) btnSalir.getScene().getWindow();
        stageAct.close();
    }

    @FXML
    private void btnConfirmar_Click(ActionEvent event) {
        Stage stageAct = (Stage) btnConfirmar.getScene().getWindow();
        stageAct.close();
          
    }
    
    public void CargarVueto(){
     double vuelto=(double)AppContext.getInstance().get("Vuelto");
     if(vuelto>0){
         lblVuelto.setText(String.valueOf(vuelto));
     }else{
       lblVuelto.setText("0");
     }
    }
  /*++++++++++++++++++++++++++++++++++++++++++++++++++++++Metodos Lanzadores+++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
}
