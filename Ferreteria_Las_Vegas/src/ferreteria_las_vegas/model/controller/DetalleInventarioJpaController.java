/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.DetalleInventario;
import ferreteria_las_vegas.model.entities.Persona;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Query;

/**
 *
 * @author sanwi
 */
public class DetalleInventarioJpaController {
  private static DetalleInventarioJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (DetalleInventarioJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new DetalleInventarioJpaController();
                }
            }
        }
    }

    public static DetalleInventarioJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }   
    
    public DetalleInventario AgregarDetalleInventario(DetalleInventario pDetalleInventario) {
         et = em.getTransaction();
        try {
            et.begin();
            em.persist(pDetalleInventario);
            et.commit();
            return pDetalleInventario;
        }catch (EntityExistsException ex) {
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
