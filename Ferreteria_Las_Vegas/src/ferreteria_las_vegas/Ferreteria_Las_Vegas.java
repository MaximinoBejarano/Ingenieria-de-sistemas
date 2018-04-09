/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas;

import ferreteria_las_vegas.controller.ScenesManager;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author wili
 */
public class Ferreteria_Las_Vegas extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {        
        LoggerManager.Open();                
        try {
            ScenesManager.getInstance().LoadStage(stage);   
        } catch (IOException ex) {            
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la aplicación.");            
            LoggerManager.Logger().info(ex.toString());
            LoggerManager.Close();
        }                
    }
    
    @Override
    public void stop(){
        LoggerManager.Close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Se ha realizado un cambio
        launch(args);
    }

}
