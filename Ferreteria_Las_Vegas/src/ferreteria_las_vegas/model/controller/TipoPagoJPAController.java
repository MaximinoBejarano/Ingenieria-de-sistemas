/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.TipoPago;
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
public class TipoPagoJPAController {
    private static TipoPagoJPAController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (AbonosJPAController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new TipoPagoJPAController();
                }
            }
        }
    }

    public static TipoPagoJPAController getInstance() {
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
     * @param pTPago 
     * @return 
     */
    public TipoPago AgregarTipoPago(TipoPago pTPago) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(pTPago);
            et.commit();
            return pTPago;
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
     * Metodo para realizar la modificación de un registro del tipo de pago
     *
     * @param pTipoPago  
     * @return
     */
    public TipoPago Modificar_TipoPago (TipoPago pTipoPago) {
        et = em.getTransaction();
        try {
            et.begin();
            em.merge(pTipoPago);
            et.commit();
            return pTipoPago;
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
    public List<TipoPago> Consultar_TiposPagos() {
        try {
            Query qry = em.createQuery("SELECT t FROM TipoPago t", TipoPago.class);// consulta todos los abonos
            List<TipoPago> ListTipoPago = qry.getResultList();// Recibe el resultado de la consulta  
            return ListTipoPago;
        } catch (Exception ex) {
            return null;
        }
    }
    
     /**
     * Consulta por codigo del tipoPago
     *
     * @param pTipoPago 
     * @return
     */
    public TipoPago Consultar_TipoPagoCodigo(int pTipoPago) {
        try {
            Query qry = em.createNamedQuery("TipoPago.findByTipCodigo", TipoPago.class);// consulta definida 
            qry.setParameter("tipCodigo", pTipoPago);
            TipoPago pTPago = (TipoPago) qry.getSingleResult();// trae el resultado de la consulta  
            return pTPago;
        } catch (Exception ex) {
            return null;
        }
    }
    
     /**
     * Consulta por Nombre del tipoPago
     *
     * @param pTipNombre 
     * @return
     */
    public TipoPago Consultar_TipoPagoCodigo(String pTipNombre) {
        try {
            Query qry = em.createNamedQuery("TipoPago.findByTipNombre", TipoPago.class);// consulta definida 
            qry.setParameter("tipNombre", pTipNombre);
            TipoPago pTPago = (TipoPago) qry.getSingleResult();// trae el resultado de la consulta  
            return pTPago;
        } catch (Exception ex) {
            return null;
        }
    }
    
}
