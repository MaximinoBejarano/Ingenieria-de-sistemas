/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.CuentasXCobrarJPAController;
import ferreteria_las_vegas.model.controller.FacturaJPAController;
import ferreteria_las_vegas.model.controller.InventarioJpaController;
import ferreteria_las_vegas.model.entities.ArticuloXFactura;
import ferreteria_las_vegas.model.entities.CuentaXCobrar;
import ferreteria_las_vegas.model.entities.Factura;
import ferreteria_las_vegas.model.entities.Inventario;
import ferreteria_las_vegas.utils.AppContext;
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
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_AnulaciónController implements Initializable {

    @FXML
    private Button btnLimpiarPantalla;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblCliente;
    @FXML
    private Label lblMontoTotal;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblTipoFactura;
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
    private Button btnAnularFactura;
    @FXML
    private Button btnBuscarFactura;
    @FXML
    private Button btnReimprimirFactura;
    @FXML
    private Label lblCodigoFactura;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LimpiarInterface();
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
    private void btnLimpiarCampos(ActionEvent event) {
        LimpiarInterface();
    }

    @FXML
    private void btnAnularFactura(ActionEvent event) {
        try {
            Factura pFactura = (Factura) AppContext.getInstance().get("seleccion-FacCliente");
            if (pFactura != null) {
                pFactura.setFacEstado("I");
                new FacturaJPAController().ModificarFactura(pFactura);
                for (ArticuloXFactura articuloXFacturaList : pFactura.getArticuloXFacturaList()) {
                    Inventario pInventario = new InventarioJpaController().ConsultarInventarioCodigoProducto(articuloXFacturaList.getArtArticulo().getArtCodigo());
                    pInventario.setInvCantidad(pInventario.getInvCantidad() + articuloXFacturaList.getArtCantidad());
                    new InventarioJpaController().ModificarInventario(pInventario);
                }
                if (pFactura.getFactTipoFact().equals("K")) {
                    CuentaXCobrar pCobrar = new CuentasXCobrarJPAController().Consultar_CuentaXCobrarCodigo(pFactura.getCuentaXCobrarList().get(0).getCueCodigo());
                    pCobrar.setCueEstado("I");
                    new CuentasXCobrarJPAController().Modificar_CuentaXCobrar(pCobrar);
                }
                Message.getInstance().Information("Proceso Exitoso", "El proceso de anulación se completo de manera satisfactoria");
                LimpiarInterface();
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error al intentar cargar los datos de la factura. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    private void btnBuscarFactura(ActionEvent event) {
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
                lblUsuario.setText("null");
                lblMontoTotal.setText(String.format("%.2f", pFactura.getFacTotal()));
                lblTipoFactura.setText(pFactura.getFactTipoFact().replace("E", "Cóntado").replace("K", "Crédito"));

            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error al intentar cargar los datos de la factura. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    private void btnReimprimirFactura(ActionEvent event) {
        AppContext.getInstance().set("seleccion-FacReimprecion", true);
        ProcesoGenerarFactura();

    }

    public void LanzarFacturas() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Factura_Cliente.fxml"));
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnBuscarFactura.getScene().getWindow());
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
    }

    private void ProcesoGenerarFactura() {
        try {
            wd = new WorkIndicatorDialog(btnReimprimirFactura.getScene().getWindow(), "Imprimiendo...");

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
            LimpiarInterface();
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
