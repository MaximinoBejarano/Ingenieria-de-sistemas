/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.utils;

import javafx.scene.input.KeyEvent;

/**
 *
 * @author johan
 */
public class GeneralUtils {

    private static GeneralUtils INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (GeneralUtils.class) {

                if (INSTANCE == null) {
                    INSTANCE = new GeneralUtils();
                }
            }
        }
    }

    public static GeneralUtils getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void ValidarCampos(boolean numeros, int cantidad, int restriccion, KeyEvent a) {

        if (numeros == true) {
            char c = a.getCharacter().charAt(0);
            if (Character.isLetter(c)) {
                a.consume();
            }
        }
        if (cantidad >= restriccion) {
            a.consume();
        }
    }
}
