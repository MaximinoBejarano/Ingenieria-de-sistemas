/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;
import ferreteria_las_vegas.model.controller.ArticuloJpaController;
import ferreteria_las_vegas.model.controller.PersonaJpaController;
import ferreteria_las_vegas.model.entities.Articulo;
import ferreteria_las_vegas.model.entities.Persona;
import java.math.BigDecimal;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author MaxBejarano
 */
public class FXML_Buscar_ArticulosController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField txtCodigoArt;
    @FXML
    private TextField txtNombreArt;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<Articulo> tblArticulos;
    @FXML
    private TableColumn<Articulo,String> colCodigo;
    @FXML
    private TableColumn<Articulo,String> colNombre;
    @FXML
    private TableColumn<Articulo, String> colMarca;
    @FXML
    private TableColumn<Articulo, String> colUndMedida;
    @FXML
    private TableColumn<Articulo, String> colDescripcion;
    @FXML
    private TableColumn<Articulo, BigDecimal >colPrecio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void FinalizarProceso(ActionEvent event) {
    }

    @FXML
    private void ConfirmarArgregación(ActionEvent event) {
    }

    @FXML
    private void ConsultarArticulos(ActionEvent event) {
        ProcesoBusquedad();
    }
    
    void ProcesoBusquedad(){
      CargarDatosTabla();
     if(!txtCodigoArt.getText().isEmpty()){
     
     }else{
     
     }   
    }
    void CargarDatosTabla() {

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("artCodigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("artNombre"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("artMarca"));
        colUndMedida.setCellValueFactory(new PropertyValueFactory<>("artUnidadMedida"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("artDescripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("artPrecio"));        

        List<Articulo> ArticulosList = ArticuloJpaController.getInstance().ConsultarTodosArticulos();
        ObservableList<Articulo> OLecturaList = FXCollections.observableArrayList(ArticulosList);
        tblArticulos.setItems(OLecturaList);
    }
}
