/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Direccion;
import ferreteria_las_vegas.model.entities.Persona;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Query;

/**
 *
 * @author Usuario Singlenton para gestion de las operaciones sobre la entidad
 * Direccion (tb_Direcciones)
 */
public class DireccionJpaController {

    private static DireccionJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (DireccionJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new DireccionJpaController();
                }
            }
        }
    }

    public static DireccionJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    /**
     * Metodo que consulta y devuelve (en caso de existir) una direccion desde
     * la base de datos en la tabla tb_Direcciones mediante la persona a la que
     * le pertenece
     *
     * @param pPersona
     * @return
     */
    public Direccion ConsultarDireccionPersona(Persona pPersona) {
        try {
            Query qry = em.createNamedQuery("Persona.findByPerCedula", Persona.class);// consulta definida 
            qry.setParameter("perCedula", pPersona.getPerCedula());
            Persona persona = (Persona) qry.getSingleResult();// trae el resultado de la consulta  
            return persona.getDireccionList().get(1);
        } catch (Exception ex) {
            return null;
        }
    }

    
    
    
    public Direccion AgregarDireccion(Direccion pDireccion) {
        et = em.getTransaction();
        try {                        
            et.begin();
            em.persist(pDireccion);
            et.commit();
            return pDireccion;
        } catch (Exception ex) {
            et.rollback();
            return null;
        }
    }
    
    
    
    /**
     * Metodo que agrega la direccion de una persona en la base de datos en la
     * tabla tb_Direcciones
     *
     * @param pPersona
     * @param pDireccion
     * @return
     */
    public Direccion AgregarDireccionPersona(Persona pPersona, Direccion pDireccion) {
        et = em.getTransaction();
        try {                                                
            //pPersona.getDireccionList().clear();
            pPersona.getDireccionList().add(pDireccion);            
            
            et.begin();            
            em.merge(pPersona);
            et.commit();
            
           /* pDireccion.getPersonaList().add(pPersona);
            
            et.begin();            
            em.merge(pDireccion);
            et.commit();*/
            
            return pDireccion;
        } catch (Exception ex) {
            et.rollback();
            return null;
        }
    }

    /**
     * Metodo que edita una direccion en la base de datos en tabla
     * tb_Direcciones
     *
     * @param pPersona
     * @param pDireccion
     * @return
     */
    public Direccion ModificarDireccionPersona(Persona pPersona, Direccion pDireccion) {
        et = em.getTransaction();
        try {
            pPersona.getDireccionList().clear();
            pPersona.getDireccionList().add(pDireccion);
            em.lock(pPersona, LockModeType.PESSIMISTIC_WRITE);
            et.begin();
            em.persist(pPersona);
            et.commit();
            return pDireccion;
        } catch (Exception ex) {
            et.rollback();
            return null;
        }
    }

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
}
