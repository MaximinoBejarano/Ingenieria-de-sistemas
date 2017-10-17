/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;
import ferreteria_las_vegas.model.entities.Cliente;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
/**
 *
 * @author wili
 */
public class ClienteJpaController {
     private static ClienteJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (ClienteJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new ClienteJpaController();
                }
            }
        }
    }

    public static ClienteJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
    
    
    
    
    
}
