/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;


import ferreteria_las_vegas.model.entities.ArticuloXCompra;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author sanwi
 */
public class ArticulosXCompraJpaController {
   private static ArticulosXCompraJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (ArticulosXCompraJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new ArticulosXCompraJpaController();
                }
            }
        }
    }

    public static ArticulosXCompraJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public ArticuloXCompra AgregarArticulosXCompra(ArticuloXCompra pArticulos) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(pArticulos);
            et.commit();
            return pArticulos;
        } catch (EntityExistsException ex) {
            et.rollback();
            System.err.println(ex);
            return null;
        } catch (Exception ex) {
            et.rollback();
            System.err.println(ex);
            return null;
        }
    }
    
    
    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
}
