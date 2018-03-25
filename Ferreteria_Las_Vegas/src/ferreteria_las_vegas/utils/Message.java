/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author johan
 *
 * Por ahora el codigo es temporal
 */
public class Message {

    private static Message INSTANCE = null;

    private static void createInstance() {
        if (INSTANCE == null) {

            synchronized (Message.class) {

                if (INSTANCE == null) {
                    INSTANCE = new Message();
                }
            }
        }
    }

    public static Message getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void Information(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public void Warning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public void Error(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public boolean Confirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.showAndWait();
        return alert.getResult().equals(ButtonType.YES);
    }
}
