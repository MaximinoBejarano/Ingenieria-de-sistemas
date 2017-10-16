/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas;

import ferreteria_las_vegas.utils.EntityManagerHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author wili
 */
public class Ferreteria_Las_Vegas extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        EntityManagerHelper.getManager();
        
        Parent root = FXMLLoader.load(getClass().getResource("view/FXML_Login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.resizableProperty().set(false); 
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Se ha realizado un cambio
        launch(args);
    }
    
}
