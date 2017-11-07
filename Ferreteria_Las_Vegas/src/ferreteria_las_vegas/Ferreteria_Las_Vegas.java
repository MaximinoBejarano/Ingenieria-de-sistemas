/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas;

import ferreteria_las_vegas.Controller.ScenesManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author wili
 */
public class Ferreteria_Las_Vegas extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ScenesManager.getInstance().LoadStage(stage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Se ha realizado un cambio
        launch(args);
    }

}
