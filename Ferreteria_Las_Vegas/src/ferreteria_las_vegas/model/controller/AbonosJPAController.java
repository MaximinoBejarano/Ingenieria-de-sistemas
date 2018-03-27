/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

import ferreteria_las_vegas.utils.EntityManagerHelper;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Maximino
 */
public class AbonosJPAController {
      private static AbonosJPAController INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (AbonosJPAController.class) {

                if (INSTANCE == null) {
                    INSTANCE = new AbonosJPAController();
                }
            }
        }
    }

    public static AbonosJPAController getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    private EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
}
