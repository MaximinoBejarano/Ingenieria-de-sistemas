/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.NotaCredito;
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
public class NotaCreditoJPAController {
   private static NotaCreditoJPAController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (NotaCreditoJPAController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new NotaCreditoJPAController();
                }
            }
        }
    }

    public static NotaCreditoJPAController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    /**
     * Metodo para insertar NotaCredito
     *
     * @param pParametro
     * @return parametro
     */
    public NotaCredito InsertarNotaCredito(NotaCredito pParametro) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(pParametro);
            et.commit();
            return pParametro;
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
     * Metodo para realizar la edicion de una Nota de credito
     *
     * @param pParametro 
     * @return
     */
    public NotaCredito ModificarNotaCredito(NotaCredito pNotaCredito) {
        et = em.getTransaction();
        try {
            et.begin();
            em.merge(pNotaCredito);
            et.commit();
            return pNotaCredito;
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
     * Procedimiento para consultar todas las notas de credito
     * BD_FV
     *
     * @return
     */
    public List<NotaCredito> Consultar_NotasCreditos() {
        try {
            Query qry = em.createNamedQuery("NotaCredito.findAll",NotaCredito.class);
            List<NotaCredito> listNotaCreditos = qry.getResultList();// Recibe el resultado de la consulta  
            return listNotaCreditos;
        } catch (NoResultException ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        }
    }

    /**
     * Consulta por codigo
     *
     * @param pCodigo
     * @return
     */
    public NotaCredito ConsultarNotaCredito(int pCodigo) {
        try {
            Query qry = em.createNamedQuery("NotaCredito.findByNotCodigo", NotaCredito.class);// consulta definida 
            qry.setParameter("notCodigo", pCodigo);
            NotaCredito pParametro = (NotaCredito) qry.getSingleResult();// trae el resultado de la consulta  
            return pParametro;
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
 //******************************************************************************************
    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;  
}
