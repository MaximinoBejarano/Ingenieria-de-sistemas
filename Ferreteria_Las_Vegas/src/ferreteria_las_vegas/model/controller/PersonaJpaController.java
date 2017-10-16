/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Persona;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 *
 * Singlenton para gestion de las operaciones sobre la entidad Persona
 * (tb_Personas)
 */
public class PersonaJpaController {

    private static PersonaJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (PersonaJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new PersonaJpaController();
                }
            }
        }
    }

    public static PersonaJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    /**
     * Metodo que consulta (mediante la cedula) y devuelve (en caso de existir)
     * una persona desde la base de datos en la tabla tb_Personas
     *
     * @param pCedula
     * @return
     */
    public Persona ConsultarPersonaCedula(String pCedula) {
        try {
            Query qry = em.createNamedQuery("Persona.findByPerCedula", Persona.class);// consulta definida 
            qry.setParameter("perCedula", pCedula);
            Persona persona = (Persona) qry.getSingleResult();// trae el resultado de la consulta  
            return persona;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Metodo que consulta y devuelve una lista con todas las personas que hay
     * en la base de datos en la tabla tb_Personas.
     *
     * @return
     */
    public List<Persona> ConsultarPersonasTodos() {
        try {
            Query qry = em.createNamedQuery("Persona.findAll", Persona.class);// consulta definida por folio
            List<Persona> personas = qry.getResultList();// Recibe el resultado de la consulta  
            return personas;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Metodo que agrega una persona en la base de datos en la tabla tb_Personas
     *
     * @param pPersona
     * @return
     */
    public Persona AgregarPersona(Persona pPersona) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(pPersona);
            et.commit();
            return pPersona;
        } catch (Exception ex) {
            et.rollback();
            return null;
        }
    }

    /**
     * Metodo que edita una persona en la base de datos en tabla tb_Personas
     *
     * @param pPersona
     * @return
     */
    public Persona ModificarPersona(Persona pPersona) {
        et = em.getTransaction();
        try {
            et.begin();
            em.lock(pPersona, LockModeType.PESSIMISTIC_WRITE);
            em.merge(pPersona);
            et.commit();
            return pPersona;
        } catch (Exception ex) {
            et.rollback();
            return null;
        }
    }

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
}
