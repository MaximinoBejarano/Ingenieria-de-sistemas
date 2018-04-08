/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Factura;
import ferreteria_las_vegas.model.entities.ArticuloXFactura;
import ferreteria_las_vegas.model.entities.Pago;

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
public class FacturaJPAController {

    private static FacturaJPAController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (FacturaJPAController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new FacturaJPAController();
                }
            }
        }
    }

    public static FacturaJPAController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    //***************************** Area de Metodos***********************************
    /**
     * Se agrega un nuevo registro a la base de datos
     *
     * @param pFactura
     * @return
     */
    public Factura AgregarFactura(Factura pFactura, List<ArticuloXFactura> ListArticuloXFacturas, List<Pago> ListPago) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(pFactura);
            em.flush();
         
            if (!ListArticuloXFacturas.isEmpty()) {
                for (ArticuloXFactura Objeto : ListArticuloXFacturas) {
                    em.persist(Objeto);
                    em.flush();
                    pFactura.getArticuloXFacturaList().add(Objeto);
                }
            }
            if (!ListPago.isEmpty()) {
                for (Pago Objeto : ListPago) {
                    if (Objeto.getPagMonto() != 0) {
                        em.persist(Objeto);
                        em.flush();
                        pFactura.getPagoList().add(Objeto);
                    }
                }
            }
            em.merge(pFactura);
            et.commit();
            return pFactura;

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
     * Metodo para realizar la modificación de un registro de Factura
     *
     * @param pFactura
     * @return
     */
    public Factura ModificarFactura(Factura pFactura) {
        et = em.getTransaction();
        try {
            et.begin();
            em.merge(pFactura);
            et.commit();
            return pFactura;
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
     * Se realiza la consulta todos las Factura
     *
     * @return List
     */
    public List<Factura> ConsultarFacturas() {
        try {
            Query qry = em.createNamedQuery("Factura.findAll", Factura.class);// consulta todos las Facturas
            List<Factura> listfacturas = qry.getResultList();// Recibe el resultado de la consulta  
            return listfacturas;
        } catch (NoResultException ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        }
    }

    /**
     * Realiza la consulta de una factura por medio de su codigo
     *
     * @param pCodigo
     * @return
     */
    public Factura ConsultarFactura_Codigo(int pCodigo) {
        try {
            Query qry = em.createNamedQuery("Factura.findByFacCodigo", Factura.class);// consulta definida 
            qry.setParameter("facCodigo", pCodigo);
            Factura pFactura = (Factura) qry.getSingleResult();// trae el resultado de la consulta  
            return pFactura;
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
