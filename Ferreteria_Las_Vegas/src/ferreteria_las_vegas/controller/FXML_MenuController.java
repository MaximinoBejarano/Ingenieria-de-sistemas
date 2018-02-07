/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.entities.Usuario;
import ferreteria_las_vegas.utils.AppContext;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_MenuController implements Initializable {

    @FXML
    private Button btnFacturacion;

    @FXML
    private Button btnAbonos;

    @FXML
    private Button btnNotaCredito;

    @FXML
    private Button btnInventario;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnEmpleados;

    @FXML
    private Button btnProveedores;

    @FXML
    private Button btnAnulacion;   

    @FXML
    private Button btnEstadisticas;

    @FXML
    private Button btnConfiguraciones;
    
    @FXML
    private Label lblDate;

    @FXML
    private Label lblUserName;

    @FXML
    private void accesoCliente(ActionEvent e) {
        try {
            if (true) {
                ScenesManager.getInstance().LoadSceneClientes();
            } else {
                new Alert(Alert.AlertType.WARNING, "No cuenta con los permisos para entrar aquí.", ButtonType.OK).showAndWait();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void accesoEmpleados(ActionEvent e) {
        if (true) {
            ScenesManager.getInstance().LoadSceneEmpleados();
        } else {
            new Alert(Alert.AlertType.WARNING, "No cuenta con los permisos para entrar aquí.", ButtonType.OK).showAndWait();
        }
    }

    @FXML
    private void accesoInventario(ActionEvent e) {
        try {
            if (true) {
                ScenesManager.getInstance().LoadSceneInventario();
            } else {
                new Alert(Alert.AlertType.WARNING, "No cuenta con los permisos para entrar aquí.", ButtonType.OK).showAndWait();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void accesoProveedores(ActionEvent e) {
        try {
            if (true) {
                ScenesManager.getInstance().LoadSceneProveedores();
            } else {
                new Alert(Alert.AlertType.WARNING, "No cuenta con los permisos para entrar aquí.", ButtonType.OK).showAndWait();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void accesoAnulacion(ActionEvent e) {
        try {
            if (true) {
                ScenesManager.getInstance().LoadSceneAnulacion();
            } else {
                new Alert(Alert.AlertType.WARNING, "No cuenta con los permisos para entrar aquí.", ButtonType.OK).showAndWait();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void accesoAbonos(ActionEvent e) {
        try {
            if (true) {
                ScenesManager.getInstance().LoadSceneAbonos();
            } else {
                new Alert(Alert.AlertType.WARNING, "No cuenta con los permisos para entrar aquí.", ButtonType.OK).showAndWait();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void accesoVales(ActionEvent e) {
        try {
            if (true) {
                ScenesManager.getInstance().LoadSceneVales();
            } else {
                new Alert(Alert.AlertType.WARNING, "No cuenta con los permisos para entrar aquí.", ButtonType.OK).showAndWait();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void accesoFacturacion(ActionEvent e) {
        try {
            if (true) {
                ScenesManager.getInstance().LoadSceneFacturacion();
            } else {
                new Alert(Alert.AlertType.WARNING, "No cuenta con los permisos para entrar aquí.", ButtonType.OK).showAndWait();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    void SalirClick(ActionEvent event) {
        ScenesManager.getInstance().LoadSceneLogin();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Usuario usuario = (Usuario) AppContext.getInstance().get("user");
        lblUserName.setText(lblUserName.getText() + usuario.getPersona().getPerNombre() + " " + usuario.getPersona().getPerPApellido());
        lblDate.setText("Bienvenido");
    }
}
