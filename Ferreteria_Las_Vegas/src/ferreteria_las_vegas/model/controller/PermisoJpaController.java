/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Permiso;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 */
public class PermisoJpaController {
    private static PermisoJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (PermisoJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new PermisoJpaController();
                }
            }
        }
    }

    public static PermisoJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
    
    
    public List<Permiso> ConsultarPermisosTodos() {
        try {
            Query qry = em.createNamedQuery("Permiso.findAll", Permiso.class);
            List<Permiso> permisos = qry.getResultList();
            return permisos;
        } catch (NoResultException ex) {
            System.err.println(ex);
            return null;
        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }
    }    
    
    public List<Permiso> ConsultarPermisosTodoslol() {
        try {
            Query qry = em.createNamedQuery("Permiso.findAll", Permiso.class);
            List<Permiso> permisos = qry.getResultList();
            return permisos;
        } catch (NoResultException ex) {
            System.err.println(ex);
            return null;
        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }
    }
    
    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
}
