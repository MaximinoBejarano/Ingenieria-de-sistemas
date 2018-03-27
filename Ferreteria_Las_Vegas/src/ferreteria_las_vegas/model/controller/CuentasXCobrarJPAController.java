/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.utils.EntityManagerHelper;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import ferreteria_las_vegas.model.entities.CuentaXCobrar;
import javax.persistence.EntityExistsException;

/**
 *
 * @author Maximino
 */
public class CuentasXCobrarJPAController {
        private static CuentasXCobrarJPAController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (CuentasXCobrarJPAController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new CuentasXCobrarJPAController();
                }
            }
        }
    }

    public static CuentasXCobrarJPAController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
  //***************************** Area de Metodos***********************************
    /**
     * Se agrega una nueva cuenta por cobrar
     * @param Cuenta
     * @return 
     */
   public CuentaXCobrar AgregarCuentaXCobrar(CuentaXCobrar Cuenta){
    et = em.getTransaction();
        try {
            et.begin();
            em.persist(Cuenta);
            et.commit();
            return Cuenta;
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
}
