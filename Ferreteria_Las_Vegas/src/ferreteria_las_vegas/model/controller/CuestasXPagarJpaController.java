/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.CuentaXPagar;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author johan
 */

public class CuestasXPagarJpaController {
    private static CuestasXPagarJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (CuestasXPagarJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new CuestasXPagarJpaController();
                }
            }
        }
    }

    public static CuestasXPagarJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
 
    public CuentaXPagar AgregarCuentaXCobrar(CuentaXPagar pCuenta) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(pCuenta);
            et.commit();
            return pCuenta;
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
    
    public List<CuentaXPagar> ConsultarCuentasXPagar() {
        try {
            Query qry = em.createQuery("SELECT c FROM CuentaXPagar c", CuentaXPagar.class);
            List<CuentaXPagar> cuentas = qry.getResultList();
            return cuentas;
        } catch (Exception ex) {
            return null;
        }
    }
    
    public CuentaXPagar ModificarCuentaXPagar(CuentaXPagar pCuenta) {
        et = em.getTransaction();
        try {            
            et.begin();
            em.merge(pCuenta);
            et.commit();
            return pCuenta;
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
    
    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
}

/* No Borrar

*CuentaXPagar
@JoinTable(name = "tb_cxp_abonos", joinColumns = {
        @JoinColumn(name = "RCA_CuentaPagar", referencedColumnName = "Cue_Codigo")}, inverseJoinColumns = {
        @JoinColumn(name = "RCA_Abono", referencedColumnName = "Abo_Codigo")})
    @ManyToMany    
    private List<Abono> abonoList;

*Abono
@ManyToMany(mappedBy = "abonoList")
    private List<CuentaXPagar> cuentaXPagarList;
*/