/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Usuario
 */
public class ScenesManager {

    private static ScenesManager INSTANCE = null;    
    private static Parent root;
    private static Stage stage;
    private static Scene scene;

    private ScenesManager() {
        stage = new Stage();
    }

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (ScenesManager.class) {

                if (INSTANCE == null) {
                    INSTANCE = new ScenesManager();
                }
            }
        }
    }

    public static ScenesManager getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

   public void LoadStage(Stage pstage) {
        try {
            root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Login.fxml"));
            stage = pstage;
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(true);            
            stage.setMinWidth(1100);
            stage.setMinHeight(650);            
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(ScenesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
   public void LoadSceneLogin()
   {
        try {
            root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Login.fxml"));
            scene.setRoot(root);
                 
        } catch (IOException ex) {
            Logger.getLogger(ScenesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public void LoadSceneMenu()
   {
        try {
            root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Menu.fxml"));
            scene.setRoot(root);
                 
        } catch (IOException ex) {
            Logger.getLogger(ScenesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public void LoadSceneEmpleados()
   {
        try {
            root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Empleados.fxml"));            
            scene.setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ScenesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public void LoadSceneClientes(){
       try {
            root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Clientes.fxml"));            
            scene.setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ScenesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public void LoadSceneInventario(){
       try {
            root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Inventario.fxml"));            
            scene.setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ScenesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public void LoadSceneProveedores(){
       try {
            root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Proveedores.fxml"));            
            scene.setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ScenesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public void LoadSceneAnulacion(){
       try {
            root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Anulación.fxml"));            
            scene.setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ScenesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public void LoadSceneAbonos(){
       try {
            root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Abonos.fxml"));            
            scene.setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ScenesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public void LoadSceneVales(){
       try {
            root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Vales.fxml"));            
            scene.setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ScenesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public void LoadSceneFacturacion(){
       try {
            root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Facturación.fxml"));            
            scene.setRoot(root);
            
        } catch (IOException ex) {
            Logger.getLogger(ScenesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
}
