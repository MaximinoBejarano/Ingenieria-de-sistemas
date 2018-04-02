/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.ArticuloXCompra;
import ferreteria_las_vegas.model.entities.Compra;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author sanwi
 */
public class CompraJpaController {

    private static CompraJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (CompraJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new CompraJpaController();
                }
            }
        }
    }

    public static CompraJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    //**********************************Area de metodos **************************************************

    /**
     * Metodo para insertar articulo en la BD_FV
     *
     * @param Com
     * @return compra
     */
    public Compra InsertarCompra(Compra Com, List<ArticuloXCompra> Lista) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(Com);
            em.flush();
 
            if (!Lista.isEmpty()) {
                for (ArticuloXCompra Lista1 : Lista) {
                    em.persist(Lista1);
                    em.flush();
                    Com.getArticuloXCompraList().add(Lista1);    
                }
            }
            em.merge(Com );
            et.commit();
            return Com;
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
     * Metodo para realizar la edicion de articulos
     *
     * @param pCompra
     * @return
     */
    public Compra ModificarCompra(Compra pCompra) {
        et = em.getTransaction();
        try {
            et.begin();
            em.merge(pCompra);
            et.commit();
            return pCompra;
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
  public Compra ModificarCompra(Compra Com, List<ArticuloXCompra> Lista) {
        et = em.getTransaction();
        try {
            et.begin();
           
 
            if (!Lista.isEmpty()) {
                for (ArticuloXCompra Lista1 : Lista) {

                    Com.getArticuloXCompraList().add(Lista1);    
                }
            }
            em.merge(Com);
            et.commit();
            return Com;
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
    //***********************************Area de procedimientos*******************************
    /**
     * Procedimiento para consultar todos los articulos que se encuentren en la
     * BD_FV
     *
     * @return
     */
    public List<Compra> ConsultarCompras() {
        try {
            Query qry = em.createNamedQuery("Compra.findAll", Compra.class);// consulta todos los articulos 
            List<Compra> Compras = qry.getResultList();// Recibe el resultado de la consulta  
            return Compras;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Consulta por codigo del articulo
     *
     * @param pCodigo
     * @return
     */
    public Compra ConsultarCompraCodigoFac(String pCodigo) {
        try {
            Query qry = em.createNamedQuery("Compra.findByComNumeroFact", Compra.class);// consulta definida 
            qry.setParameter("comNumeroFact", pCodigo);
            Compra articulo = (Compra) qry.getSingleResult();// trae el resultado de la consulta  
            return articulo;
        } catch (Exception ex) {
            return null;
        }
    }

    //******************************************************************************************
}
