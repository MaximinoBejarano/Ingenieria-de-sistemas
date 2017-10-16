/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.controller;

/**
 *
 * @author Usuario
 * 
 * Singlenton para gestion de las operaciones sobre la entidad Persona (tb_Personas)
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

}
