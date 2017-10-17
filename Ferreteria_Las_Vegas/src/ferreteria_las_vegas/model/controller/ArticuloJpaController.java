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
import ferreteria_las_vegas.model.entities.Articulo;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.persistence.Query;

/**
 *
 * @author MaxBejarano
 */
public class ArticuloJpaController {

    private static ArticuloJpaController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (ArticuloJpaController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new ArticuloJpaController();
                }
            }
        }
    }

    public static ArticuloJpaController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;

    public void InsertarArticulo(Articulo Art) {
        et = em.getTransaction();
        try {
            et.begin();
            em.persist(Art);
            et.commit();
            new Alert(Alert.AlertType.INFORMATION, "Información: Se han ingresado los datos de forma exitosa ", ButtonType.OK).showAndWait();
            
        } catch (Exception ex) {
            et.rollback();
            new Alert(Alert.AlertType.ERROR, "Error: No se han guardado los datos", ButtonType.OK).showAndWait();
        }

    }

}
