/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Contacto;
import ferreteria_las_vegas.model.entities.Persona;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 * Singlenton para gestion de las operaciones sobre la entidad Contacto (tb_Contactos)
 */
public class ContactoJpaController {
    private static ContactoJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {
            
            synchronized (ContactoJpaController.class) {
                
                if (INSTANCE == null) {
                    INSTANCE = new ContactoJpaController();
                }
            }
        }
    }
  
    public static ContactoJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }
    
    
    /**
     * Metodo que consulta y devuelve (en caso de existir) un contacto desde
     * la base de datos en la tabla tb_Contactos mediante la persona a la que
     * le pertenece
     *
     * @param pPersona
     * @return
     */
    public Contacto ConsultarContactoPersona(Persona pPersona, String pTipo) {
        try {
            Query qry = em.createNamedQuery("Persona.findByPerCedula", Persona.class);// consulta definida 
            qry.setParameter("perCedula", pPersona.getPerCedula());
            Persona persona = (Persona) qry.getSingleResult();// trae el resultado de la consulta  
            return persona.getContactoList().get(1);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Metodo que agrega el contacto de una persona en la base de datos en la
     * tabla tb_Contactos
     *
     * @param pPersona
     * @param pContacto
     * @return
     */
    public Contacto AgregarContactoPersona(Persona pPersona, Contacto pContacto) {
        et = em.getTransaction();
        try {            
            Contacto auxCon = BuscarContactoTipo(pPersona, pContacto.getConTipoContacto());
            if(auxCon!=null)
            {
                pPersona.getContactoList().remove(auxCon);
            }            
            pPersona.getContactoList().add(pContacto);
            em.lock(pPersona, LockModeType.PESSIMISTIC_WRITE);
            et.begin();
            em.persist(pPersona);
            et.commit();
            return pContacto;
        } catch (Exception ex) {
            et.rollback();
            return null;
        }
    }

    /**
     * Metodo que edita un contacto en la base de datos en tabla
     * tb_Contactos
     *
     * @param pPersona
     * @param pContacto
     * @return
     */
    public Contacto ModificarContactoPersona(Persona pPersona, Contacto pContacto) {
        et = em.getTransaction();
        
        try {            
            Contacto auxCon = BuscarContactoTipo(pPersona, pContacto.getConTipoContacto());
            if(auxCon!=null)
            {
                pPersona.getContactoList().remove(auxCon);
            }            
            pPersona.getContactoList().add(pContacto);
            em.lock(pPersona, LockModeType.PESSIMISTIC_WRITE);
            et.begin();
            em.persist(pPersona);
            et.commit();
            return pContacto;
        } catch (Exception ex) {
            et.rollback();
            return null;
        }
    }
    
    Contacto BuscarContactoTipo(Persona pPersona, String Tipo)
    {
        for (Contacto con : pPersona.getContactoList()) {
            if(con.getConTipoContacto().equalsIgnoreCase(Tipo)){
                return con;
            }
        }
        return null;
    }
    
    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
}
