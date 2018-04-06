
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Articulo;
import ferreteria_las_vegas.model.entities.DetalleInventario;
import ferreteria_las_vegas.model.entities.Inventario;
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

    public Inventario InsertarInvetario(Inventario Art, DetalleInventario Det) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(Art);
            em.flush();
            
            if(Det!=null){
            em.persist(Det);
            em.flush();
            Art.getDetalleInventarioList().add(Det);
            }
            em.merge(Art);
            et.commit();
            return Art;
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
     * Metodo para realizar la edicion de articulos
     *
     * @param pInventario
     *
     * @return
     */
    public Inventario ModificarInventario(Inventario pInventario) {
         et = em.getTransaction();
        try {            
            et.begin();
            em.merge(pInventario);
            et.commit();
            return pInventario;
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
        } catch (NoResultException ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
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
            qry.setParameter("invArticulo", pCodigo);
            Inventario articulo = (Inventario) qry.getSingleResult();// trae el resultado de la consulta  
            return articulo;
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
}
