/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

/**
 *
 * @author Usuario
 * Singlenton para gestion de las operaciones sobre la entidad Direccion (tb_Direcciones)
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
    
}
