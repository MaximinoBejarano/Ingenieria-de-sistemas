/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.ArticuloJpaController;
import ferreteria_las_vegas.model.controller.FacturaJPAController;
import ferreteria_las_vegas.model.controller.InventarioJpaController;
import ferreteria_las_vegas.model.entities.Factura;
import ferreteria_las_vegas.model.entities.Articulo;
import ferreteria_las_vegas.model.entities.ArticuloXFactura;
import ferreteria_las_vegas.model.entities.Cliente;
import ferreteria_las_vegas.model.entities.CuentaXCobrar;
import ferreteria_las_vegas.model.entities.Inventario;
import ferreteria_las_vegas.model.entities.Persona;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.GeneralUtils;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import ferreteria_las_vegas.utils.PrinterManagerFacturacion;
import ferreteria_las_vegas.utils.WorkIndicatorDialog;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
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
    private TableView<InventarioCompleto> tbl_Factura;
    @FXML
    private TableColumn<InventarioCompleto, String> colCodigo;

    @FXML
    private TableColumn<InventarioCompleto, String> ColProducto;

    @FXML
    private TableColumn<InventarioCompleto, String> colDescripción;

    @FXML
    private TableColumn<InventarioCompleto, String> colUnidadMedida;

    @FXML
    private TableColumn<InventarioCompleto, String> colDescuento;

    @FXML
    private TableColumn<InventarioCompleto, String> colCantida;

    @FXML
    private TableColumn<InventarioCompleto, String> colPrecio;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblCliente;
    @FXML
    private Label lblFecha;

    /**
     * Area de variables Globales
     */
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    Date fecha = new Date();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblFecha.setText(formatter.format(fecha.getTime()));
        InicializarValores();
    }

    @FXML
    private void btnBuscarArticulo_Click(ActionEvent event) {
        Lanzar_FXMLBuscarArticulo();
    }

    @FXML
    private void btnSalir_Click(ActionEvent event) {
        try {
            ScenesManager.getInstance().LoadSceneMenu();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de Menu.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    private void btnCredito_Click(ActionEvent event) {
        if (cliente != null) {
            if (!ListArticulos.isEmpty()) {
                AgregarDatosfactura();
                AgregarFacturaCredito();
                AppContext.getInstance().set("seleccion-FacReimprecion", false);
                ProcesoGenerarFactura();
                if (Message.getInstance().Confirmation("Confirmación", "Factura ingresada con exito desea realizar un abono")) {
                        Lanzar_FXML_Abonos();
                         InicializarValores();
                    } else {
                        Limpiar_Vista();
                    }
               
            } else {
                Message.getInstance().Information("Información:", "Es requerido agregar articulos a la factura");
            }
        } else {
            Message.getInstance().Information("Información:", "Es requerido seleccionar un cliente para la factura");
        }

    }

    @FXML
    private void btnCobrarFactura_Click(ActionEvent event) {
        if (cliente != null) {
            if (!ListArticulos.isEmpty()) {
                AgregarDatosfactura();
                pFactura.setFactEstadoPago("I");
                pFactura.setFactTipoFact("E");
                AppContext.getInstance().set("Factura", pFactura);
                AppContext.getInstance().set("ArticulosXFactura", listArticuloXFacturas);
                Lanzar_FXMLPagos();
                //Se encarga de volver al estado inicial la pantalla de factura si esta ya fue cancelada
                if ((boolean) AppContext.getInstance().get("pago") == true) {
                    Limpiar_Vista();
                    InicializarValores();
                }
            } else {
                Message.getInstance().Information("Información:", "Es requerido agregar articulos a la factura");
            }
        } else {
            Message.getInstance().Information("Información:", "Es requerido seleccionar un cliente para la factura");
        }
    }

    @FXML
    private void btnGuardarPedido_Click(ActionEvent event) {
        if (cliente != null) {
            if (!ListArticulos.isEmpty()) {
                AgregarDatosfactura();
                pFactura.setArticuloXFacturaList(listArticuloXFacturas);
                Limpiar_Vista();
                InicializarValores();
                ListPedidos.add(pFactura);
                AppContext.getInstance().set("ListPedidos", ListPedidos);
                if (Message.getInstance().Confirmation("Confirmación:", "El pedido se ha guardado de forma exitosa,desea buscar un pedido")) {
                    Lanzar_FXML_FacturaPendiete();
                }

            } else {
                Message.getInstance().Information("Información:", "Es requerido agregar articulos a la factura");
            }
        } else {
            Message.getInstance().Information("Información:", "Es requerido seleccionar un cliente para la factura");

        }

    }

    @FXML
    private void btnCliente_Click(ActionEvent event) {
        Lanzar_FXMLBuscarCliente();
        cliente = (Cliente) AppContext.getInstance().get("selected-Cliente");
        Persona per = cliente.getPersona();
        lblCliente.setText(String.valueOf(per.getPerCedula()) + " " + per.getPerNombre() + " " + per.getPerPApellido());
    }

    @FXML
    private void btnGenerarProforma_Click(ActionEvent event) {
    }

    @FXML
    private void btnBorrarLinea_Click(ActionEvent event) {
        Removerlinea_tabla();
        CargarTabla();
    }

    @FXML
    void txtCodigoArticulo_KeyTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtCodigoArticulo.getText().length(), 44, event);
    }

    @FXML
    void txtCodigoArticulo_OnAction(ActionEvent event) {
        CargasDatos();
    }


    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++Procesos fundamentales++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    public void AgregarDatosfactura() {
        pFactura = new Factura();
        listArticuloXFacturas = new ArrayList<>();
        pFactura.setFacCliente(cliente);
        pFactura.setFacFecha(fecha);
        pFactura.setFatSubtotal(Subtotal);
        pFactura.setFacDescuento(Descuento);
        pFactura.setFacImpVentas(ImpuestoVenta);
        pFactura.setFacTotal(Total);
        pFactura.setFactEstadoPago("A");
        pFactura.setFacEstado("A");
        for (InventarioCompleto pCompleto : ListArticulos) {
            ArticuloXFactura temp = new ArticuloXFactura();
            temp.setArtPrecio(pCompleto.getArticulo().getArtPrecio());
            temp.setArtCantidad(pCompleto.getCantArticulo());
            temp.setArtArticulo(pCompleto.getArticulo());
            temp.setArtCodigo(Integer.SIZE);
            temp.setArtFactura(pFactura);
            temp.setArtDescuento(pCompleto.getArticulo().getArtDescuento());
            listArticuloXFacturas.add(temp);
        }

    }

    /**
     * Se registra una factura a credito
     */
    public void AgregarFacturaCredito() {
        CuentaXCobrar pCuentaXCobrar = new CuentaXCobrar();
        try {
            if (pFactura != null) {
                pFactura.setFactTipoFact("K");
                pCuentaXCobrar.setCueCliente(pFactura.getFacCliente());
                pCuentaXCobrar.setCueCodigo(Integer.SIZE);
                pCuentaXCobrar.setCueSaldo(pFactura.getFacTotal());
                pCuentaXCobrar.setCueSaldoFac(pFactura.getFacTotal());
                pCuentaXCobrar.setCueFactura(pFactura);
                pCuentaXCobrar.setCueEstado("A");

                AppContext.getInstance().set("Factura", pFactura);
                AppContext.getInstance().set("ArticulosXFactura", listArticuloXFacturas);
                Factura pFacturar = FacturaJPAController.getInstance().AgregarFactura_Credito(pFactura, listArticuloXFacturas, pCuentaXCobrar);
                if (pFacturar != null) {
                    AppContext.getInstance().set("seleccion-FacCliente", pFacturar);
                    
                } else {
                    Message.getInstance().Error("Error", "Error al registrar la factura");
                }
            }
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
        }
    }

    /**
     * Se valida la existencia de un artículo en inventario y si posee con una
     * cantidad suficinte para cubrir con la demanda del producto
     *
     * @param pArticulo
     * @param cantidad
     * @return true or falsse
     */
    public boolean Validar_ExistenciaArticulo(Articulo pArticulo, int cantidad) {
        Inventario pInventario = new Inventario();
        if (InventarioJpaController.getInstance().ConsultarInventarioCodigoProducto(pArticulo.getArtCodigo()) != null) {
            pInventario = InventarioJpaController.getInstance().ConsultarInventarioCodigoProducto(pArticulo.getArtCodigo());

            if ((pInventario.getInvCantidad() > cantidad) && (pInventario.getInvCantidad() - cantidad) > 5) {
                return true;
            } else {
                if ((pInventario.getInvCantidad() >= cantidad) && (pInventario.getInvCantidad() - cantidad) >= 0) {

                    Message.getInstance().Information("Información", "Artículo con pocas existencias en inventario,"
                            + "\nCantida Actual: " + (pInventario.getInvCantidad() - cantidad));
                    return true;
                }
            }
        }
        return false;
    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++Metodos importantes que no son procesos+++++++++++++++++++++++++++++++++++++++++*/
    //Calculo de subtotal, descuento, impuesto de venta y total de la factura
    public void Calcular_Total() {
        //Calculo el subtotal y el descuento de cada uno de los articulos
        Subtotal = Descuento = Total = ImpuestoVenta = 0;
        for (InventarioCompleto art : ListArticulos) {
            Subtotal += art.getCantArticulo() * art.getArticulo().getArtPrecio();
            if (art.getArticulo().getArtEstadoDescuento().equalsIgnoreCase("A")) {
                Descuento += (art.getCantArticulo() * art.getArticulo().getArtPrecio()) * art.getArticulo().getArtDescuento();
            }
        }
        ImpuestoVenta = (Subtotal - Descuento) * 0.13;
        Total = ((Subtotal - Descuento) + ImpuestoVenta);
        lblTotal.setText(String.format("%.2f", Total));

    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++Otros metodos+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    public String ValidarNuemro(String Valor) {
        String pValor = "";
        for (int x = 0; x < Valor.length(); x++) {
            if ((int) Valor.charAt(x) > 47 && (int) Valor.charAt(x) < 58) {
                pValor += Valor.charAt(x);
            } else {
                return "";
            }
        }
        return pValor;
    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++Metodos GUI-++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    public void CargarTabla() {
        try {
            colCodigo.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtCodigo().toString())));
            ColProducto.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtNombre())));
            colDescripción.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtDescripcion())));
            colUnidadMedida.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().getArticulo().getArtUnidadMedida())));
            colDescuento.setCellValueFactory((cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getArticulo().getArtDescuento()))));
            colCantida.setCellValueFactory((cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCantArticulo()))));
            colPrecio.setCellValueFactory((cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecioArt()))));

            colCantida.setCellFactory(TextFieldTableCell.forTableColumn());

            ModificarTabla();
            ObservableList<InventarioCompleto> ListArCompletos = FXCollections.observableArrayList(ListArticulos);
            tbl_Factura.setItems(ListArCompletos);
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public void ModificarTabla() {
        try {
            // Se encarga de abstraer el valor de "Cantidad" ingresado por el usuario
            colCantida.setOnEditCommit(Date -> {
                String Valor = "";
                Valor = ValidarNuemro(Date.getNewValue());
                if (!Date.getNewValue().isEmpty() && Valor != "") {
                    if (Integer.valueOf(Date.getNewValue()) > 0) {
                        if (Validar_ExistenciaArticulo(Date.getRowValue().getArticulo(), Integer.parseInt(Date.getNewValue()))) {
                            Date.getRowValue().setCantArticulo(Integer.valueOf(Date.getNewValue()));
                            Calcular_Total();
                        } else {
                            Message.getInstance().Information("Informació", "Artículo sin existencias");
                        }
                    } else {
                        Date.getRowValue().setCantArticulo(Integer.valueOf(Date.getOldValue()));
                        Calcular_Total();
                    }
                } else {
                    Date.getRowValue().setCantArticulo(Integer.valueOf(Date.getOldValue()));
                    Calcular_Total();
                }
                CargarTabla();
            });
        } catch (Exception ex) {
            System.err.println(ex);
        }

    }

    public void Removerlinea_tabla() {
        if (tbl_Factura.getSelectionModel().getSelectedItem() != null) {
            ListArticulos.remove(tbl_Factura.getSelectionModel().getSelectedItem());
            Calcular_Total();
        } else {
            Message.getInstance().Warning("Advertencia:", "Por favor seleccione el artículo que desea eliminar");
        }
    }

    public void CargasDatos() {
        try {
            if (pArticulo == null) {
                if (!txtCodigoArticulo.getText().isEmpty()) {
                    pArticulo = ArticuloJpaController.getInstance().ConsultarArticuloCodBarras(txtCodigoArticulo.getText());

                    if (pArticulo != null) {
                        Limpiar_Vista();
                        ProcesoCargaInformacion();
                        pArticulo = null;
                    } else {
                        txtCodigoArticulo.setText("");
                        Message.getInstance().Warning("Advertencia:", "El Artículo no existe");
                    }
                }
            } else {
                ProcesoCargaInformacion();
            }

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public void ProcesoCargaInformacion() {
        InventarioCompleto pCompleto = new InventarioCompleto();
        pCompleto.setArticulo(pArticulo);
        boolean bandera = true;
        try {
            if (cliente != null) {
                Persona per = cliente.getPersona();
                lblCliente.setText(String.valueOf(per.getPerCedula()) + " " + per.getPerNombre() + " " + per.getPerPApellido());
            }
            if (!ListArticulos.isEmpty()) {
                pCompleto = CalcularPrecioArticulo(pCompleto);
                //si existe un articulo repetido en la lista se aumenta su cantidad
                for (InventarioCompleto pArCompleto : ListArticulos) {
                    if (pArCompleto.getArticulo().getArtCodigo() == pCompleto.getArticulo().getArtCodigo()) {
                        pCompleto.setCantArticulo(pArCompleto.getCantArticulo() + 1);//Aumento la cantidad de articulos
                        if (Validar_ExistenciaArticulo(pCompleto.getArticulo(), pCompleto.getCantArticulo())) {
                            ListArticulos.set(ListArticulos.indexOf(pArCompleto), pCompleto);
                            bandera = false;
                        } else {
                            bandera = false;
                            Message.getInstance().Information("Informació", "Artículo con pocas existencias en inventario, imposible incrementar cantida");
                        }
                    }
                }

                //Si el articulo no existe en la lista se agrega uno nuevo
                if (bandera) {
                    pCompleto.setCantArticulo(1);
                    if (Validar_ExistenciaArticulo(pCompleto.getArticulo(), pCompleto.getCantArticulo())) {
                        ListArticulos.add(pCompleto);
                    } else {
                        Message.getInstance().Information("Información", "El artículo no se puede agregar debido a que se agoto o no existe en bodega");
                    }
                }
            } else {
                pCompleto = CalcularPrecioArticulo(pCompleto);
                pCompleto.setCantArticulo(1);
                if (Validar_ExistenciaArticulo(pCompleto.getArticulo(), pCompleto.getCantArticulo())) {
                    ListArticulos.add(pCompleto);
                } else {
                    Message.getInstance().Information("Información", "El artículo no se puede agregar debido a que se agoto o no existe en bodega");
                }
            }
            Calcular_Total();
            CargarTabla();

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public InventarioCompleto CalcularPrecioArticulo(InventarioCompleto parametro) {
        double Descuentop, subPrecio;
        Descuentop = parametro.getArticulo().getArtPrecio() * parametro.getArticulo().getArtDescuento();
        if (Descuentop >= 0) {
            subPrecio = parametro.getArticulo().getArtPrecio() - Descuentop;
            parametro.setPrecioArt(subPrecio + (subPrecio * 0.13));
            return parametro;
        } else {
            parametro.setPrecioArt(parametro.getArticulo().getArtPrecio());
            return parametro;
        }
    }

    public void CargarInfo_FacturaPendiente() {
        Factura tempFactura = null;
        Articulo tempArticulo = new Articulo();
        InventarioCompleto pCompleto = new InventarioCompleto();
        tempFactura = (Factura) AppContext.getInstance().get("seleccion-Factura");
        if (tempFactura != null) {
            Persona per = tempFactura.getFacCliente().getPersona();
            lblCliente.setText(String.valueOf(per.getPerCedula()) + " " + per.getPerNombre() + " " + per.getPerPApellido());
            listArticuloXFacturas = tempFactura.getArticuloXFacturaList();
            for (ArticuloXFactura pArtXFact : listArticuloXFacturas) {
                tempArticulo = ArticuloJpaController.getInstance().ConsultarArticuloCodigo(pArtXFact.getArtCodigo());
                if (Validar_ExistenciaArticulo(tempArticulo, pArtXFact.getArtCantidad())) {
                    pCompleto.setArticulo(tempArticulo);
                    pCompleto.setCantArticulo(pArtXFact.getArtCantidad());
                } else {
                    Inventario pInventario = new Inventario();
                    if (InventarioJpaController.getInstance().ConsultarInventarioCodigoProducto(tempArticulo.getArtCodigo()) != null) {
                        pInventario = InventarioJpaController.getInstance().ConsultarInventarioCodigoProducto(tempArticulo.getArtCodigo());
                        if ((pInventario.getInvCantidad() < pArtXFact.getArtCantidad()) && (pInventario.getInvCantidad() - pArtXFact.getArtCantidad()) < 0) {
                            pCompleto.setArticulo(tempArticulo);
                            pCompleto.setCantArticulo(pInventario.getInvCantidad());
                            pCompleto = CalcularPrecioArticulo(pCompleto);
                            ListArticulos.add(pCompleto);
                            Message.getInstance().Warning("Advertencia", "La cantidad del articulo:" + tempArticulo.getArtNombre()
                                    + " en inventario es insuficiente,la cantidad disponible es:" + pInventario.getInvCantidad());
                        }
                    }
                }

            }
            CargarTabla();
            Calcular_Total();

        }
    }

    public void Limpiar_Vista() {
        lblTotal.setText("");
        lblCliente.setText("");
        lblFecha.setText(formatter.format(fecha.getTime()));
        txtCodigoArticulo.setText("");
        tbl_Factura.getItems().clear();
    }

    public void InicializarValores() {
        cliente = null;
        pFactura = null;
        ListArticulos = new ArrayList<>();
    }

    /*++++++++++++++++++++++++++++++++++++++++++++++++++++++Metodos Lanzadores+++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    public void Lanzar_FXMLPagos() {
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

    public void Lanzar_FXMLBuscarCliente() {
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

    public void Lanzar_FXMLBuscarArticulo() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Productos.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnBuscarArticulo.getScene().getWindow());
            stage.showAndWait();
            pArticulo = (Articulo) AppContext.getInstance().get("seleccion-Articulo");
            CargasDatos();

        } catch (IOException ex) {
            Logger.getLogger(FXML_FacturaciónController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Lanzar_FXML_FacturaPendiete() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Factura_Pendiente.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnGuardarPedido.getScene().getWindow());
            stage.showAndWait();
            CargarInfo_FacturaPendiente();

        } catch (IOException ex) {
            Logger.getLogger(FXML_FacturaciónController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Lanzar_FXML_Abonos() {
        try {
            ScenesManager.getInstance().LoadSceneAbonos();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de Abonos.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    private void ProcesoGenerarFactura() {
        try {
            wd = new WorkIndicatorDialog(btnCredito.getScene().getWindow(), "Imprimiendo...");

            wd.exec("123", inputParam -> {
                try {
                    PrinterJob pj = PrinterJob.getPrinterJob();
                    pj.setPrintable(new PrinterManagerFacturacion(), getPageFormat(pj));
                    pj.print();
                    return 1;
                } catch (PrinterException ex) {
                    LoggerManager.Logger().info(ex.toString());
                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.ERROR, "Ocurrio un error al imprimir la factura. El codigo de error es "
                                + "el siguiente: " + ex, ButtonType.OK).showAndWait();
                    });
                    return 2;
                }
            });

        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
            new Alert(Alert.AlertType.ERROR, "Ocurrio un error al generar la factura. El codigo de error es " + "el siguiente: " + ex, ButtonType.OK).showAndWait();
        }
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    public PageFormat getPageFormat(PrinterJob pj) {
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double headerHeight = 2.0;
        double middleHeight = 8.0;
        double footerHeight = 2.0;
        double width = convert_CM_To_PPI(10);
        double height = convert_CM_To_PPI(40);
        paper.setSize(width, height);
        paper.setImageableArea(
                0,
                10,
                width,
                height - convert_CM_To_PPI(1)
        );
        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);

        return pf;
    }

    private static double convert_CM_To_PPI(double cm) {
        return toPPI(cm * 0.393600787);
    }

    private static double toPPI(double inch) {
        return inch * 72d;
    }


    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++Variables de Clase++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    Cliente cliente;
    Articulo pArticulo;
    Factura pFactura;
    List<InventarioCompleto> ListArticulos;
    List<Factura> ListPedidos = new ArrayList<>();
    List<ArticuloXFactura> listArticuloXFacturas;
    double Total, Subtotal, Descuento, ImpuestoVenta;
    WorkIndicatorDialog wd;

}
