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
import ferreteria_las_vegas.model.entities.Proveedor;
import ferreteria_las_vegas.model.entities.CuentaXPagar;
import java.util.ArrayList;
import java.util.List;
import ferreteria_las_vegas.model.entities.ArticuloXCompra;
import ferreteria_las_vegas.model.entities.Compra;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sanwi
 */
public class CompraJpaController implements Serializable {

    public CompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compra compra) {
        if (compra.getCuentaXPagarList() == null) {
            compra.setCuentaXPagarList(new ArrayList<CuentaXPagar>());
        }
        if (compra.getArticuloXCompraList() == null) {
            compra.setArticuloXCompraList(new ArrayList<ArticuloXCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor comProveedor = compra.getComProveedor();
            if (comProveedor != null) {
                comProveedor = em.getReference(comProveedor.getClass(), comProveedor.getProCodigo());
                compra.setComProveedor(comProveedor);
            }
            List<CuentaXPagar> attachedCuentaXPagarList = new ArrayList<CuentaXPagar>();
            for (CuentaXPagar cuentaXPagarListCuentaXPagarToAttach : compra.getCuentaXPagarList()) {
                cuentaXPagarListCuentaXPagarToAttach = em.getReference(cuentaXPagarListCuentaXPagarToAttach.getClass(), cuentaXPagarListCuentaXPagarToAttach.getCueCodigo());
                attachedCuentaXPagarList.add(cuentaXPagarListCuentaXPagarToAttach);
            }
            compra.setCuentaXPagarList(attachedCuentaXPagarList);
            List<ArticuloXCompra> attachedArticuloXCompraList = new ArrayList<ArticuloXCompra>();
            for (ArticuloXCompra articuloXCompraListArticuloXCompraToAttach : compra.getArticuloXCompraList()) {
                articuloXCompraListArticuloXCompraToAttach = em.getReference(articuloXCompraListArticuloXCompraToAttach.getClass(), articuloXCompraListArticuloXCompraToAttach.getArtCodigo());
                attachedArticuloXCompraList.add(articuloXCompraListArticuloXCompraToAttach);
            }
            compra.setArticuloXCompraList(attachedArticuloXCompraList);
            em.persist(compra);
            if (comProveedor != null) {
                comProveedor.getCompraList().add(compra);
                comProveedor = em.merge(comProveedor);
            }
            for (CuentaXPagar cuentaXPagarListCuentaXPagar : compra.getCuentaXPagarList()) {
                Compra oldCueCompraOfCuentaXPagarListCuentaXPagar = cuentaXPagarListCuentaXPagar.getCueCompra();
                cuentaXPagarListCuentaXPagar.setCueCompra(compra);
                cuentaXPagarListCuentaXPagar = em.merge(cuentaXPagarListCuentaXPagar);
                if (oldCueCompraOfCuentaXPagarListCuentaXPagar != null) {
                    oldCueCompraOfCuentaXPagarListCuentaXPagar.getCuentaXPagarList().remove(cuentaXPagarListCuentaXPagar);
                    oldCueCompraOfCuentaXPagarListCuentaXPagar = em.merge(oldCueCompraOfCuentaXPagarListCuentaXPagar);
                }
            }
            for (ArticuloXCompra articuloXCompraListArticuloXCompra : compra.getArticuloXCompraList()) {
                Compra oldArtCompraOfArticuloXCompraListArticuloXCompra = articuloXCompraListArticuloXCompra.getArtCompra();
                articuloXCompraListArticuloXCompra.setArtCompra(compra);
                articuloXCompraListArticuloXCompra = em.merge(articuloXCompraListArticuloXCompra);
                if (oldArtCompraOfArticuloXCompraListArticuloXCompra != null) {
                    oldArtCompraOfArticuloXCompraListArticuloXCompra.getArticuloXCompraList().remove(articuloXCompraListArticuloXCompra);
                    oldArtCompraOfArticuloXCompraListArticuloXCompra = em.merge(oldArtCompraOfArticuloXCompraListArticuloXCompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compra compra) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra persistentCompra = em.find(Compra.class, compra.getComCodigo());
            Proveedor comProveedorOld = persistentCompra.getComProveedor();
            Proveedor comProveedorNew = compra.getComProveedor();
            List<CuentaXPagar> cuentaXPagarListOld = persistentCompra.getCuentaXPagarList();
            List<CuentaXPagar> cuentaXPagarListNew = compra.getCuentaXPagarList();
            List<ArticuloXCompra> articuloXCompraListOld = persistentCompra.getArticuloXCompraList();
            List<ArticuloXCompra> articuloXCompraListNew = compra.getArticuloXCompraList();
            List<String> illegalOrphanMessages = null;
            for (CuentaXPagar cuentaXPagarListOldCuentaXPagar : cuentaXPagarListOld) {
                if (!cuentaXPagarListNew.contains(cuentaXPagarListOldCuentaXPagar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CuentaXPagar " + cuentaXPagarListOldCuentaXPagar + " since its cueCompra field is not nullable.");
                }
            }
            for (ArticuloXCompra articuloXCompraListOldArticuloXCompra : articuloXCompraListOld) {
                if (!articuloXCompraListNew.contains(articuloXCompraListOldArticuloXCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ArticuloXCompra " + articuloXCompraListOldArticuloXCompra + " since its artCompra field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (comProveedorNew != null) {
                comProveedorNew = em.getReference(comProveedorNew.getClass(), comProveedorNew.getProCodigo());
                compra.setComProveedor(comProveedorNew);
            }
            List<CuentaXPagar> attachedCuentaXPagarListNew = new ArrayList<CuentaXPagar>();
            for (CuentaXPagar cuentaXPagarListNewCuentaXPagarToAttach : cuentaXPagarListNew) {
                cuentaXPagarListNewCuentaXPagarToAttach = em.getReference(cuentaXPagarListNewCuentaXPagarToAttach.getClass(), cuentaXPagarListNewCuentaXPagarToAttach.getCueCodigo());
                attachedCuentaXPagarListNew.add(cuentaXPagarListNewCuentaXPagarToAttach);
            }
            cuentaXPagarListNew = attachedCuentaXPagarListNew;
            compra.setCuentaXPagarList(cuentaXPagarListNew);
            List<ArticuloXCompra> attachedArticuloXCompraListNew = new ArrayList<ArticuloXCompra>();
            for (ArticuloXCompra articuloXCompraListNewArticuloXCompraToAttach : articuloXCompraListNew) {
                articuloXCompraListNewArticuloXCompraToAttach = em.getReference(articuloXCompraListNewArticuloXCompraToAttach.getClass(), articuloXCompraListNewArticuloXCompraToAttach.getArtCodigo());
                attachedArticuloXCompraListNew.add(articuloXCompraListNewArticuloXCompraToAttach);
            }
            articuloXCompraListNew = attachedArticuloXCompraListNew;
            compra.setArticuloXCompraList(articuloXCompraListNew);
            compra = em.merge(compra);
            if (comProveedorOld != null && !comProveedorOld.equals(comProveedorNew)) {
                comProveedorOld.getCompraList().remove(compra);
                comProveedorOld = em.merge(comProveedorOld);
            }
            if (comProveedorNew != null && !comProveedorNew.equals(comProveedorOld)) {
                comProveedorNew.getCompraList().add(compra);
                comProveedorNew = em.merge(comProveedorNew);
            }
            for (CuentaXPagar cuentaXPagarListNewCuentaXPagar : cuentaXPagarListNew) {
                if (!cuentaXPagarListOld.contains(cuentaXPagarListNewCuentaXPagar)) {
                    Compra oldCueCompraOfCuentaXPagarListNewCuentaXPagar = cuentaXPagarListNewCuentaXPagar.getCueCompra();
                    cuentaXPagarListNewCuentaXPagar.setCueCompra(compra);
                    cuentaXPagarListNewCuentaXPagar = em.merge(cuentaXPagarListNewCuentaXPagar);
                    if (oldCueCompraOfCuentaXPagarListNewCuentaXPagar != null && !oldCueCompraOfCuentaXPagarListNewCuentaXPagar.equals(compra)) {
                        oldCueCompraOfCuentaXPagarListNewCuentaXPagar.getCuentaXPagarList().remove(cuentaXPagarListNewCuentaXPagar);
                        oldCueCompraOfCuentaXPagarListNewCuentaXPagar = em.merge(oldCueCompraOfCuentaXPagarListNewCuentaXPagar);
                    }
                }
            }
            for (ArticuloXCompra articuloXCompraListNewArticuloXCompra : articuloXCompraListNew) {
                if (!articuloXCompraListOld.contains(articuloXCompraListNewArticuloXCompra)) {
                    Compra oldArtCompraOfArticuloXCompraListNewArticuloXCompra = articuloXCompraListNewArticuloXCompra.getArtCompra();
                    articuloXCompraListNewArticuloXCompra.setArtCompra(compra);
                    articuloXCompraListNewArticuloXCompra = em.merge(articuloXCompraListNewArticuloXCompra);
                    if (oldArtCompraOfArticuloXCompraListNewArticuloXCompra != null && !oldArtCompraOfArticuloXCompraListNewArticuloXCompra.equals(compra)) {
                        oldArtCompraOfArticuloXCompraListNewArticuloXCompra.getArticuloXCompraList().remove(articuloXCompraListNewArticuloXCompra);
                        oldArtCompraOfArticuloXCompraListNewArticuloXCompra = em.merge(oldArtCompraOfArticuloXCompraListNewArticuloXCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compra.getComCodigo();
                if (findCompra(id) == null) {
                    throw new NonexistentEntityException("The compra with id " + id + " no longer exists.");
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
            Compra compra;
            try {
                compra = em.getReference(Compra.class, id);
                compra.getComCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compra with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CuentaXPagar> cuentaXPagarListOrphanCheck = compra.getCuentaXPagarList();
            for (CuentaXPagar cuentaXPagarListOrphanCheckCuentaXPagar : cuentaXPagarListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Compra (" + compra + ") cannot be destroyed since the CuentaXPagar " + cuentaXPagarListOrphanCheckCuentaXPagar + " in its cuentaXPagarList field has a non-nullable cueCompra field.");
            }
            List<ArticuloXCompra> articuloXCompraListOrphanCheck = compra.getArticuloXCompraList();
            for (ArticuloXCompra articuloXCompraListOrphanCheckArticuloXCompra : articuloXCompraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Compra (" + compra + ") cannot be destroyed since the ArticuloXCompra " + articuloXCompraListOrphanCheckArticuloXCompra + " in its articuloXCompraList field has a non-nullable artCompra field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Proveedor comProveedor = compra.getComProveedor();
            if (comProveedor != null) {
                comProveedor.getCompraList().remove(compra);
                comProveedor = em.merge(comProveedor);
            }
            em.remove(compra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Compra> findCompraEntities() {
        return findCompraEntities(true, -1, -1);
    }

    public List<Compra> findCompraEntities(int maxResults, int firstResult) {
        return findCompraEntities(false, maxResults, firstResult);
    }

    private List<Compra> findCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compra.class));
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

    public Compra findCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compra.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compra> rt = cq.from(Compra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
