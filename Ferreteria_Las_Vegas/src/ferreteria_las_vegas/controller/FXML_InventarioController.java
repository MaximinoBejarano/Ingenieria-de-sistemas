/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.ArticulosXCompraJpaController;
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
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private Button btnLimpiarPantalla;

    @FXML
    private Button btnAgregarFacturas;

    @FXML
    private Button btnBuscarFacturas;

    @FXML
    private Button btnEditarFacturas;

    @FXML
    private Button btnEliminarFacturas;

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
    private TextField txtDescuento;

    @FXML
    private CheckBox ckbAplicar;

    @FXML
    private Button btnBuscarProductos;

    @FXML
    private Button btnNuevoProductos;

    @FXML
    private Button btnEliminarProductos;
    @FXML
    private TableView<InventarioCompleto> tblProductos;
    @FXML
    private TableColumn<InventarioCompleto, String> tcCodigoProducto;

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
        btnEditarFacturas.setDisable(false);
        LimpiarInterface();
    }

    /*Evenetos FXML-----------------------------------------------------------*/
    @FXML
    private void SalirClick(ActionEvent event) {
        try {
            ScenesManager.getInstance().LoadSceneMenu();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo volver a la pantalla de menú.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    private void AgregarProducto(ActionEvent event) {
        try {
            if (true) {
                LanzarProductos();
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
                && !txtServicioCarga.getText().isEmpty() && !txtDescuento.getText().isEmpty()
                && boxProveedores.getSelectionModel().getSelectedItem() != null) {
            boolean falta = false;
            for (InventarioCompleto listaArticulo : listaArticulos) {
                if (listaArticulo.getCantArticulo() == 0) {
                    falta = true;
                }
            }
            if (!falta) {
                ProcesoNuevaFactura();
            } else {
                Message.getInstance().Error("Acción no exitosa", "No se pueden ingresar articulos con cantidades en cero.");
            }

        } else {
            Message.getInstance().Error("Acción no exitosa", "Se deben completar los campos requeridos.");
        }
    }

    @FXML
    private void BuscarArticulo(ActionEvent event) {
        try {
            LanzarBusquedaAritculos();
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
    private void BuscarFacturaInventario(ActionEvent event) {
        try {
            LimpiarInterface();
            LanzarBusquedaFactura();
            Compra pCompra = (Compra) AppContext.getInstance().get("seleccion-FacInventario");
            if (pCompra != null) {
                boxProveedores.getSelectionModel().select(pCompra.getComProveedor());
                txtNomProveedor.setText(pCompra.getComNombre());
                txtNumFactura.setText(pCompra.getComNumeroFact());
                txtDescuento.setText(String.valueOf(pCompra.getComDescuento()));
                txtFlete.setText(String.valueOf(pCompra.getComFlete()));
                txtServicioCarga.setText(String.valueOf(pCompra.getComServCarga()));
                lblDescuento.setText(String.format("%.2f", pCompra.getComSubTotal() * pCompra.getComDescuento()));
                lblSubtotal.setText(String.valueOf(pCompra.getComSubTotal()));
                lblImpuesto.setText(String.valueOf(pCompra.getComImpVenta()));
                lblTotal.setText(String.valueOf(pCompra.getComTotal()));

                pickFeccha.setValue(pCompra.getComFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                pickFeccha.requestFocus();
                boxProveedores.requestFocus();
                if (pCompra.getComTipoFact().equals("C")) {
                    rdbCredito.setSelected(true);
                } else {
                    rdbContado.setSelected(true);
                }
                rdbContado.setDisable(true);
                rdbCredito.setDisable(true);
                if (pCompra.getArticuloXCompraList().get(0).getArtEstado().equals("A")) {
                    ckbAplicar.setSelected(true);
                    ckbAplicar.setDisable(true);
                }

                if (pCompra != null) {
                    listaArticulos.clear();
                    for (ArticuloXCompra articuloXCompraList : pCompra.getArticuloXCompraList()) {
                        InventarioCompleto pInventarioCompleto = new InventarioCompleto();
                        pInventarioCompleto.setArticulo(articuloXCompraList.getArtArticulo());
                        pInventarioCompleto.setCantArticulo(articuloXCompraList.getArtCantidad());
                        pInventarioCompleto.setPrecioArt(articuloXCompraList.getArtPrecio());
                        pInventarioCompleto.setDescuentoComercio(0);
                        listaArticulos.add(pInventarioCompleto);
                        RecargarTblClientes();
                    }

                }
                btnEliminarFacturas.setDisable(false);
                btnEditarFacturas.setDisable(false);
                btnNuevoProductos.setDisable(true);
                btnEliminarProductos.setDisable(true);
                btnBuscarProductos.setDisable(true);
            }

        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo búscar una nueva factura. "
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
    private void EditarFacturaInventario(ActionEvent event) {
        ProcesoEditarFactura(false);
    }

    @FXML
    private void EliminarFacturaInventario(ActionEvent event) {
        ProcesoEditarFactura(true);
    }

    @FXML
    private void txtFleteTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtFlete.getText().length(), 8, event);
    }

    @FXML
    private void txtNombreVendTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtNomProveedor.getText().length(), 30, event);
    }

    @FXML
    private void txtNumFacturaTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtNumFactura.getText().length(), 30, event);
    }

    @FXML
    private void txtServicioCargaTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtServicioCarga.getText().length(), 8, event);
    }

    @FXML
    private void txtDescuentoTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtServicioCarga.getText().length(), 8, event);
        txtDescuento.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (txtDescuento.getText().length() == 1) {
                    txtDescuento.setText("0.0" + txtDescuento.getText());
                } else {
                    txtDescuento.setText("0." + txtDescuento.getText());
                    txtDescuento.setText(txtDescuento.getText().replace("0.0.", "0."));
                }
            }
        });
    }

    @FXML
    private void btnLimpiarCampos(ActionEvent event) {
        LimpiarInterface();
        AppContext.getInstance().set("seleccion-FacInventario", null);
    }

    /*Procesos fundamentales--------------------------------------------------*/
    public void ProcesoNuevaFactura() {
        try {
            if (new CompraJpaController().ConsultarCompraCodigoFac(txtNumFactura.getText()) == null) {
                if (!lblTotal.getText().equals("0")) {
                    Compra compra = new Compra();
                    // susutituir por metodo de busca de proveedor 
                    Proveedor pro = boxProveedores.getSelectionModel().getSelectedItem();

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
                    compra.setComDescuento(Double.valueOf(txtDescuento.getText()));
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
                        articuloXcompra.setArtEstado("I");

                        if (ckbAplicar.isSelected()) {
                            articuloXcompra.setArtEstado("A");
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
                        lArticulos.add(articuloXcompra);
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
                        new CuentasXPagarJpaController().AgregarCuentasXPagar(pCuenta);
                    }

                    Message.getInstance().Information("Exito", "El proceso de registro de una factura de inventario se a realizado exitosamente.");
                    LimpiarInterface();
                } else {
                    Message.getInstance().Error("Error", "El proceso de registro de una factura de inventario no se puede realizar con un monto total de cero.");
                }
            } else {
                Message.getInstance().Error("Error", "El proceso de registro de una factura de inventario no se puede realizar porque ya se encontro registrada otra factura con este número.");
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo agregar la factura de Inventario. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    public void ProcesoEditarFactura(boolean eliminar) {
        try {
            if (!ckbAplicar.isDisable()) {
                if (!lblTotal.getText().equals("0")) {
                    Compra compra = (Compra) AppContext.getInstance().get("seleccion-FacInventario");
                    Proveedor pro = boxProveedores.getSelectionModel().getSelectedItem();

                    compra.setComProveedor(pro);
                    compra.setComNombre(txtNomProveedor.getText());
                    compra.setComNumeroFact(txtNumFactura.getText());
                    compra.setComFlete(Double.valueOf(txtFlete.getText()));
                    compra.setComServCarga(Double.valueOf(txtServicioCarga.getText()));

                    compra.setComFecha(Date.from(Instant.from(pickFeccha.getValue().atStartOfDay(ZoneId.systemDefault()))));
                    compra.setComSubTotal(Double.valueOf(lblSubtotal.getText()));
                    compra.setComDescuento(Double.valueOf(txtDescuento.getText()));
                    compra.setComImpVenta(Double.valueOf(lblImpuesto.getText()));
                    compra.setComTotal(Double.valueOf(lblTotal.getText()));
                    if (eliminar) {
                        compra.setComEstado("I");
                    }

                    new CompraJpaController().ModificarCompra(compra);
                    List<ArticuloXCompra> pArticuloXCompras = compra.getArticuloXCompraList();

                    if (pArticuloXCompras.get(0).getArtEstado().equals("I")) {

                        for (int i = 0; i < pArticuloXCompras.size(); i++) {
                            pArticuloXCompras.get(i).setArtCantidad(listaArticulos.get(i).getCantArticulo());
                            pArticuloXCompras.get(i).setArtPrecio(listaArticulos.get(i).getPrecioArt());
                            pArticuloXCompras.get(i).setArtEstado("A");
                            new ArticulosXCompraJpaController().ModificarArticuloXCompra(pArticuloXCompras.get(i));

                            if (ckbAplicar.isSelected()) {
                                Inventario pInventario = new Inventario();
                                pInventario.setInvCodigo(Integer.SIZE);
                                pInventario.setInvArticulo(pArticuloXCompras.get(i).getArtArticulo());
                                pInventario.setInvBodega(null);
                                pInventario.setInvFecha(new Date());
                                pInventario.setInvCantidad(pArticuloXCompras.get(i).getArtCantidad());
                                pInventario.setInvEstado("A");

                                DetalleInventario pDetalleInventario = new DetalleInventario();
                                pDetalleInventario.setDetCodigo(Integer.SIZE);
                                pDetalleInventario.setDetFecha(new Date());
                                pDetalleInventario.setDetPrecio(pArticuloXCompras.get(i).getArtPrecio());
                                pDetalleInventario.setDetInventario(pInventario);

                                if (new InventarioJpaController().ConsultarInventarioCodigoProducto(pInventario.getInvArticulo().getArtCodigo()) != null) {

                                    pInventario = new InventarioJpaController().ConsultarInventarioCodigoProducto(pInventario.getInvArticulo().getArtCodigo());
                                    pInventario.setInvCantidad(pInventario.getInvCantidad() + pArticuloXCompras.get(i).getArtCantidad());
                                    pDetalleInventario.setDetInventario(pInventario);
                                    new DetalleInventarioJpaController().AgregarDetalleInventario(pDetalleInventario);
                                    new InventarioJpaController().ModificarInventario(pInventario);
                                } else {
                                    new InventarioJpaController().InsertarInvetario(pInventario, pDetalleInventario);
                                }
                            }
                        }
                    }

                    if (rdbCredito.isSelected()) {
                        Compra pCompra = (Compra) AppContext.getInstance().get("seleccion-FacInventario");
                        CuentaXPagar pCuenta = new CuentasXPagarJpaController().ConsultarCuentaXPagar(pCompra.getComCodigo());

                        pCuenta.setCueProveedor(pCompra.getComProveedor());
                        pCuenta.setCueSaldo(pCompra.getComTotal());
                        pCuenta.setCueSaldoCompra(pCompra.getComTotal());
                        if (eliminar) {
                            pCuenta.setCueEstado("I");
                        }

                        new CuentasXPagarJpaController().ModificarCuentaXPagar(pCuenta);
                    }
                    if (eliminar) {
                        Message.getInstance().Information("Exito", "El proceso de eliminar una factura de inventario se a realizado exitosamente.");
                    } else {
                        Message.getInstance().Information("Exito", "El proceso de registro de una factura de inventario se a realizado exitosamente.");
                    }
                    LimpiarInterface();
                } else {
                    Message.getInstance().Error("Error", "El proceso de registro de una factura de inventario no se puede realizar con un monto total de cero.");
                }
            } else {
                Message.getInstance().Error("Error", "Esta tarea no se puede realizar ya que ésta factura ya se encuentra ingresada en el inventario ");

            }

        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo agregar la factura de Inventario. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    private void GenerarCalculos() {
        for (InventarioCompleto listaArticulo : listaArticulos) {

            lblSubtotal.setText(String.format("%.2f", (Double.valueOf(lblSubtotal.getText()) + (listaArticulo.getPrecioArt() * listaArticulo.getCantArticulo()))));
            lblDescuento.setText(String.format("%.2f", Double.valueOf(txtDescuento.getText()) * Double.valueOf(lblSubtotal.getText())));
            lblImpuesto.setText(String.format("%.2f", ((Double.valueOf(lblSubtotal.getText()) - Double.valueOf(lblDescuento.getText())) * 0.13)));
            lblTotal.setText(String.format("%.2f", ((Double.valueOf(lblSubtotal.getText()) - Double.valueOf(lblDescuento.getText()))
                    + (Double.valueOf(lblImpuesto.getText()) + Double.valueOf(txtFlete.getText()) + Double.valueOf(txtServicioCarga.getText())))));
        }
    }

    /*Metodos importantes que no son procesos---------------------------------*/
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
            tblProductos.setItems(FXCollections.observableArrayList(listaArticulos));
            AppContext.getInstance().set("articulo-Ingresado", null);
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo recargar la tabla. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    /*Otros metodos-----------------------------------------------------------*/
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
        listaArticulos.clear();
        boxProveedores.getSelectionModel().clearSelection();
        txtServicioCarga.clear();
        txtNomProveedor.clear();
        txtNumFactura.clear();
        txtDescuento.clear();
        rdbContado.setSelected(true);
        rdbContado.setDisable(false);
        rdbCredito.setDisable(false);
        pickFeccha.getEditor().clear();
        pickFeccha.requestFocus();
        boxProveedores.requestFocus();
        ckbAplicar.setSelected(false);
        ckbAplicar.setDisable(false);
        tblProductos.getItems().clear();
        lblDescuento.setText("0");
        lblImpuesto.setText("0");
        lblSubtotal.setText("0");
        lblTotal.setText("0");
        btnNuevoProductos.setDisable(false);
        btnEliminarProductos.setDisable(false);
        btnBuscarProductos.setDisable(false);
        btnEliminarFacturas.setDisable(true);
        btnEditarFacturas.setDisable(true);
    }

    public void EditarTable() {
        try {

            tcPrcioProducto.setOnEditCommit(data -> {
                boolean procesar = false;
                for (int i = 0; i < data.getNewValue().length(); i++) {
                    if (!Character.isDigit(data.getNewValue().charAt(i)) && data.getNewValue().charAt(i) != '.') {
                        procesar = true;
                    }
                }
                if (!procesar) {
                    if (!txtFlete.getText().isEmpty() && !txtServicioCarga.getText().isEmpty()) {
                        String valor = data.getNewValue().replace(",", ".");
                        data.getRowValue().setPrecioArt(Double.valueOf(valor));
                        data.getRowValue().setPrecioArt(Double.valueOf(data.getNewValue()));
                        lblSubtotal.setText("0");
                        lblTotal.setText("0");
                        GenerarCalculos();

                    } else {
                        Message.getInstance().Error("Error", "Ocurrió un error y se debe completar el campo del valor del flete, servicio de carga y descuento.");
                        data.consume();
                        RecargarTblClientes();
                    }
                } else {
                    Message.getInstance().Error("Error", "Este campo no permite letras en su contenido.");
                    data.consume();
                    RecargarTblClientes();
                }
            });

            tcCantidad.setOnEditCommit(data -> {
                if (data.getRowValue().getPrecioArt() != 0) {
                    boolean procesar = false;
                    for (int i = 0; i < data.getNewValue().length(); i++) {
                        if (!Character.isDigit(data.getNewValue().charAt(i))) {
                            procesar = true;
                        }
                    }
                    if (!procesar) {
                        if (!txtFlete.getText().isEmpty() && !txtServicioCarga.getText().isEmpty()) {
                            data.getRowValue().setCantArticulo(Integer.valueOf(data.getNewValue()));
                            lblSubtotal.setText("0");
                            lblTotal.setText("0");
                            GenerarCalculos();

                        } else {
                            Message.getInstance().Error("Error", "Ocurrió un error y se debe completar el campo del valor del flete, servicio de carga y descuento.");
                            data.consume();
                            RecargarTblClientes();
                        }
                    } else {
                        Message.getInstance().Error("Error", "Este campo no permite letras o caracteres en su contenido.");
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


    /*Metodos Lanzadores------------------------------------------------------*/
    public void LanzarProductos() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Productos.fxml"));
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnNuevoProductos.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error al intentar abrir la pantalla de Productos. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }

    }

    public void LanzarBusquedaAritculos() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Productos.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initOwner(btnBuscarProductos.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo abrir la pantalla de búsqueda de productos. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    public void LanzarBusquedaFactura() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Factura_Inventario.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initOwner(btnBuscarFacturas.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo abrir la pantalla de búsqueda de facturas. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    /*Variables de Clase------------------------------------------------------*/
    private ArrayList<InventarioCompleto> listaArticulos = new ArrayList<InventarioCompleto>();
    private SearchComboBox<Proveedor> boxProveedores;
}
