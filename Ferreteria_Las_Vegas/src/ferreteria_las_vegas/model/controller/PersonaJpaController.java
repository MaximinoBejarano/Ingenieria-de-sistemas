/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Contacto;
import ferreteria_las_vegas.model.entities.Direccion;
import ferreteria_las_vegas.model.entities.Persona;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
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
        } catch (NoResultException ex) {
            System.err.println(ex);
            return null;
        } catch (NonUniqueResultException ex) {
            System.err.println(ex);
            return null;
        } catch (Exception ex) {
            System.err.println(ex);
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
        } catch (NoResultException ex) {
            System.err.println(ex);
            return null;
        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }
    }

    public List<Persona> ConsultarPersonasEmpleados() {
        try {
            Query qry = em.createNamedQuery("Persona.findAll", Persona.class);// consulta definida por folio
            List<Persona> personas = qry.getResultList();// Recibe el resultado de la consulta         
            return personas.stream().filter(e -> e.getUsuario() != null).collect(Collectors.toList());
        } catch (NoResultException ex) {
            System.err.println(ex);
            return null;
        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }
    }

    public List<Persona> ConsultarPersonasNoEmpleados() {
        try {
            Query qry = em.createNamedQuery("Persona.findAll", Persona.class);// consulta definida por folio
            List<Persona> personas = qry.getResultList();// Recibe el resultado de la consulta         
            return personas.stream().filter(e -> e.getCliente() != null).collect(Collectors.toList());
        } catch (NoResultException ex) {
            System.err.println(ex);
            return null;
        } catch (Exception ex) {
            System.err.println(ex);
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
     * Metodo que edita una persona en la base de datos en tabla tb_Personas
     *
     * @param pPersona
     * @return
     */
    public Persona ModificarPersona(Persona pPersona) {
        et = em.getTransaction();
        try {
            //em.lock(pPersona, LockModeType.PESSIMISTIC_WRITE);
            et.begin();
            em.merge(pPersona);
            et.commit();
            return pPersona;
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

    /*public Persona AgregarPersona(Persona pPersona, Direccion pDireccion, Contacto pTel, Contacto pEmail) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(pPersona);
            em.flush();

            em.persist(pDireccion);
            em.flush();

            em.persist(pTel);
            em.flush();

            em.persist(pEmail);
            em.flush();

            pPersona.getDireccionList().add(pDireccion);
            pPersona.getContactoList().add(pTel);
            pPersona.getContactoList().add(pEmail);

            em.merge(pPersona);

            et.commit();

            return pPersona;
        } catch (Exception ex) {
            et.rollback();
            System.err.println(ex);
            return null;
        }
    }*/
    /**
     * Metodo que agrega una persona en la base de datos en la tabla tb_Personas
     * Ademas de agregar contacto y direccion (tb_Contactos, tb_Direcciones)
     * Tambien realiza la union entre las tablas intermedias
     *
     * @param pPersona
     * @param pDireccion
     * @param pTel1
     * @param pTel2
     * @param pEmail
     * @return
     */
    public Persona AgregarPersona(Persona pPersona, Direccion pDireccion, Contacto pTel1, Contacto pTel2, Contacto pEmail) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(pPersona);
            em.flush();

            em.persist(pDireccion);
            em.flush();
            pPersona.getDireccionList().add(pDireccion);

            if (pTel1 != null) {
                em.persist(pTel1);
                em.flush();
                pPersona.getContactoList().add(pTel1);
            }
            if (pTel2 != null) {
                em.persist(pTel2);
                em.flush();
                pPersona.getContactoList().add(pTel2);
            }
            if (pEmail != null) {
                em.persist(pEmail);
                em.flush();
                pPersona.getContactoList().add(pEmail);
            }

            em.merge(pPersona);

            et.commit();

            return pPersona;
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


/* No Borrar esto
Persona
@JoinTable(name = "tb_personas_direcciones", joinColumns = {
        @JoinColumn(name = "RPD_Persona", referencedColumnName = "Per_Cedula")}, inverseJoinColumns = {
        @JoinColumn(name = "RPD_Direccion", referencedColumnName = "Dir_ID")})
    @ManyToMany
    private List<Direccion> direccionList;        
    @JoinTable(name = "tb_personas_contactos", joinColumns = {
        @JoinColumn(name = "RPC_Persona", referencedColumnName = "Per_Cedula")}, inverseJoinColumns = {
        @JoinColumn(name = "RPC_Contacto", referencedColumnName = "Con_ID")})
    @ManyToMany
    private List<Contacto> contactoList;

Direccion
@ManyToMany(mappedBy = "direccionList")    
    private List<Persona> personaList;

Contacto
@ManyToMany(mappedBy = "contactoList")
    private List<Persona> personaList;
 */
