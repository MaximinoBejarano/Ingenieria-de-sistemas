/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.entities.Factura;
import ferreteria_las_vegas.model.entities.Articulo;
import ferreteria_las_vegas.model.entities.ArticuloXFactura;
import ferreteria_las_vegas.model.entities.Cliente;
import ferreteria_las_vegas.model.entities.Persona;
import ferreteria_las_vegas.utils.AppContext;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_FacturaciónController implements Initializable {

    @FXML
    private Button btnBuscarArticulo;
    @FXML
    private TextField txtCodigoArticulo;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnCredito;
    @FXML
    private Button btnCobrarFactura;
    @FXML
    private Button btnGuardarPedido;
    @FXML
    private Button btnCliente;
    @FXML
    private Button btnGenerarProforma;
    @FXML
    private Button btnBorrarLinea;
    @FXML
    private TableView<?> tbl_Factura;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblCliente;
    @FXML
    private Label lblFecha;
    
     /**
     * Area de variables Globales
     */
    Cliente cliente; 
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    Date fecha = new Date();
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblFecha.setText(formatter.format(fecha.getTime()));
      
    }    
    
    @FXML
    private void btnBuscarArticulo_Click(ActionEvent event) {
        Lanzar_FXMLBuscarArticulo();
    }

    @FXML
    private void btnSalir_Click(ActionEvent event) {
        ScenesManager.getInstance().LoadSceneMenu();
    }

    @FXML
    private void btnCredito_Click(ActionEvent event) {
        Lanzar_FXML_Abonos();
    }

    @FXML
    private void btnCobrarFactura_Click(ActionEvent event) {
         Lanzar_FXMLPagos();
    }

    @FXML
    private void btnGuardarPedido_Click(ActionEvent event) {
        
    }

    @FXML
    private void btnCliente_Click(ActionEvent event) {
        Lanzar_FXMLBuscarCliente();
        cliente = (Cliente) AppContext.getInstance().get("selected-Cliente");
        Persona per=cliente.getPersona();
        lblCliente.setText(String.valueOf(per.getPerCedula())+" "+per.getPerNombre()+" "+per.getPerPApellido());
    }

    @FXML
    private void btnGenerarProforma_Click(ActionEvent event) {
    }

    @FXML
    private void btnBorrarLinea_Click(ActionEvent event) {
    }
    
    
 /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++Procesos fundamentales++++++++++++++++++++++++++++++++++++++++++++++++++++*/    
 /*+++++++++++++++++++++++++++++++++++++++++++++++++Metodos importantes que no son procesos+++++++++++++++++++++++++++++++++++++++++*/
 /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++Otros metodos+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/ 
 /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++Metodos GUI-++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
 /*++++++++++++++++++++++++++++++++++++++++++++++++++++++Metodos Lanzadores+++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
     public void Lanzar_FXMLPagos(){
     try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Pagos.fxml"));
           
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage(StageStyle.UTILITY);
                stage.setScene(new Scene(root1));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnCobrarFactura.getScene().getWindow());
                stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(FXML_FacturaciónController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void Lanzar_FXMLBuscarCliente(){
       try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Clientes.fxml"));
           
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage(StageStyle.UTILITY);
                stage.setScene(new Scene(root1));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnCliente.getScene().getWindow());
                stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(FXML_FacturaciónController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public void Lanzar_FXMLBuscarArticulo(){
       try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Productos.fxml"));
           
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage(StageStyle.UTILITY);
                stage.setScene(new Scene(root1));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(btnBuscarArticulo.getScene().getWindow());
                stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(FXML_FacturaciónController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void Lanzar_FXML_Abonos(){
      ScenesManager.getInstance().LoadSceneAbonos();
    }
 /*+++++++++++++++++++++++++++++++++++++++++++++++++++++Variables de Clase++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
      
}
