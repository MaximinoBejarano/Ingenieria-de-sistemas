/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import ferreteria_las_vegas.model.entities.Usuario;
import ferreteria_las_vegas.utils.AppContext;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_MenuController implements Initializable {

    @FXML
    private Label lblDate;

    @FXML
    private Label lblUserName;

    @FXML
    private VBox dataPane;    

    @FXML
    private void accesoCliente(ActionEvent e) {
        try {
            if (true) {
                //setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Clientes.fxml"));
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
            try {
                //setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Empleados.fxml"));
                ScenesManager.getInstance().LoadSceneEmpleados();
            } catch (Exception ex) {
                Logger.getLogger(FXML_MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }

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
                //setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Proveedores.fxml"));
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
                //setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Anulación.fxml"));
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
                //setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Abonos.fxml"));
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
                //setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Vales.fxml"));
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
                //setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Facturación.fxml"));
            } else {
                new Alert(Alert.AlertType.WARNING, "No cuenta con los permisos para entrar aquí.", ButtonType.OK).showAndWait();
            }
        } catch (Exception ex) {            
            System.out.println(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Usuario usuario = (Usuario) AppContext.getInstance().get("user");
        lblUserName.setText(lblUserName.getText() + usuario.getPersona().getPerNombre() + " " + usuario.getPersona().getPerPApellido());
        lblDate.setText("Bienvenido");
    }

}
