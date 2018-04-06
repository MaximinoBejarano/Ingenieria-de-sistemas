/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.ArticuloXCompra;
import ferreteria_las_vegas.model.entities.DetalleInventario;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import ferreteria_las_vegas.utils.LoggerManager;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author sanwi
 */
public class DetalleInventarioJpaController {

    private static DetalleInventarioJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (DetalleInventarioJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new DetalleInventarioJpaController();
                }
            }
        }
    }

    public static DetalleInventarioJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public DetalleInventario AgregarDetalleInventario(DetalleInventario pDetalleInventario) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(pDetalleInventario);
            et.commit();
            return pDetalleInventario;
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

    public ArticuloXCompra ModificarArticuloXCompra(ArticuloXCompra pArticuloXCompra) {
        et = em.getTransaction();
        try {
            et.begin();
            em.merge(pArticuloXCompra);
            et.commit();
            return pArticuloXCompra;
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

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
}
