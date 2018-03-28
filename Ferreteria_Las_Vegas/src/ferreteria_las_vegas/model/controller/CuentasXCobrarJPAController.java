/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Articulo;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import ferreteria_las_vegas.model.entities.CuentaXCobrar;
import java.util.List;
import javax.persistence.EntityExistsException;
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

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

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
            System.err.println(ex);
            return null;
        } catch (Exception ex) {
            et.rollback();
            System.err.println(ex);
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
            Query qry = em.createNamedQuery("CuentaXCobrar.findAll", Articulo.class);// consulta todos las cuentas por cobrar
            List<CuentaXCobrar> Cuentas = qry.getResultList();// Recibe el resultado de la consulta  
            return Cuentas;
        } catch (Exception ex) {
            return null;
        }
    }
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