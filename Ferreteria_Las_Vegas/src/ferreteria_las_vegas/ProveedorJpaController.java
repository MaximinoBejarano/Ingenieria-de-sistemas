/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas;

import ferreteria_las_vegas.exceptions.IllegalOrphanException;
import ferreteria_las_vegas.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ferreteria_las_vegas.model.entities.Contacto;
import java.util.ArrayList;
import java.util.List;
import ferreteria_las_vegas.model.entities.Direccion;
import ferreteria_las_vegas.model.entities.CuentaXPagar;
import ferreteria_las_vegas.model.entities.Compra;
import ferreteria_las_vegas.model.entities.Proveedor;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sanwi
 */
public class ProveedorJpaController implements Serializable {

    public ProveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) {
        if (proveedor.getContactoList() == null) {
            proveedor.setContactoList(new ArrayList<Contacto>());
        }
        if (proveedor.getDireccionList() == null) {
            proveedor.setDireccionList(new ArrayList<Direccion>());
        }
        if (proveedor.getCuentaXPagarList() == null) {
            proveedor.setCuentaXPagarList(new ArrayList<CuentaXPagar>());
        }
        if (proveedor.getCompraList() == null) {
            proveedor.setCompraList(new ArrayList<Compra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Contacto> attachedContactoList = new ArrayList<Contacto>();
            for (Contacto contactoListContactoToAttach : proveedor.getContactoList()) {
                contactoListContactoToAttach = em.getReference(contactoListContactoToAttach.getClass(), contactoListContactoToAttach.getConCodigo());
                attachedContactoList.add(contactoListContactoToAttach);
            }
            proveedor.setContactoList(attachedContactoList);
            List<Direccion> attachedDireccionList = new ArrayList<Direccion>();
            for (Direccion direccionListDireccionToAttach : proveedor.getDireccionList()) {
                direccionListDireccionToAttach = em.getReference(direccionListDireccionToAttach.getClass(), direccionListDireccionToAttach.getDirCodigo());
                attachedDireccionList.add(direccionListDireccionToAttach);
            }
            proveedor.setDireccionList(attachedDireccionList);
            List<CuentaXPagar> attachedCuentaXPagarList = new ArrayList<CuentaXPagar>();
            for (CuentaXPagar cuentaXPagarListCuentaXPagarToAttach : proveedor.getCuentaXPagarList()) {
                cuentaXPagarListCuentaXPagarToAttach = em.getReference(cuentaXPagarListCuentaXPagarToAttach.getClass(), cuentaXPagarListCuentaXPagarToAttach.getCueCodigo());
                attachedCuentaXPagarList.add(cuentaXPagarListCuentaXPagarToAttach);
            }
            proveedor.setCuentaXPagarList(attachedCuentaXPagarList);
            List<Compra> attachedCompraList = new ArrayList<Compra>();
            for (Compra compraListCompraToAttach : proveedor.getCompraList()) {
                compraListCompraToAttach = em.getReference(compraListCompraToAttach.getClass(), compraListCompraToAttach.getComCodigo());
                attachedCompraList.add(compraListCompraToAttach);
            }
            proveedor.setCompraList(attachedCompraList);
            em.persist(proveedor);
            for (Contacto contactoListContacto : proveedor.getContactoList()) {
                contactoListContacto.getProveedorList().add(proveedor);
                contactoListContacto = em.merge(contactoListContacto);
            }
            for (Direccion direccionListDireccion : proveedor.getDireccionList()) {
                direccionListDireccion.getProveedorList().add(proveedor);
                direccionListDireccion = em.merge(direccionListDireccion);
            }
            for (CuentaXPagar cuentaXPagarListCuentaXPagar : proveedor.getCuentaXPagarList()) {
                Proveedor oldCueProveedorOfCuentaXPagarListCuentaXPagar = cuentaXPagarListCuentaXPagar.getCueProveedor();
                cuentaXPagarListCuentaXPagar.setCueProveedor(proveedor);
                cuentaXPagarListCuentaXPagar = em.merge(cuentaXPagarListCuentaXPagar);
                if (oldCueProveedorOfCuentaXPagarListCuentaXPagar != null) {
                    oldCueProveedorOfCuentaXPagarListCuentaXPagar.getCuentaXPagarList().remove(cuentaXPagarListCuentaXPagar);
                    oldCueProveedorOfCuentaXPagarListCuentaXPagar = em.merge(oldCueProveedorOfCuentaXPagarListCuentaXPagar);
                }
            }
            for (Compra compraListCompra : proveedor.getCompraList()) {
                Proveedor oldComProveedorOfCompraListCompra = compraListCompra.getComProveedor();
                compraListCompra.setComProveedor(proveedor);
                compraListCompra = em.merge(compraListCompra);
                if (oldComProveedorOfCompraListCompra != null) {
                    oldComProveedorOfCompraListCompra.getCompraList().remove(compraListCompra);
                    oldComProveedorOfCompraListCompra = em.merge(oldComProveedorOfCompraListCompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getProCodigo());
            List<Contacto> contactoListOld = persistentProveedor.getContactoList();
            List<Contacto> contactoListNew = proveedor.getContactoList();
            List<Direccion> direccionListOld = persistentProveedor.getDireccionList();
            List<Direccion> direccionListNew = proveedor.getDireccionList();
            List<CuentaXPagar> cuentaXPagarListOld = persistentProveedor.getCuentaXPagarList();
            List<CuentaXPagar> cuentaXPagarListNew = proveedor.getCuentaXPagarList();
            List<Compra> compraListOld = persistentProveedor.getCompraList();
            List<Compra> compraListNew = proveedor.getCompraList();
            List<String> illegalOrphanMessages = null;
            for (CuentaXPagar cuentaXPagarListOldCuentaXPagar : cuentaXPagarListOld) {
                if (!cuentaXPagarListNew.contains(cuentaXPagarListOldCuentaXPagar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CuentaXPagar " + cuentaXPagarListOldCuentaXPagar + " since its cueProveedor field is not nullable.");
                }
            }
            for (Compra compraListOldCompra : compraListOld) {
                if (!compraListNew.contains(compraListOldCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Compra " + compraListOldCompra + " since its comProveedor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Contacto> attachedContactoListNew = new ArrayList<Contacto>();
            for (Contacto contactoListNewContactoToAttach : contactoListNew) {
                contactoListNewContactoToAttach = em.getReference(contactoListNewContactoToAttach.getClass(), contactoListNewContactoToAttach.getConCodigo());
                attachedContactoListNew.add(contactoListNewContactoToAttach);
            }
            contactoListNew = attachedContactoListNew;
            proveedor.setContactoList(contactoListNew);
            List<Direccion> attachedDireccionListNew = new ArrayList<Direccion>();
            for (Direccion direccionListNewDireccionToAttach : direccionListNew) {
                direccionListNewDireccionToAttach = em.getReference(direccionListNewDireccionToAttach.getClass(), direccionListNewDireccionToAttach.getDirCodigo());
                attachedDireccionListNew.add(direccionListNewDireccionToAttach);
            }
            direccionListNew = attachedDireccionListNew;
            proveedor.setDireccionList(direccionListNew);
            List<CuentaXPagar> attachedCuentaXPagarListNew = new ArrayList<CuentaXPagar>();
            for (CuentaXPagar cuentaXPagarListNewCuentaXPagarToAttach : cuentaXPagarListNew) {
                cuentaXPagarListNewCuentaXPagarToAttach = em.getReference(cuentaXPagarListNewCuentaXPagarToAttach.getClass(), cuentaXPagarListNewCuentaXPagarToAttach.getCueCodigo());
                attachedCuentaXPagarListNew.add(cuentaXPagarListNewCuentaXPagarToAttach);
            }
            cuentaXPagarListNew = attachedCuentaXPagarListNew;
            proveedor.setCuentaXPagarList(cuentaXPagarListNew);
            List<Compra> attachedCompraListNew = new ArrayList<Compra>();
            for (Compra compraListNewCompraToAttach : compraListNew) {
                compraListNewCompraToAttach = em.getReference(compraListNewCompraToAttach.getClass(), compraListNewCompraToAttach.getComCodigo());
                attachedCompraListNew.add(compraListNewCompraToAttach);
            }
            compraListNew = attachedCompraListNew;
            proveedor.setCompraList(compraListNew);
            proveedor = em.merge(proveedor);
            for (Contacto contactoListOldContacto : contactoListOld) {
                if (!contactoListNew.contains(contactoListOldContacto)) {
                    contactoListOldContacto.getProveedorList().remove(proveedor);
                    contactoListOldContacto = em.merge(contactoListOldContacto);
                }
            }
            for (Contacto contactoListNewContacto : contactoListNew) {
                if (!contactoListOld.contains(contactoListNewContacto)) {
                    contactoListNewContacto.getProveedorList().add(proveedor);
                    contactoListNewContacto = em.merge(contactoListNewContacto);
                }
            }
            for (Direccion direccionListOldDireccion : direccionListOld) {
                if (!direccionListNew.contains(direccionListOldDireccion)) {
                    direccionListOldDireccion.getProveedorList().remove(proveedor);
                    direccionListOldDireccion = em.merge(direccionListOldDireccion);
                }
            }
            for (Direccion direccionListNewDireccion : direccionListNew) {
                if (!direccionListOld.contains(direccionListNewDireccion)) {
                    direccionListNewDireccion.getProveedorList().add(proveedor);
                    direccionListNewDireccion = em.merge(direccionListNewDireccion);
                }
            }
            for (CuentaXPagar cuentaXPagarListNewCuentaXPagar : cuentaXPagarListNew) {
                if (!cuentaXPagarListOld.contains(cuentaXPagarListNewCuentaXPagar)) {
                    Proveedor oldCueProveedorOfCuentaXPagarListNewCuentaXPagar = cuentaXPagarListNewCuentaXPagar.getCueProveedor();
                    cuentaXPagarListNewCuentaXPagar.setCueProveedor(proveedor);
                    cuentaXPagarListNewCuentaXPagar = em.merge(cuentaXPagarListNewCuentaXPagar);
                    if (oldCueProveedorOfCuentaXPagarListNewCuentaXPagar != null && !oldCueProveedorOfCuentaXPagarListNewCuentaXPagar.equals(proveedor)) {
                        oldCueProveedorOfCuentaXPagarListNewCuentaXPagar.getCuentaXPagarList().remove(cuentaXPagarListNewCuentaXPagar);
                        oldCueProveedorOfCuentaXPagarListNewCuentaXPagar = em.merge(oldCueProveedorOfCuentaXPagarListNewCuentaXPagar);
                    }
                }
            }
            for (Compra compraListNewCompra : compraListNew) {
                if (!compraListOld.contains(compraListNewCompra)) {
                    Proveedor oldComProveedorOfCompraListNewCompra = compraListNewCompra.getComProveedor();
                    compraListNewCompra.setComProveedor(proveedor);
                    compraListNewCompra = em.merge(compraListNewCompra);
                    if (oldComProveedorOfCompraListNewCompra != null && !oldComProveedorOfCompraListNewCompra.equals(proveedor)) {
                        oldComProveedorOfCompraListNewCompra.getCompraList().remove(compraListNewCompra);
                        oldComProveedorOfCompraListNewCompra = em.merge(oldComProveedorOfCompraListNewCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proveedor.getProCodigo();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getProCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CuentaXPagar> cuentaXPagarListOrphanCheck = proveedor.getCuentaXPagarList();
            for (CuentaXPagar cuentaXPagarListOrphanCheckCuentaXPagar : cuentaXPagarListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the CuentaXPagar " + cuentaXPagarListOrphanCheckCuentaXPagar + " in its cuentaXPagarList field has a non-nullable cueProveedor field.");
            }
            List<Compra> compraListOrphanCheck = proveedor.getCompraList();
            for (Compra compraListOrphanCheckCompra : compraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the Compra " + compraListOrphanCheckCompra + " in its compraList field has a non-nullable comProveedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Contacto> contactoList = proveedor.getContactoList();
            for (Contacto contactoListContacto : contactoList) {
                contactoListContacto.getProveedorList().remove(proveedor);
                contactoListContacto = em.merge(contactoListContacto);
            }
            List<Direccion> direccionList = proveedor.getDireccionList();
            for (Direccion direccionListDireccion : direccionList) {
                direccionListDireccion.getProveedorList().remove(proveedor);
                direccionListDireccion = em.merge(direccionListDireccion);
            }
            em.remove(proveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Proveedor findProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
