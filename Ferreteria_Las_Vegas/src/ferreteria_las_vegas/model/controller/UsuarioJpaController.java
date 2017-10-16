/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.model.entities.Usuario;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Usuario
 * Singlenton para gestion de las operaciones sobre la entidad Usuario (tb_Usuarios)
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
     * Metodo que verifica el ingreso de un usuario y una contraseña validos o no de un empleado del sistema.
     * @param pusuario
     * @param pcontraseña
     * @return 
     */    
    public Usuario SolicitarAcceso(String pusuario, String pcontraseña) {
        try {
            Query qryUsuario = em.createNamedQuery("Usuario.findByUsuLogin", Usuario.class);
            qryUsuario.setParameter("usuUsuario", pusuario);
            qryUsuario.setParameter("usuContrase\u00f1a", pcontraseña);
            Usuario usuario = (Usuario) qryUsuario.getSingleResult();
            return usuario;
        } catch (Exception ex) {
            return null;
        }
    }
    
    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
}
