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
 * Singlenton para gestion de las operaciones sobre la entidad TipoContacto (tb_TiposContacto)
 */
public class TipoContactoJpaController {

    private static TipoContactoJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (TipoContactoJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new TipoContactoJpaController();
                }
            }
        }
    }

    public static TipoContactoJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
    
    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
}
