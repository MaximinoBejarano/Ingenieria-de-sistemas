/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Usuario;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Query;

/**
 * @author Usuario Singlenton para gestion de las operaciones sobre la entidad
 * Usuario (tb_Usuarios)
 */
public class UsuarioJpaController {

    private static UsuarioJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (UsuarioJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new UsuarioJpaController();
                }
            }
        }
    }

    public static UsuarioJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    /**
     * Metodo que verifica el ingreso de un usuario y una contraseña validos o
     * no de un empleado del sistema en la base de datos en la tabla tb_Usuarios
     *
     * @param pusuario
     * @param pcontraseña
     * @return
     */
    public Usuario SolicitarAcceso(String pusuario, String pcontraseña) {
        try {
            Query qry = em.createNamedQuery("Usuario.findByUsuLogin", Usuario.class);
            qry.setParameter("usuUsuario", pusuario);
            qry.setParameter("usuContrase\u00f1a", pcontraseña);
            Usuario usuario = (Usuario) qry.getSingleResult();
            return usuario;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Metodo que consulta (mediante la cedula) y devuelve (en caso de existir)
     * un usuario desde la base de datos en la tabla tb_Usuarios
     *
     * @param pCedula
     * @return
     */
    public Usuario ConsultarUsuarioCedula(String pCedula) {
        try {
            Query qry = em.createNamedQuery("Usuario.findByUsuPersona", Usuario.class);// consulta definida 
            qry.setParameter("usuPersona", pCedula);
            Usuario usuario = (Usuario) qry.getSingleResult();// trae el resultado de la consulta  
            return usuario;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Metodo que consulta (mediante el nombre de usuario) y devuelve (en caso
     * de existir) un usuario desde la base de datos en la tabla tb_Usuarios.
     *
     * @param pCedula
     * @return
     */
    public Usuario ConsultarUsuarioNombre(String pNombre) {
        try {
            Query qry = em.createNamedQuery("Usuario.findByUsuNombre", Usuario.class);// consulta definida 
            qry.setParameter("usuNombre", pNombre);
            Usuario usuario = (Usuario) qry.getSingleResult();// trae el resultado de la consulta  
            return usuario;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Metodo que consulta y devuelve una lista con todos los usuarios que hay
     * en la base de datos en la tabla tb_Usuarios.
     *
     * @return
     */
    public List<Usuario> ConsultarUsuariosTodos() {
        try {
            Query qry = em.createNamedQuery("Usuario.findAll", Usuario.class);// consulta definida por folio
            List<Usuario> empleados = qry.getResultList();// Recibe el resultado de la consulta  
            return empleados;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Metodo que agrega un usuario en la base de datos en la tabla tb_Usuarios
     *
     * @param pUsuario
     * @return
     */
    public Usuario AgregarUsuario(Usuario pUsuario) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(pUsuario);
            et.commit();
            return pUsuario;
        } catch (Exception ex) {
            et.rollback();
            return null;
        }
    }

    /**
     * Metodo que edita un usuario en la base de datos en tabla tb_Usuarios
     *
     * @param pUsuario
     * @return
     */
    public Usuario ModificarUsuario(Usuario pUsuario) {
        et = em.getTransaction();
        try {
            et.begin();
            em.lock(pUsuario, LockModeType.PESSIMISTIC_WRITE);
            em.merge(pUsuario);
            et.commit();
            return pUsuario;
        } catch (Exception ex) {
            et.rollback();
            return null;
        }
    }

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
}

/*No Borrar
Usuarios
, @NamedQuery(name = "Usuario.findByUsuLogin", query = "SELECT u FROM Usuario u WHERE u.usuNombre = :usuUsuario AND u.usuContrase\u00f1a = :usuContrase\u00f1a")
*/