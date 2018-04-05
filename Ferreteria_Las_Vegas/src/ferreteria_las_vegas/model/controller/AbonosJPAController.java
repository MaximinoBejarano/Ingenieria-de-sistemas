/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Abono;

import ferreteria_las_vegas.utils.EntityManagerHelper;
import ferreteria_las_vegas.utils.LoggerManager;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
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

    //***************************** Area de Metodos***********************************
    /**
     * Se agrega un nuevo registro a la base de datos
     *
     * @param pAbono
     * @return
     */
    public Abono AgregarAbono(Abono pAbono) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(pAbono);
            et.commit();
            return pAbono;
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

    /**
     * Metodo para realizar la modificación de un registro de abono
     *
     * @param pAbono
     * @return
     */
    public Abono ModificarAbono(Abono pAbono) {
        et = em.getTransaction();
        try {
            et.begin();
            em.merge(pAbono);
            et.commit();
            return pAbono;
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
        } catch (NoResultException ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        }
    }

    public Abono ConsultarAbono_Codigo(int pCodigo) {
        try {
            Query qry = em.createNamedQuery("Abono.findByAboCodigo", Abono.class);// consulta definida 
            qry.setParameter("aboCodigo", pCodigo);
            Abono pAbono = (Abono) qry.getSingleResult();// trae el resultado de la consulta  
            return pAbono;
        } catch (NoResultException ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        } catch (NonUniqueResultException ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        }
    }
    
    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
}
