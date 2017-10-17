/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import ferreteria_las_vegas.model.controller.ArticuloJpaController;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import ferreteria_las_vegas.utils.EntityManagerHelper;
import ferreteria_las_vegas.model.entities.Articulo;
import java.math.BigDecimal;
/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_ProductosController implements Initializable {

    @FXML
    private TextField txtCodigoProducto;

    @FXML
    private TextField txtCodBarras;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtUndMedida;
    
    @FXML
    private TextField txtDescripcion;

    @FXML
    private Button btnAgregarProducto;

    @FXML
    private Button btnBuscarProducto;
    
    @FXML
    private Button btnEditarProducto;

    @FXML
    private Button btnEliminarProducto;
            
    @FXML
    private void AgregarProducto(ActionEvent e) {
        if(txtCodigoProducto.getText().isEmpty()||txtNombre.getText().isEmpty()||
           txtDescripcion.getText().isEmpty()||txtPrecio.getText().isEmpty()){
           new Alert(Alert.AlertType.WARNING, "Advertencia: Es necesario completar los campos requeridos", ButtonType.OK).showAndWait();
        }
        else{
            GuardarProducto();
        } 
    }
  
    private void GuardarProducto(){   
       BigDecimal Precio=new BigDecimal(txtPrecio.getText());
       Articulo articulo=new Articulo(txtCodigoProducto.getText(),txtNombre.getText(), txtDescripcion.getText(),Precio);
       articulo.setArtMarca(txtMarca.getText());
       articulo.setArtUnidadMedida(txtUndMedida.getText());
       ArticuloJpaController.getInstance().InsertarArticulo(articulo);
    }
  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
