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
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

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

    public void setDataPane(Node node) {
        dataPane.getChildren().setAll(node);
    }

    public VBox cargarScena(String url) throws IOException {
        VBox v = (VBox) FXMLLoader.load(getClass().getResource(url));
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(v);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        
        return v;
    }

    @FXML
    private void accesoCliente(ActionEvent e) {
        try {
            if (true) {
                 setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Clientes.fxml"));
                 
            } else {

                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error de Acceso");
                alert.setHeaderText("¡Cuidado!");
                String s = "Este usuario no cuenta con los permisos necesarios para acceder a esta página";
                alert.setContentText(s);
                alert.show();
            }
        } catch (Exception ex) {
            // mandar al servidor al log de errores
            JOptionPane.showMessageDialog(null, ex.toString());
        }

    }

    @FXML
    private void accesoEmpleados(ActionEvent e) {
        try {
            if (true) {
                setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Empleados.fxml"));
                
            } else {

                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error de Acceso");
                alert.setHeaderText("¡Cuidado!");
                String s = "Este usuario no cuenta con los permisos necesarios para acceder a esta página";
                alert.setContentText(s);
                alert.show();
            }
        } catch (Exception ex) {
            // mandar al servidor al log de errores
            JOptionPane.showMessageDialog(null, ex.toString());
        }

    }

    @FXML
    private void accesoInventario(ActionEvent e) {
        try {
            if (true) {
                
                     setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Inventario.fxml"));
                
            } else {

                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error de Acceso");
                alert.setHeaderText("¡Cuidado!");
                String s = "Este usuario no cuenta con los permisos necesarios para acceder a esta página";
                alert.setContentText(s);
                alert.show();
            }
        } catch (Exception ex) {
            // mandar al servidor al log de errores
            JOptionPane.showMessageDialog(null, ex.toString());
        }

    }
    
     @FXML
    private void accesoProveedores(ActionEvent e) {
        try {
            if (true) {
                 setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Proveedores.fxml"));
                 
            } else {

                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error de Acceso");
                alert.setHeaderText("¡Cuidado!");
                String s = "Este usuario no cuenta con los permisos necesarios para acceder a esta página";
                alert.setContentText(s);
                alert.show();
            }
        } catch (Exception ex) {
            // mandar al servidor al log de errores
            JOptionPane.showMessageDialog(null, ex.toString());
        }

    }
    
    @FXML
    private void accesoAnulacion(ActionEvent e) {
        try {
            if (true) {
                 setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Anulación.fxml"));
                 
            } else {

                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error de Acceso");
                alert.setHeaderText("¡Cuidado!");
                String s = "Este usuario no cuenta con los permisos necesarios para acceder a esta página";
                alert.setContentText(s);
                alert.show();
            }
        } catch (Exception ex) {
            // mandar al servidor al log de errores
            JOptionPane.showMessageDialog(null, ex.toString());
        }

    }
      @FXML
    private void accesoAbonos(ActionEvent e) {
        try {
            if (true) {
                 setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Abonos.fxml"));
                 
            } else {

                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error de Acceso");
                alert.setHeaderText("¡Cuidado!");
                String s = "Este usuario no cuenta con los permisos necesarios para acceder a esta página";
                alert.setContentText(s);
                alert.show();
            }
        } catch (Exception ex) {
            // mandar al servidor al log de errores
            JOptionPane.showMessageDialog(null, ex.toString());
        }

    }
    
    @FXML
    private void accesoVales(ActionEvent e) {
        try {
            if (true) {
                 setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Vales.fxml"));
                 
            } else {

                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error de Acceso");
                alert.setHeaderText("¡Cuidado!");
                String s = "Este usuario no cuenta con los permisos necesarios para acceder a esta página";
                alert.setContentText(s);
                alert.show();
            }
        } catch (Exception ex) {
            // mandar al servidor al log de errores
            JOptionPane.showMessageDialog(null, ex.toString());
        }

    }
    
     @FXML
    private void accesoFacturacion(ActionEvent e) {
        try {
            if (true) {
                 setDataPane(cargarScena("/ferreteria_las_vegas/view/FXML_Facturación.fxml"));
                 
            } else {

                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error de Acceso");
                alert.setHeaderText("¡Cuidado!");
                String s = "Este usuario no cuenta con los permisos necesarios para acceder a esta página";
                alert.setContentText(s);
                alert.show();
            }
        } catch (Exception ex) {
            // mandar al servidor al log de errores
            JOptionPane.showMessageDialog(null, ex.toString());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Usuario usuario = (Usuario)AppContext.getInstance().get("user");
        lblUserName.setText(lblUserName.getText() + usuario.getPersona().getPerNombre()+" "+usuario.getPersona().getPerPApellido());
        lblDate.setText("Bienvenido");
    }

}
