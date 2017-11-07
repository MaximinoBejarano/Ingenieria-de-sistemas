/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import ferreteria_las_vegas.model.entities.Usuario;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Scene;

/**
 *
 * @author Usuario
 */
public class PermisosManager {
    private static PermisosManager INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {
            
            synchronized (PermisosManager.class) {
                
                if (INSTANCE == null) {
                    INSTANCE = new PermisosManager();
                }
            }
        }
    }
  
    public static PermisosManager getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
    
    
    void Menu(Scene scene, Usuario usuario)
    {
        
    }
    
    
}
