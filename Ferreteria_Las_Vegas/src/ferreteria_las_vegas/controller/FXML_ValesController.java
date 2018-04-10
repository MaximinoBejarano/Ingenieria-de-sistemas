/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.NotaCreditoJPAController;
import ferreteria_las_vegas.model.entities.ArticuloXFactura;
import ferreteria_las_vegas.model.entities.Factura;
import ferreteria_las_vegas.model.entities.NotaCredito;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.GeneralUtils;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import ferreteria_las_vegas.utils.PrinterManagerFacturacion;
import ferreteria_las_vegas.utils.PrinterManagerVales;
import ferreteria_las_vegas.utils.WorkIndicatorDialog;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_ValesController implements Initializable {

    @FXML
    private Tab tabNuevo;
    @FXML
    private Tab tabEliminar;
    @FXML
    private Button btnAgregarVales;
    @FXML
    private Button btnBuscarVales;
    @FXML
    private Button btnEliminarVales;
    @FXML
    private Button btnLimpiarPantalla;
    @FXML
    private Label lblFecha;
    @FXML
    private TextArea txtJustificacionCreacion;
    @FXML
    private Label lblVales;
    @FXML
    private Label lblCliente;
    @FXML
    private Label lblMontoTotal;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblTipoFactura;
    @FXML
    private Label lblCodigoFactura;
    @FXML
    private TableView<ArticuloXFactura> tblListaArticulos;
    @FXML
    private TableColumn<ArticuloXFactura, String> tcCodigo;
    @FXML
    private TableColumn<ArticuloXFactura, String> tcNombre;
    @FXML
    private TableColumn<ArticuloXFactura, String> tcDescripcion;
    @FXML
    private TableColumn<ArticuloXFactura, String> tcCantidad;
    @FXML
    private TableColumn<ArticuloXFactura, String> tcPrecio;
    @FXML
    private TextArea txaJustuficacionVales;
    @FXML
    private Label lblFechaEmisionVale;
    @FXML
    private Label lblMontoVale;
    @FXML
    private Label lblNumFacVales;
    @FXML
    private TextField txtNumVale;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void SalirClick(ActionEvent event) {
        try {
            ScenesManager.getInstance().LoadSceneMenu();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo volver a la pantalla de menú.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    private void btnAgregarValesCllick(ActionEvent event) {

        try {
            Factura pFactura = (Factura) AppContext.getInstance().get("seleccion-FacCliente");
            if(pFactura!=null){
            if (pFactura.getFactTipoFact().equals("E")) {
                NotaCredito pNotaCredito = new NotaCredito();
                pNotaCredito.setNotCodigo(Integer.SIZE);
                pNotaCredito.setNotFactura(pFactura);

                pNotaCredito.setNotFecha(new Date());
                pNotaCredito.setNotMonto(pFactura.getFacTotal());
                pNotaCredito.setNotJustificacion(txtJustificacionCreacion.getText());
                pNotaCredito.setNotEstado("A");
                NotaCredito pCredito = new NotaCreditoJPAController().ConsultarNotaCreditoXFactura(pFactura.getFacCodigo());
                if (pCredito == null) {
                    AppContext.getInstance().set("seleccion-vales", new NotaCreditoJPAController().InsertarNotaCredito(pNotaCredito));
                    ProcesoGenerarFactura();
                    Message.getInstance().Information("Éxito", "Se creo el vale con éxito");
                    LimpiarInterface();
                } else {
                    Message.getInstance().Error("Error", "Ya se encuentra una nota de crédito registrada con este código de factura");
                }
            } else {
                Message.getInstance().Error("Error", "Éste proceso solo es permitido para facturas de Cóntado");
            }
            }else{
                Message.getInstance().Error("Error", "Para poder realizar este proceso se debe cargar una Factura.");
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error al intentar cargar los datos de la factura. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }

    }

    @FXML
    private void btnBuscarValesClick(ActionEvent event) {
        if (tabNuevo.isSelected()) {
            LanzarFacturas();

            try {
                Factura pFactura = (Factura) AppContext.getInstance().get("seleccion-FacCliente");
                if (pFactura != null) {
                    RecargarTblListaAriticulos();
                    lblCodigoFactura.setText(pFactura.getFacCodigo().toString());
                    lblCliente.setText(pFactura.getFacCliente().getPersona().getPerNombre() + " "
                            + pFactura.getFacCliente().getPersona().getPerPApellido() + " "
                            + pFactura.getFacCliente().getPersona().getPerSApellido());
                    lblFecha.setText(formateador.format(pFactura.getFacFecha()));
                    lblUsuario.setText(pFactura.getFacEmpleado());
                    lblMontoTotal.setText(String.format("%.2f", pFactura.getFacTotal()));
                    lblTipoFactura.setText(pFactura.getFactTipoFact().replace("E", "Cóntado").replace("K", "Crédito"));

                }
            } catch (Exception ex) {
                Message.getInstance().Error("Error", "Ocurrió un error al intentar cargar los datos de la factura. "
                        + "El codigo de error es: " + ex.toString());
                LoggerManager.Logger().info(ex.toString());
            }
        } else {
            if (!txtNumVale.getText().isEmpty()) {
                BuscarVales();
            } else {
                Message.getInstance().Error("Error", "Se necesita Ingresar un número de vale para realizar la busqueda");
            }

        }
    }

    @FXML
    private void btnEliminarValesClick(ActionEvent event) {
        NotaCredito pCredito=(NotaCredito)AppContext.getInstance().get("seleccion-vales");
        if(pCredito!=null){
            pCredito.setNotEstado("I");
            new NotaCreditoJPAController().ModificarNotaCredito(pCredito);
            Message.getInstance().Information("Información", "Se logro eliminar con exito el vale.");
            LimpiarInterface();
        }else{
         Message.getInstance().Error("Error", "Se necesita buscar un vale.");
        }
    }

    @FXML
    private void btnLimpiarCampos(ActionEvent event) {
        LimpiarInterface();
    }

    @FXML
    private void onActiontxtNumVale(ActionEvent event) {
        BuscarVales();
    }

    public void BuscarVales() {
        try {
            NotaCredito pCredito = new NotaCreditoJPAController().ConsultarNotaCredito(Integer.valueOf(txtNumVale.getText()));
            txtNumVale.setText(String.format("%08d", Integer.valueOf(txtNumVale.getText())));
            
            if (pCredito != null && pCredito.getNotEstado().equals("A")) {
                lblVales.setText("Sin Utilizar");
                lblNumFacVales.setText(pCredito.getNotFactura().getFacCodigo().toString());
                lblMontoVale.setText(String.format("%.2f", pCredito.getNotMonto()));
                lblFechaEmisionVale.setText(formateador.format(pCredito.getNotFecha()));
                txaJustuficacionVales.setText(pCredito.getNotJustificacion());
                AppContext.getInstance().set("seleccion-vales", pCredito);
            } else {
                Message.getInstance().Error("Error", "No se encontro el número de vale o el vale ya fue cobrado.");
            }
        } catch (NumberFormatException ex) {
            Message.getInstance().Error("Error", "Ocurrio un error al consultar este vale. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    private void txtNumValeTyped(KeyEvent event) {
         GeneralUtils.getInstance().ValidarCampos(true, txtNumVale.getText().length(), 8, event);
    }

    public void LanzarFacturas() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Factura_Cliente.fxml"));
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnBuscarVales.getScene().getWindow());
            stage.showAndWait();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error al intentar abrir la pantalla de Busqueda de facturas. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    public void RecargarTblListaAriticulos() {
        try {
            tblListaArticulos.getItems().clear();

            tcCodigo.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getArtArticulo().getArtCodigo().toString()));
            tcNombre.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getArtArticulo().getArtNombre()));
            tcDescripcion.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getArtArticulo().getArtDescripcion()));
            tcCantidad.setCellValueFactory((cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getArtCantidad())));
            tcPrecio.setCellValueFactory((cellData) -> new SimpleStringProperty(String.format("%.2f", cellData.getValue().getArtPrecio())));

            tblListaArticulos.setItems(FXCollections.observableArrayList(((Factura) AppContext.getInstance().get("seleccion-FacCliente")).getArticuloXFacturaList()));

        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo recargar la tabla. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    public void LimpiarInterface() {

        lblCodigoFactura.setText("");
        lblCliente.setText("");
        lblFecha.setText("");
        lblUsuario.setText("");
        lblMontoTotal.setText("");
        lblTipoFactura.setText("");
        tblListaArticulos.getItems().clear();
        AppContext.getInstance().set("seleccion-FacCliente", null);
        AppContext.getInstance().set("seleccion-vales", null);
        txtNumVale.setText("");
        lblVales.setText("");
        lblNumFacVales.setText("");
        lblMontoVale.setText("");
        lblFechaEmisionVale.setText("");
        txaJustuficacionVales.setText("");
    }

    private void ProcesoGenerarFactura() {
        try {
            wd = new WorkIndicatorDialog(btnAgregarVales.getScene().getWindow(), "Imprimiendo...");

            wd.exec("123", inputParam -> {
                try {
                    PrinterJob pj = PrinterJob.getPrinterJob();
                    pj.setPrintable(new PrinterManagerVales(), getPageFormat(pj));
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

    /*--------------------------------------------------------------------------------------------------------------*/
    WorkIndicatorDialog wd;

    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yy");

}
