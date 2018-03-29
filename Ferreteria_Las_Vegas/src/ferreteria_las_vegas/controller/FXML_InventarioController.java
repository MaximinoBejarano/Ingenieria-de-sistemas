/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.CompraJpaController;
import ferreteria_las_vegas.model.controller.CuentasXPagarJpaController;
import ferreteria_las_vegas.model.controller.DetalleInventarioJpaController;
import ferreteria_las_vegas.model.controller.InventarioJpaController;
import ferreteria_las_vegas.model.controller.ProveedorJpaController;
import ferreteria_las_vegas.model.entities.Articulo;
import ferreteria_las_vegas.model.entities.ArticuloXCompra;
import ferreteria_las_vegas.model.entities.Compra;
import ferreteria_las_vegas.model.entities.CuentaXPagar;
import ferreteria_las_vegas.model.entities.DetalleInventario;
import ferreteria_las_vegas.model.entities.Inventario;
import ferreteria_las_vegas.model.entities.Proveedor;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.GeneralUtils;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import ferreteria_las_vegas.utils.SearchComboBox;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
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

    /*Objetos FXML------------------------------------------------------------*/
    @FXML
    private VBox DataPanel;

    @FXML
    private GridPane SearchBoxGrid;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnAgregarFactura;

    @FXML
    private Button btnBuscarFactura;

    @FXML
    private Button btnEditarFactura;

    @FXML
    private Button btnEliminarFactura;

    @FXML
    private TextField txtNomProveedor;

    @FXML
    private TextField txtNumFactura;

    @FXML
    private RadioButton rdbContado;

    @FXML
    private ToggleGroup tipoPago;

    @FXML
    private RadioButton rdbCredito;

    @FXML
    private DatePicker pickFeccha;

    @FXML
    private TextField txtFlete;

    @FXML
    private TextField txtServicioCarga;

    @FXML
    private CheckBox ckbAplicar;

    @FXML
    private Button btnBuscarProducto;

    @FXML
    private Button btnNuevoProducto;

    @FXML
    private Button btnEliminarProducto;
    @FXML
    private TableView<InventarioCompleto> tblProductos;
    @FXML
    private TableColumn<InventarioCompleto, String> tcCodigoProducto;
    @FXML
    private TableColumn<InventarioCompleto, String> tcDescuento;
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
    private TableColumn< InventarioCompleto, String> tcCantidad;
    @FXML
    private Label lblSubtotal;

    @FXML
    private Label lblDescuento;

    @FXML
    private Label lblImpuesto;

    @FXML
    private Label lblTotal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ComboBoxProveedores();
        BloquearBotones();
    }

    /*Evenetos FXML-----------------------------------------------------------*/
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

                    InventarioCompleto inventarioCompleto;
                    inventarioCompleto = new InventarioCompleto(articulo, 0, 0, 0);

                    if (listaArticulos.isEmpty()) {
                        listaArticulos.add(inventarioCompleto);
                    } else {
                        int listasize = listaArticulos.size();
                        for (int i = 0; i < listasize; i++) {

                            if (Objects.equals(listaArticulos.get(i).getArticulo().getArtCodigo(), inventarioCompleto.getArticulo().getArtCodigo())) {
                                Message.getInstance().Information("Cuidado", "Estos datos ya se encuentran ingresados en la tabla.");

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
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo agregar un nuevo producto. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    private void AgregarFactura(ActionEvent event) {

        if (!txtNomProveedor.getText().isEmpty() && !txtNumFactura.getText().isEmpty()
                && pickFeccha.getValue() != null && !listaArticulos.isEmpty() && !txtFlete.getText().isEmpty()
                && !txtServicioCarga.getText().isEmpty()) {
            ProcesoNuevaFactura();

        } else {
            Message.getInstance().Error("Acción no exitosa", "Se deben completar los campos requeridos.");
        }
    }

    @FXML
    private void BuscarArticulo(ActionEvent event) {
        try {
            ProcesoBusquedad();
            Articulo articulo = (Articulo) AppContext.getInstance().get("articulo-Ingresado");
            if (articulo != null) {

                InventarioCompleto inventarioCompleto;
                inventarioCompleto = new InventarioCompleto(articulo, 0, 0, 0);

                if (listaArticulos.isEmpty()) {
                    listaArticulos.add(inventarioCompleto);
                } else {
                    int listasize = listaArticulos.size();
                    for (int i = 0; i < listasize; i++) {
                        if (Objects.equals(listaArticulos.get(i).getArticulo().getArtCodigo(), inventarioCompleto.getArticulo().getArtCodigo())) {
                            Message.getInstance().Information("Cuidado", "Estos datos ya se encuentran ingresados en la tabla.");
                            break;
                        }
                        if (!listaArticulos.get(i).equals(inventarioCompleto) && ((i + 1) == listasize)) {
                            listaArticulos.add(inventarioCompleto);
                        }
                    }
                }

                RecargarTblClientes();
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo búscar un nuevo producto. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    private void EliminarArticuloLista(ActionEvent event) {
        try {
            if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                listaArticulos.remove(tblProductos.getSelectionModel().getSelectedItem());
                RecargarTblClientes();
            } else {
                Message.getInstance().Information("Información", "Seleccione un registro de la tabla.");
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo eliminar un producto de la tabla. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

      @FXML
    void txtFleteTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtFlete.getText().length(), 8, event);
    }

    @FXML
    void txtNombreVendTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtNomProveedor.getText().length(), 30, event);
    }

    @FXML
    void txtNumFacturaTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtNumFactura.getText().length(), 30, event);
    }

    @FXML
    void txtServicioCargaTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtServicioCarga.getText().length(), 8, event);
    }

    
    /*Procesos fundamentales--------------------------------------------------*/
    public void ProcesoBusquedad() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Productos.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initOwner(btnBuscarProducto.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo abrir la pantalla de búsqueda de productos. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    public void ProcesoNuevaFactura() {
        try {
            if (!lblTotal.getText().equals("0")) {
                Compra compra = new Compra();
                // susutituir por metodo de busca de proveedor 
                Proveedor pro = ProveedorJpaController.getInstance().ConsultarProveedoresTodos().get(0);

                compra.setComProveedor(pro);
                compra.setComNombre(txtNomProveedor.getText());
                compra.setComNumeroFact(txtNumFactura.getText());
                compra.setComFlete(Double.valueOf(txtFlete.getText()));
                compra.setComServCarga(Double.valueOf(txtServicioCarga.getText()));
                if (rdbContado.isSelected()) {
                    compra.setComTipoFact("D");
                } else if (rdbCredito.isSelected()) {
                    compra.setComTipoFact("C");
                }
                compra.setComCodigo(Integer.SIZE);
                compra.setComFecha(Date.from(Instant.from(pickFeccha.getValue().atStartOfDay(ZoneId.systemDefault()))));
                compra.setComSubTotal(Double.valueOf(lblSubtotal.getText()));
                compra.setComDescuento(Double.valueOf(lblDescuento.getText()));
                compra.setComImpVenta(Double.valueOf(lblImpuesto.getText()));
                compra.setComTotal(Double.valueOf(lblTotal.getText()));
                compra.setComEstado("A");
                compra.setComFleteC(Double.valueOf(0));

                List<ArticuloXCompra> lArticulos = new ArrayList<>();

                for (InventarioCompleto listaArticulo : listaArticulos) {

                    ArticuloXCompra articuloXcompra = new ArticuloXCompra();
                    articuloXcompra.setArtCodigo(Integer.SIZE);
                    articuloXcompra.setArtArticulo(listaArticulo.getArticulo());
                    articuloXcompra.setArtCantidad(listaArticulo.getCantArticulo());
                    articuloXcompra.setArtCompra(compra);
                    articuloXcompra.setArtPrecio(listaArticulo.getPrecioArt());
                    articuloXcompra.setArtEstado("A");
                    lArticulos.add(articuloXcompra);

                    if (ckbAplicar.isSelected()) {

                        Inventario pInventario = new Inventario();
                        pInventario.setInvCodigo(Integer.SIZE);
                        pInventario.setInvArticulo(listaArticulo.getArticulo());
                        pInventario.setInvBodega(null);
                        pInventario.setInvFecha(new Date());
                        pInventario.setInvCantidad(listaArticulo.getCantArticulo());
                        pInventario.setInvEstado("A");

                        DetalleInventario pDetalleInventario = new DetalleInventario();
                        pDetalleInventario.setDetCodigo(Integer.SIZE);
                        pDetalleInventario.setDetFecha(new Date());
                        pDetalleInventario.setDetPrecio(listaArticulo.getPrecioArt());
                        pDetalleInventario.setDetInventario(pInventario);

                        Inventario i = new InventarioJpaController().ConsultarInventarioCodigoProducto(pInventario.getInvArticulo().getArtCodigo());

                        if (new InventarioJpaController().ConsultarInventarioCodigoProducto(pInventario.getInvArticulo().getArtCodigo()) != null) {

                            pInventario = new InventarioJpaController().ConsultarInventarioCodigoProducto(pInventario.getInvArticulo().getArtCodigo());
                            pInventario.setInvCantidad(pInventario.getInvCantidad() + listaArticulo.getCantArticulo());
                            pDetalleInventario.setDetInventario(pInventario);
                            new DetalleInventarioJpaController().AgregarDetalleInventario(pDetalleInventario);
                            new InventarioJpaController().ModificarInventario(pInventario);
                        } else {
                            new InventarioJpaController().InsertarInvetario(pInventario, pDetalleInventario);
                        }
                    }
                }
                if (rdbContado.isSelected()) {
                    new CompraJpaController().InsertarCompra(compra, lArticulos);
                } else if (rdbCredito.isSelected()) {
                    Compra pCompra = new CompraJpaController().InsertarCompra(compra, lArticulos);
                    CuentaXPagar pCuenta = new CuentaXPagar();
                    pCuenta.setCueCodigo(Integer.SIZE);
                    pCuenta.setCueCompra(pCompra);
                    pCuenta.setCueProveedor(pCompra.getComProveedor());
                    pCuenta.setCueSaldo(pCompra.getComTotal());
                    pCuenta.setCueSaldoCompra(pCompra.getComTotal());
                    pCuenta.setCueEstado("A");
                    new CuentasXPagarJpaController().AgregarCuentaXCobrar(pCuenta);
                }

                Message.getInstance().Confirmation("Exito", "El proceso de registro de una factura de inventario se a realizado exitosamente.");
                LimpiarInterface();
            } else {
                Message.getInstance().Error("Error", "El proceso de registro de una factura de inventario no se puede realizar con un monto total de cero.");
            }

        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo agregar la factura de Inventario. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    /*Metodos importantes que no son procesos---------------------------------*/
    public void CargarProductos() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Productos.fxml"));
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnNuevoProducto.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error al intentar abrir la pantalla de Productos. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }

    }

    public void RecargarTblClientes() {
        try {
            tblProductos.getItems().clear();
            tblProductos.setEditable(true);
            tcCodigoProducto.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtCodigo().toString()));
            tcNombreProducto.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtNombre()));
            tcMarcaProducto.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtMarca()));
            tcUnidadProducto.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtUnidadMedida()));
            tcDescripcionProducto.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtDescripcion()));
            tcPrcioProducto.setCellValueFactory((cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecioArt())));
            tcPrcioProducto.setCellFactory(TextFieldTableCell.forTableColumn());
            tcCantidad.setCellValueFactory((cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCantArticulo())));
            tcCantidad.setCellFactory(TextFieldTableCell.forTableColumn());
            EditarTable();
            tcDescuento.setCellValueFactory((cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDescuentoComercio())));
            tblProductos.setItems(FXCollections.observableArrayList(listaArticulos));
            AppContext.getInstance().set("articulo-Ingresado", null);
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo recargar la tabla. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    /*Otros metodos-----------------------------------------------------------*/
    public void EditarTable() {
        try {
            tcPrcioProducto.setOnEditCommit(data -> {
                String valor = data.getNewValue().replace(",", ".");
                data.getRowValue().setPrecioArt(Double.valueOf(valor));
                
            });
            tcCantidad.setOnEditCommit(data -> {
                if (data.getRowValue().getPrecioArt() != 0) {
                    if (!txtFlete.getText().isEmpty() && !txtServicioCarga.getText().isEmpty()) {
                        TextInputDialog dialog = new TextInputDialog("0");
                        dialog.setTitle("Descuentos");
                        dialog.setHeaderText("¿Desea agregar un descuento a este producto?");
                        dialog.setContentText("Porcentaje del descuento:");
                        Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()) {
                            if (result.get().length() > 1) {
                                data.getRowValue().setDescuentoComercio(Double.valueOf("0." + result.get()));
                            } else {
                                data.getRowValue().setDescuentoComercio(Double.valueOf("0.0" + result.get()));
                            }
                            RecargarTblClientes();
                        }
                        data.getRowValue().setCantArticulo(Integer.valueOf(data.getNewValue()));

                        lblSubtotal.setText(String.format("%.2f", (Double.valueOf(lblSubtotal.getText())
                                + (data.getRowValue().getArticulo().getArtPrecio()
                                * Double.valueOf(data.getNewValue()))))
                        );
                        lblImpuesto.setText(String.format("%.2f", ((Double.valueOf(lblSubtotal.getText()) - Double.valueOf(lblDescuento.getText())) * 0.13)));
                        lblDescuento.setText(String.format("%.2f", (Double.valueOf(lblDescuento.getText()) + ((data.getRowValue().getArticulo().getArtPrecio()
                                * Double.valueOf(data.getNewValue())) * data.getRowValue().getDescuentoComercio())))
                        );
                        lblTotal.setText(String.format("%.2f", ((Double.valueOf(lblSubtotal.getText()) - Double.valueOf(lblDescuento.getText()))
                                + (Double.valueOf(lblImpuesto.getText()) + Double.valueOf(txtFlete.getText()) + Double.valueOf(txtServicioCarga.getText())))));
                    } else {
                        Message.getInstance().Error("Error", "Ocurrió un error y se debe completar el campo del valor del flete y servicio de carga.");
                        data.consume();
                        RecargarTblClientes();
                    }
                } else {
                    Message.getInstance().Information("Cuidado", "El precio de un articulo no puede ser 0");
                    data.consume();
                    RecargarTblClientes();
                }
            });
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo búscar un nuevo producto. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    private void CargarProveedores(SearchComboBox<Proveedor> box) {
        try {
            List<Proveedor> proveedores = new ProveedorJpaController().ConsultarProveedoresTodos().stream().filter(e -> e.getProEstado().equalsIgnoreCase("A")).collect(Collectors.toList());

            ObservableList<Proveedor> OClienteList = FXCollections.observableArrayList(proveedores);

            box.setItems(OClienteList);

        } catch (Exception ex) {

            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo cargar la información de los proveedores. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    private void ComboBoxProveedores() {

        try {
            boxProveedores = new SearchComboBox<>();
            boxProveedores.setMinHeight(33);
            boxProveedores.setMinWidth(176);
            boxProveedores.getSelectionModel().select(0);
            boxProveedores.setPromptText("Selecionar Proveedor");
            boxProveedores.setFilter((Proveedor t, String u) -> (t.getProNombre()).toUpperCase().contains(u.toUpperCase()));
            CargarProveedores(boxProveedores);

            SearchBoxGrid.add(boxProveedores, 0, 0);

        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo cargar el combo box de proveedores. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }

    }

    /*Metodos GUI-------------------------------------------------------------*/
    private void LimpiarInterface() {
        txtFlete.clear();
        txtServicioCarga.clear();
        txtNomProveedor.clear();
        txtNumFactura.clear();
        rdbContado.setSelected(true);
        pickFeccha.getEditor().clear();
        ckbAplicar.setSelected(false);
        tblProductos.getItems().clear();
        lblDescuento.setText("0");
        lblImpuesto.setText("0");
        lblSubtotal.setText("0");
        lblTotal.setText("0");
        btnEliminarProducto.setDisable(true);
        btnEliminarFactura.setDisable(true);
        btnEditarFactura.setDisable(true);
    }
    private void BloquearBotones(){
        btnEliminarProducto.setDisable(false);
        btnEliminarFactura.setDisable(false);
        btnEditarFactura.setDisable(false);
    }
    /*Metodos Lanzadores------------------------------------------------------*/
 /*Variables de Clase------------------------------------------------------*/
    private ArrayList<InventarioCompleto> listaArticulos = new ArrayList<InventarioCompleto>();
    private SearchComboBox<Proveedor> boxProveedores;
}
