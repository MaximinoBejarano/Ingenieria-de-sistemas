/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;
import ferreteria_las_vegas.model.entities.Abono;

import ferreteria_las_vegas.utils.EntityManagerHelper;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Maximino
 */
public class AbonosJPAController {
      private static AbonosJPAController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (AbonosJPAController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new AbonosJPAController();
                }
            }
        }
    }

    public static AbonosJPAController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
    //***************************** Area de Metodos***********************************
    /**
     * Se agrega un nuevo registro a la base de datos
     * @param Abono
     * @return 
     */
    public Abono AgregarAbono(Abono Abono) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(Abono);
            et.commit();
            return Abono;
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
    
     /**
     * Metodo para realizar la modificación de un registro de abono
     *
     * @param pAbono 
     * @return
     */
    public Abono ModificarAbono (Abono pAbono) {
        et = em.getTransaction();
        try {
            et.begin();
            em.merge(pAbono);
            et.commit();
            return pAbono;
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
     /**
     * Se realiza la consulta todos los abonos
     *
     * @return
     */
    public List<Abono> ConsultarAbonos() {
        try {
            Query qry = em.createNamedQuery("Abono.findAll", Abono.class);// consulta todos los abonos
            List<Abono> Abonos = qry.getResultList();// Recibe el resultado de la consulta  
            return Abonos;
        } catch (Exception ex) {
            return null;
        }
    }
}
