/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Parametro;
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
public class ParametroJPAController {
     private static ParametroJPAController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (ParametroJPAController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new ParametroJPAController();
                }
            }
        }
    }

    public static ParametroJPAController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    /**
     * Metodo para insertar parametro
     *
     * @param pParametro
     * @return parametro
     */
    public Parametro InsertarParametro(Parametro pParametro) {
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
     * Metodo para realizar la edicion un parametro
     *
     * @param pParametro 
     * @return
     */
    public Parametro ModificarParametro(Parametro pParametro) {
        et = em.getTransaction();
        try {
            et.begin();
            em.merge(pParametro);
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
     * Procedimiento para consultar todos los parametros
     * BD_FV
     *
     * @return
     */
    public List<Parametro> ConsultarParametros() {
        try {
            Query qry = em.createNamedQuery("Parametro.findAll",Parametro.class);
            List<Parametro> listParametros = qry.getResultList();// Recibe el resultado de la consulta  
            return listParametros;
        } catch (NoResultException ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        }
    }

    /**
     * Consulta por codigo ferreteria
     *
     * @param pCodigo
     * @return
     */
    public Parametro ConsultarParametro_Ferrteria(int pCodigo) {
        try {
            Query qry = em.createNamedQuery("Parametro.findByParFerreteria", Parametro.class);// consulta definida 
            qry.setParameter("parFerreteria", pCodigo);
            Parametro pParametro = (Parametro) qry.getSingleResult();// trae el resultado de la consulta  
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
