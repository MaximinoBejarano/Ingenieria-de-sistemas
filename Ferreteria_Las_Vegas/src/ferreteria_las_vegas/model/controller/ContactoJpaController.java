/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.utils.EntityManagerHelper;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Usuario
 * Singlenton para gestion de las operaciones sobre la entidad Contacto (tb_Contactos)
 */
public class ContactoJpaController {
    private static ContactoJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {
            
            synchronized (ContactoJpaController.class) {
                
                if (INSTANCE == null) {
                    INSTANCE = new ContactoJpaController();
                }
            }
        }
    }
  
    public static ContactoJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
    
    
    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
}
