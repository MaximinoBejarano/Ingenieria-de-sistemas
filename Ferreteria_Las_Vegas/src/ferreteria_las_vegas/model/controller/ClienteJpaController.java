
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;
import ferreteria_las_vegas.model.entities.Cliente;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import ferreteria_las_vegas.utils.LoggerManager;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
/**
 *
 * @author wili
 */
public class ClienteJpaController {
     private static ClienteJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (ClienteJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new ClienteJpaController();
                }
            }
        }
    }

    public static ClienteJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
    
       /**
     * Metodo que consulta y devuelve una lista con todas las personas que hay
     * en la base de datos en la tabla tb_Personas.
     *
     * @return
     */
    public List<Cliente> ConsultarPersonasTodos() {
        try {
            Query qry = em.createNamedQuery("Cliente.findAll", Cliente.class);// consulta definida por folio
            List<Cliente> personas = qry.getResultList();// Recibe el resultado de la consulta  
            return personas;
        } catch (NoResultException ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
            return null;
        }
    }
    
        public Cliente AgregarCliente(Cliente pCliente) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(pCliente);
            et.commit();
            return pCliente;
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
     * Metodo que edita una persona(cliente) en la base de datos en tabla tb_Personas
     *
     * @param pPersona
     * @return
     */
        
    public Cliente ModificarCliente(Cliente pPersona) {
        et = em.getTransaction();
        try {
            et.begin();            
            em.merge(pPersona);
            et.commit();            
            return pPersona;
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

