/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Articulo;
import ferreteria_las_vegas.model.entities.Inventario;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author wili
 */
public class InventarioJpaController {
  private static InventarioJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized ( InventarioJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new  InventarioJpaController();
                }
            }
        }
    }

    public static  InventarioJpaController getInstance() {
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
     * @param Art
     * @return articulo
     */

    public Articulo InsertarArticulo(Articulo Art) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(Art);
            et.commit();
            return Art;
        } catch (Exception ex) {
            et.rollback();
            System.err.println(ex);
            return null;
        }
    }

    /**
     * Metodo para realizar la edicion de articulos
     *
     * @param pArticulo
     * @return
     */
    public Articulo ModificarArticulos(Articulo pArticulo) {
        et = em.getTransaction();
        try {
            et.begin();
            em.merge(pArticulo);
            et.commit();
            return pArticulo;
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
    public List<Inventario> ConsultarInventario() {
        try {
            Query qry = em.createNamedQuery("Inventario.findAll", Inventario.class);// consulta todos los articulos 
            List<Inventario> inventario = qry.getResultList();// Recibe el resultado de la consulta  
            return inventario;
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
    public  Inventario ConsultarInventarioCodigoProducto(int pCodigo) {
        try {
            Query qry = em.createNamedQuery("Inventario.findByInvCodArticulo", Inventario.class);// consulta definida 
            qry.setParameter("invA", pCodigo);
            Inventario articulo = (Inventario) qry.getSingleResult();// trae el resultado de la consulta  
            return articulo;
        } catch (Exception ex) {
            return null;
        }
    }

    //******************************************************************************************
}
