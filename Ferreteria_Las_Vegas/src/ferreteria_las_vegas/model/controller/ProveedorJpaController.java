/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Contacto;
import ferreteria_las_vegas.model.entities.Direccion;
import ferreteria_las_vegas.model.entities.Proveedor;
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
 * @author johan
 */
public class ProveedorJpaController {

    private static ProveedorJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (ProveedorJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new ProveedorJpaController();
                }
            }
        }
    }

    public static ProveedorJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
    
    
    public List<Proveedor> ConsultarProveedoresTodos() {
        try {
            Query qry = em.createQuery("SELECT p FROM Proveedor p", Proveedor.class);
            List<Proveedor> proveedores = qry.getResultList();
            return proveedores;
        } catch (NoResultException ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        }
    }
    
    public Proveedor ConsultarProveedorCedulaJuridica(String pCedulaJuridica) {
        try {
            Query qry = em.createQuery("SELECT p FROM Proveedor p WHERE p.proCedulaJuridica = :proCedulaJuridica", Proveedor.class);// consulta definida 
            qry.setParameter("proCedulaJuridica", pCedulaJuridica);
            Proveedor proveedor = (Proveedor) qry.getSingleResult();
            return proveedor;
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
    
    public Proveedor ConsultarProvedorNombre(String pNombre) {
        try {
            Query qry = em.createQuery("SELECT p FROM Proveedor p WHERE p.proNombre = :proNombre", Proveedor.class);// consulta definida 
            qry.setParameter("proNombre", pNombre);
            Proveedor proveedor = (Proveedor) qry.getSingleResult();
            return proveedor;
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
    
    public Proveedor ModificarProveedor(Proveedor pProveedor) {
        et = em.getTransaction();
        try {            
            et.begin();
            em.merge(pProveedor);
            et.commit();
            return pProveedor;
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

    public Proveedor AgregarProveedor(Proveedor pProveedor, Direccion pDireccion, Contacto pTel1, Contacto pTel2, Contacto pEmail) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(pProveedor);
            em.flush();

            em.persist(pDireccion);
            em.flush();
            pProveedor.getDireccionList().add(pDireccion);

            if (pTel1 != null) {
                em.persist(pTel1);
                em.flush();
                pProveedor.getContactoList().add(pTel1);
            }
            if (pTel2 != null) {
                em.persist(pTel2);
                em.flush();
                pProveedor.getContactoList().add(pTel2);
            }
            if (pEmail != null) {
                em.persist(pEmail);
                em.flush();
                pProveedor.getContactoList().add(pEmail);
            }
            em.merge(pProveedor);
            et.commit();
            return pProveedor;
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


/* No Borrar

*Proveedores
@JoinTable(name = "tb_contactos_proveedores", joinColumns = {
        @JoinColumn(name = "RCP_Proveedor", referencedColumnName = "Pro_Codigo")}, inverseJoinColumns = {
        @JoinColumn(name = "RCP_Contacto", referencedColumnName = "Con_Codigo")})
    @ManyToMany
    private List<Contacto> contactoList;
            
    
    @JoinTable(name = "tb_direcciones_proveedores", joinColumns = {
        @JoinColumn(name = "RDP_Proveedor", referencedColumnName = "Pro_Codigo")}, inverseJoinColumns = {
        @JoinColumn(name = "RDP_Direccion", referencedColumnName = "Dir_Codigo")})
    @ManyToMany
    private List<Direccion> direccionList;

*Contacto
@ManyToMany(mappedBy = "contactoList")
    private List<Proveedor> proveedorList;

*Direccion
@ManyToMany(mappedBy = "direccionList")
    private List<Proveedor> proveedorList;

*/