/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import ferreteria_las_vegas.model.controller.InventarioJpaController;
import ferreteria_las_vegas.model.entities.Articulo;
import ferreteria_las_vegas.model.entities.Inventario;
import ferreteria_las_vegas.utils.AppContext;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_InventarioController implements Initializable {

    @FXML
    private VBox DataPanel;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnAgregarFactura;
    @FXML
    private Button btnBuscarFactura;
    @FXML
    private Button btnEliminarFactura;
    @FXML
    private Button btnEditarFactura;
    @FXML
    private Button btnEliminarProducto;
    @FXML
    private TextField txtCedCliente;
    @FXML
    private TextField txtNumFactura;
    @FXML
    private TextField txtVnombre;
    @FXML
    private RadioButton rdbContado;
    @FXML
    private ToggleGroup tipoPago;
    @FXML
    private RadioButton rdbCredito;
    @FXML
    private DatePicker pickFeccha;
    @FXML
    private Button btnBuscarProducto;
    @FXML
    private Button btnNuevoProducto;
    @FXML
    private Button btnEditarProducto;
    @FXML
    private TableView<InventarioCompleto> tblProductos;
    @FXML
    private TableColumn<InventarioCompleto, String> tcCodigoProducto;
    @FXML
    private TableColumn<InventarioCompleto, String> tcCodigoBarrasProducto;
    @FXML
    private TableColumn<InventarioCompleto, String> tcNombreProducto;
    @FXML
    private TableColumn<InventarioCompleto, String> tcMarcaProducto;
    @FXML
    private TableColumn<InventarioCompleto, String> tcUnidadProducto;
    @FXML
    private TableColumn<InventarioCompleto, String> tcDescripcionProducto;
    @FXML
    private TableColumn<InventarioCompleto, String> tcPrcioProducto;
    @FXML
    private TableColumn<InventarioCompleto, String> tcCantidad;

    ArrayList<InventarioCompleto> listaArticulos = new ArrayList<InventarioCompleto>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void SalirClick(ActionEvent event) {
        ScenesManager.getInstance().LoadSceneMenu();
    }

    @FXML
    private void AgregarProducto(ActionEvent event) {
        try {
            if (true) {
                CargarProductos();
                Articulo articulo = (Articulo) AppContext.getInstance().get("articulo-Ingresado");
                if (articulo != null) {
                    Inventario inventario = new InventarioJpaController().ConsultarInventarioCodigoProducto(articulo);
                    InventarioCompleto inventarioCompleto;
                    if (inventario == null) {
                        inventario = new Inventario(Integer.SIZE, new java.sql.Date(new java.util.Date().getTime()), 0, "A");
                        inventarioCompleto = new InventarioCompleto(articulo, inventario);
                    } else {
                        inventarioCompleto = new InventarioCompleto(articulo, inventario);
                    }

                    if (listaArticulos.isEmpty()) {
                        listaArticulos.add(inventarioCompleto);
                    } else {
                        int listasize = listaArticulos.size();
                        for (int i = 0; i < listasize; i++) {

                            if (listaArticulos.get(i).getArticulo().getArtCodigo() == inventarioCompleto.getArticulo().getArtCodigo()) {
                                new Alert(Alert.AlertType.WARNING, "Los datos de este Articulo ya se encuentran en la tabla.", ButtonType.OK).showAndWait();
                                break;
                            }
                            if (!listaArticulos.get(i).equals(inventarioCompleto) && ((i + 1) == listasize)) {
                                listaArticulos.add(inventarioCompleto);
                            }

                        }
                    }

                    RecargarTblClientes();
                }
            } else {
                //rellenar los datos del formulario
            }
        } catch (Exception ex) {
            System.err.print(ex);
        }
    }

    @FXML
    private void BuscarArticulo(ActionEvent event) {
        ProcesoBusquedad();
        Articulo articulo = (Articulo) AppContext.getInstance().get("articulo-Ingresado");
        if (articulo != null) {
            Inventario inventario = new InventarioJpaController().ConsultarInventarioCodigoProducto(articulo);
            InventarioCompleto inventarioCompleto;
            if (inventario == null) {
                inventario = new Inventario(Integer.SIZE, new java.sql.Date(new java.util.Date().getTime()), 0, "A");
                inventarioCompleto = new InventarioCompleto(articulo, inventario);
            } else {
                inventarioCompleto = new InventarioCompleto(articulo, inventario);
            }

            if (listaArticulos.isEmpty()) {
                listaArticulos.add(inventarioCompleto);
            } else {
                int listasize = listaArticulos.size();
                for (int i = 0; i < listasize; i++) {

                    if (listaArticulos.get(i).getArticulo().getArtCodigo() == inventarioCompleto.getArticulo().getArtCodigo()) {
                        new Alert(Alert.AlertType.WARNING, "Los datos de este Articulo ya se encuentran en la tabla.", ButtonType.OK).showAndWait();
                        break;
                    }
                    if (!listaArticulos.get(i).equals(inventarioCompleto) && ((i + 1) == listasize)) {
                        listaArticulos.add(inventarioCompleto);
                    }

                }
            }

            RecargarTblClientes();
        }

    }

    public void CargarProductos() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Productos.fxml"));
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnNuevoProducto.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXML_InventarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void RecargarTblClientes() {
        tblProductos.getItems().clear();
        tblProductos.setEditable(true);
        tcCodigoProducto.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtCodigo().toString()));
        tcCodigoBarrasProducto.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtCodBarra()));
        tcNombreProducto.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtNombre()));
        tcMarcaProducto.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtMarca()));
        tcUnidadProducto.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtUnidadMedida()));
        tcDescripcionProducto.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtDescripcion()));
        tcPrcioProducto.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtPrecio().toBigInteger().toString()));
        tcCantidad.setCellValueFactory((cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getInventario().getInvCantidad())));
        tcCantidad.setCellFactory(TextFieldTableCell.forTableColumn());
        tblProductos.setItems(FXCollections.observableArrayList(listaArticulos));
    }

    @FXML
    private void EliminarArticuloLista(ActionEvent event) {
         if (tblProductos.getSelectionModel().getSelectedItem() != null) {
            listaArticulos.remove(tblProductos.getSelectionModel().getSelectedItem());
            RecargarTblClientes();
        } else {
            new Alert(Alert.AlertType.WARNING, "Debe selecionar una fila de la tabla.", ButtonType.OK).showAndWait();
        }
    }
    /**
     * Lanza la ventana de busqueda
     */
    void ProcesoBusquedad() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Productos.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initOwner(btnBuscarProducto.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

        } catch (Exception ex) {
            System.err.print(ex);
        }
    }

}
