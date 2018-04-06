/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Abono;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import ferreteria_las_vegas.model.entities.CuentaXCobrar;
import ferreteria_las_vegas.utils.LoggerManager;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author Maximino
 */
public class CuentasXCobrarJPAController {

    private static CuentasXCobrarJPAController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (CuentasXCobrarJPAController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new CuentasXCobrarJPAController();
                }
            }
        }
    }

    public static CuentasXCobrarJPAController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }    

    //***************************** Area de Metodos***********************************
    /**
     * Se agrega una nueva cuenta por cobrar
     *
     * @param Cuenta
     * @return
     */
    public CuentaXCobrar AgregarCuentaXCobrar(CuentaXCobrar Cuenta) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(Cuenta);
            et.commit();
            return Cuenta;
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
     * Metodo para realizar la modificación de un registro de una cuenta por
     * cobrar con los abonos correspodientes
     *
     * @param pCuentaXCobrar
     * @param pAbono
     * @return
     */
    public CuentaXCobrar Modificar_CuentaXCobrar_Abonos(CuentaXCobrar pCuentaXCobrar, Abono pAbono) {
        et = em.getTransaction();
        try {
            et.begin();
            if (pAbono != null) {
                em.persist(pAbono);
                em.flush();
                pCuentaXCobrar.getAbonoList().add(pAbono);
                em.merge(pCuentaXCobrar);
                et.commit();
                return pCuentaXCobrar;
            }else{
             return null;
            }
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
     * Metodo para realizar la modificación de un registro de una cuenta por
     * cobrar
     *
     * @param pCuentaXCobrar
     * @return
     */
    public CuentaXCobrar Modificar_CuentaXCobrar(CuentaXCobrar pCuentaXCobrar) {
        et = em.getTransaction();
        try {
            et.begin();
            em.merge(pCuentaXCobrar);
            et.commit();
            return pCuentaXCobrar;
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
     * Se realiza la consulta de todas las cuentas por cobrar
     *
     * @return
     */
    public List<CuentaXCobrar> ConsultarCuentasXCobrar() {
        try {
            Query qry = em.createNamedQuery("CuentaXCobrar.findAll", CuentaXCobrar.class);// consulta todos las cuentas por cobrar
            List<CuentaXCobrar> Cuentas = qry.getResultList();// Recibe el resultado de la consulta  
            return Cuentas;
        } catch (NoResultException ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        }
    }

    /**
     * Consulta por codigo de la CuentaXCobrar
     *
     * @param pCodigo
     * @return
     */
    public CuentaXCobrar Consultar_CuentaXCobrarCodigo(int pCodigo) {
        try {
            Query qry = em.createNamedQuery("CuentaXCobrar.findByCueCodigo", CuentaXCobrar.class);// consulta definida 
            qry.setParameter("cueCodigo", pCodigo);
            CuentaXCobrar pCuenta = (CuentaXCobrar) qry.getSingleResult();// trae el resultado de la consulta  
            return pCuenta;
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

/* No borrar
*CuentaXCobrar
@JoinTable(name = "tb_cxc_abonos", joinColumns = {
        @JoinColumn(name = "RCA_CuentaCobrar", referencedColumnName = "Cue_Codigo")}, inverseJoinColumns = {
        @JoinColumn(name = "RCA_Abono", referencedColumnName = "Abo_Codigo")})
    @ManyToMany
    private List<Abono> abonoList;

*Abono
@ManyToMany(mappedBy = "abonoList")
    private List<CuentaXCobrar> cuentaXCobrarList;
 */
