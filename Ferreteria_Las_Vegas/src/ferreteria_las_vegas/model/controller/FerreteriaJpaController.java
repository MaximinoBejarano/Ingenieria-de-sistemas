/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Ferreteria;
import ferreteria_las_vegas.model.entities.Parametro;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import ferreteria_las_vegas.utils.LoggerManager;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author johan
 */
public class FerreteriaJpaController {
    private static FerreteriaJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (FerreteriaJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new FerreteriaJpaController();
                }
            }
        }
    }

    public static FerreteriaJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
    
    public Ferreteria ModificarFerreteriaParametro(Ferreteria pFerreteria, Parametro pParametro) {
        et = em.getTransaction();
        try {            
            et.begin();
            
            em.merge(pParametro);
            em.flush();
            pFerreteria.setParametro(pParametro);
            
            em.merge(pFerreteria);
            et.commit();
            return pFerreteria;
        } catch (EntityExistsException ex) {
            et.rollback();
            LoggerManager.Logger().info(ex.toString());
            return null;
        } catch (Exception ex) {
            et.rollback();
            LoggerManager.Logger().info(ex.toString());
            return null;
        }
    }
    
    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
}
