/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.AbonosJPAController;
import ferreteria_las_vegas.model.controller.CuentasXCobrarJPAController;
import ferreteria_las_vegas.model.controller.FacturaJPAController;
import ferreteria_las_vegas.model.controller.TipoPagoJPAController;
import ferreteria_las_vegas.model.entities.CuentaXCobrar;
import ferreteria_las_vegas.model.entities.Abono;
import ferreteria_las_vegas.model.entities.Factura;
import ferreteria_las_vegas.model.entities.Persona;
import ferreteria_las_vegas.model.entities.TipoPago;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.GeneralUtils;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import ferreteria_las_vegas.utils.PrintManagerProforma;
import ferreteria_las_vegas.utils.PrinterManagerAbono;
import ferreteria_las_vegas.utils.SearchComboBox;
import ferreteria_las_vegas.utils.WorkIndicatorDialog;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Maximino
 */
public class FXML_AbonosController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnAgregarAbono;
    @FXML
    private Button btnEditarAbono;
    @FXML
    private Button btnEliminarAbono;
    @FXML
    private Button btnLimpiar;
    @FXML
    private GridPane SearchBoxGrid_Cliente;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Label lblNumFactura;
    @FXML
    private Label lblSaldoFact;
    @FXML
    private Label lblSaldoTotal;
    @FXML
    private Label lblFechaAbono;
//    @FXML
//    private Label lblCédula_Cliente;
    @FXML
    private TextField txtAbono;

    @FXML
    private TableView<Abono> tblAbonos;

    @FXML
    private TableColumn<Abono, Integer> colNumAbono;

    @FXML
    private TableColumn<Abono, String> colFecha;

    @FXML
    private TableColumn<Abono, Double> colAbono;
    /**
     * Area de variables Globales
     */
    Abono nAbono = new Abono();//Nuevo abono
    CuentaXCobrar Cuenta;
    Factura pFactura;
    TipoPago tipPago;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    Date fecha = new Date();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nAbono = null;
        tipPago = new TipoPago();
        Cuenta = null;
        pFactura = null;
        lblFechaAbono.setText(formatter.format(fecha.getTime()));
        fecha = new Date();

        ComboBoxCliente();

    }

    @FXML
    void btnLimpiarCampos_Click(ActionEvent event) {
        Inicializar_Componentes();
        Cuenta = null;
        Limpiar_Vista();
    }

    @FXML
    private void btnSalir_Click(ActionEvent event) {
        try {
            ScenesManager.getInstance().LoadSceneMenu();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo volver a la pantalla de menú.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    private void btnAgregarAbono_Click(ActionEvent event) {
        if (Cuenta != null) {
            if (txtAbono.getText().isEmpty()) {
                Message.getInstance().Warning("Advertencia:", "Es necesario completar los campos requeridos");
            } else {
                if (nAbono.getAboCodigo() == null) {
                    AgregarAbono();
                } else {
                    if (AbonosJPAController.getInstance().ConsultarAbono_Codigo(nAbono.getAboCodigo()) != null) {
                        Message.getInstance().Information("Información:", "Abono existente, ingrese uno nuevo");
                        Inicializar_Componentes();
                    }
                }
            }
        } else {
            Inicializar_Componentes();
            Message.getInstance().Warning("Advertencia:", "Por favor consulte un cliente o cuenta por cobrar");
        }
    }

    @FXML
    private void btnEditarAbono_Click(ActionEvent event
    ) {
        if (txtAbono.getText().isEmpty()) {
            Message.getInstance().Warning("Advertencia:", "Es necesario completar los campos requeridos");
        } else {
            if (tblAbonos.getSelectionModel().getSelectedItem() != null && nAbono != null) {
                if (formatter.format(nAbono.getAboFecha()).equals(formatter.format(fecha.getTime()))) {
                    ModificarAbono();
                } else {
                    Message.getInstance().Warning("Advertencia:", "Solo modificar abonos con la fecha: " + formatter.format(fecha.getTime()));
                }
            } else {
                Message.getInstance().Warning("Advertencia:", "Debe selecionar una fila de la tabla");
            }
        }
    }

    @FXML
    private void btnEliminarAbono_Click(ActionEvent event
    ) {
        if (txtAbono.getText().isEmpty()) {
            Message.getInstance().Warning("Advertencia:", "Es necesario completar los campos requeridos");
        } else {
            if (tblAbonos.getSelectionModel().getSelectedItem() != null && nAbono != null) {
                if (formatter.format(nAbono.getAboFecha()).equals(formatter.format(fecha.getTime()))) {
                    EliminarAbono();
                } else {
                    Message.getInstance().Warning("Advertencia:", "Solo eliminar abonos con la fecha: " + formatter.format(fecha.getTime()));
                }
            } else {
                Message.getInstance().Warning("Advertencia:", "Debe selecionar una fila de la tabla");
            }
        }
    }

    @FXML
    private void cmbCliente_Click(ActionEvent event
    ) {
    }

    @FXML
    private void btnFiltrar_Click(ActionEvent event
    ) {
        LanzarBusqudad_CuentaxCobrar();
    }

    @FXML
    void tblMouse_Click(MouseEvent event) { // se carga en los componentes el item selecionado
        if (tblAbonos.getSelectionModel().getSelectedItem() != null) {
            nAbono = (Abono) tblAbonos.getSelectionModel().getSelectedItem();
            lblFechaAbono.setText(formatter.format(nAbono.getAboFecha()));
            txtAbono.setText(String.valueOf(nAbono.getAboMonto()));
            btnEditarAbono.setDisable(false);
            btnEliminarAbono.setDisable(false);
        }
    }

    @FXML
    void tblKeyPressed(KeyEvent event) {

    }

    @FXML
    void KeyTyped_txtAbono(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtAbono.getText().length(), 8, event);
    }

    //*****************************************************++ Area de Procesos ++****************************************************************+
    /**
     * Se agreaga un nuevo abono a la base de datos
     */
    public void AgregarAbono() {
        try {
            nAbono = new Abono();
            nAbono = Extraerdatos(nAbono);
            if (nAbono != null) {
                Cuenta = CuentasXCobrarJPAController.getInstance().Modificar_CuentaXCobrar_Abonos(Cuenta, nAbono);
                if (Cuenta != null) {
                    if (Message.getInstance().Confirmation("Información:", "Se han ingresado los datos de forma exitosa.\nDesea imprimir el abono")) {
                        nAbono = VerificarAbono(Cuenta, nAbono);
                        AppContext.getInstance().set("Factura-Abono", Cuenta.getCueFactura());
                        AppContext.getInstance().set("Registro-Abono", nAbono);
                        wd = new WorkIndicatorDialog(btnAgregarAbono.getScene().getWindow(), "Imprimiendo...");
                        ProcesoGenerarAbono();
                        CargarInformacion(); //Actualiza la pantalla con los nuevos cambios
                    } else {
                        CargarInformacion(); //Actualiza la pantalla con los nuevos cambios
                    }
                } else {
                    Message.getInstance().Error("Error:", "No se han guardado los datos");
                }

            }

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public void ModificarAbono() {
        double saldo;
        try {
           
            saldo = Cuenta.getCueSaldo() + nAbono.getAboMonto();
            if (Double.parseDouble(txtAbono.getText()) > 0) {
                nAbono.setAboMonto(Double.parseDouble(txtAbono.getText()));
                saldo -= nAbono.getAboMonto();
                Cuenta.setCueSaldo(saldo);
                Cuenta = CuentasXCobrarJPAController.getInstance().Modificar_CuentaXCobrar(Cuenta);
                nAbono = AbonosJPAController.getInstance().ModificarAbono(nAbono);
                if (nAbono != null && Cuenta != null) {
                    if (Message.getInstance().Confirmation("Información:", "Se han modificado los datos de forma exitosa.\nDesea imprimir el abono")) {
                        AppContext.getInstance().set("Factura-Abono", Cuenta.getCueFactura());
                        AppContext.getInstance().set("Registro-Abono", nAbono);
                        wd = new WorkIndicatorDialog(btnEditarAbono.getScene().getWindow(), "Imprimiendo...");
                        ProcesoGenerarAbono();
                        CargarInformacion(); //Actualiza la pantalla con los nuevos cambios
                    } else {
                        CargarInformacion(); //Actualiza la pantalla con los nuevos cambios
                    }
                } else {
                    Message.getInstance().Error("Error:", "No se han guardado los datos");
                }
            } else {
                Message.getInstance().Warning("Advertencia:", "Por favor solo ingrese digitos mayores a cero");
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }

    }

    public void EliminarAbono() {

        try {
            Cuenta.setCueSaldo(Cuenta.getCueSaldo() + nAbono.getAboMonto());
            nAbono.setAboEstado("I");
            if (CuentasXCobrarJPAController.getInstance().Modificar_CuentaXCobrar(Cuenta) != null
                    && AbonosJPAController.getInstance().ModificarAbono(nAbono) != null) {
                Message.getInstance().Information("Información:", "Se han eliminados los datos de forma exitosa");
                CargarInformacion(); //Actualiza la pantalla con los nuevos cambios
            } else {
                Message.getInstance().Error("Error:", "No se han guardado los datos");
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }

    }

    public TipoPago CargarTipoPago() {
        tipPago = TipoPagoJPAController.getInstance().Consultar_TipoPagoNombre("Contado");
        return tipPago;
    }

    //++++++++++++++++++++++++++++++++++++++  Area de procesos en GUI ++++++++++++++++++++++++++++++++++++++++++++/
    /*
       Extrae los datos de la vista, para crear un abono
     */
    public Abono Extraerdatos(Abono pAbono) {
        try {

            pAbono.setAboFecha(new java.sql.Date(new java.util.Date().getTime()));
            pAbono.setAboMonto(Double.parseDouble(txtAbono.getText()));
            pAbono.setAboEstado("A");
            pAbono.setAboTipoAbono("C");
            pAbono.setAboTipoPago(CargarTipoPago());

            Cuenta = CuentasXCobrarJPAController.getInstance().Consultar_CuentaXCobrarCodigo(Cuenta.getCueCodigo());
            if (pAbono.getAboMonto() > 0) {
                if ((Cuenta.getCueSaldo() - pAbono.getAboMonto()) > 0) {
                    Cuenta.setCueSaldo(Cuenta.getCueSaldo() - pAbono.getAboMonto());
                } else {
                    if ((Cuenta.getCueSaldo() - pAbono.getAboMonto()) == 0) {
                        Cuenta.setCueSaldo(Cuenta.getCueSaldo() - pAbono.getAboMonto());
                        Cuenta.setCueEstado("I");

                        //La factura pasa de pendiente de cobro a cancelada
                        //se consulta la  factura correspondiente a la cuenta por cobrar
                        if (FacturaJPAController.getInstance().ConsultarFactura_Codigo(Cuenta.getCueFactura().getFacCodigo()) != null) {
                            pFactura = FacturaJPAController.getInstance().ConsultarFactura_Codigo(Cuenta.getCueFactura().getFacCodigo());
                            pFactura.setFactEstadoPago("I");
                            FacturaJPAController.getInstance().ModificarFactura(pFactura);
                        }
                        Message.getInstance().Information("Información:", "Se ha cancelado el saldo total de la cuenta por cobrar");
                    } else {
                        Message.getInstance().Warning("Advertencia:", "El monto a abonar es mayor que el Saldo a pagar");
                        return null;
                    }
                }

            } else {
                Message.getInstance().Warning("Advertencia:", "Por favor solo ingrese digitos mayores a cero");
                return null;
            }
            return pAbono;
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    public Abono VerificarAbono(CuentaXCobrar pCuenta, Abono pAbono) {
        for (Abono Abo : pCuenta.getAbonoList()) {
            if (Abo.getAboEstado().equals(pAbono.getAboEstado()) && Abo.getAboFecha().equals(pAbono.getAboFecha())
                    && Abo.getAboMonto() == pAbono.getAboMonto() && Abo.getAboTipoAbono() == pAbono.getAboTipoAbono()
                    && Abo.getAboTipoPago().equals(pAbono.getAboTipoPago())) {
                return Abo;
            }
        }
        return null;
    }

    public void CargarInformacion() {
        Limpiar_Vista();
        nAbono = new Abono();
        Cuenta = CuentasXCobrarJPAController.getInstance().Consultar_CuentaXCobrarCodigo(Cuenta.getCueCodigo());
        if (Cuenta.getCueEstado() != "I") {
            Persona pPersona = Cuenta.getCueCliente().getPersona();
            boxCliente_CxC.setValue(Cuenta);
            lblNumFactura.setText(String.valueOf(Cuenta.getCueFactura().getFacCodigo()));
            lblSaldoFact.setText(String.format("%.2f", Cuenta.getCueSaldoFac()));
            lblSaldoTotal.setText(String.format("%.2f", Cuenta.getCueSaldo()));
            CargarTabla(Cuenta);
        }
    }

    public void CargarTabla(CuentaXCobrar Cuenta) {
        try {
            colAbono.setCellValueFactory((cellData -> new SimpleObjectProperty<Double>(cellData.getValue().getAboMonto())));
            colFecha.setCellValueFactory((cellData -> new SimpleObjectProperty<String>(formatter.format(cellData.getValue().getAboFecha()))));
            colNumAbono.setCellValueFactory((cellData -> new SimpleObjectProperty(cellData.getValue().getAboCodigo())));

            List<Abono> abonosList = new ArrayList<>();
            for (Abono pAbono : Cuenta.getAbonoList()) {
                if (pAbono.getAboEstado().equals("A")) {
                    abonosList.add(pAbono);
                }
            }
            ObservableList<Abono> ListAbonos = FXCollections.observableArrayList(abonosList);
            tblAbonos.setItems(ListAbonos);
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public void Limpiar_Vista() {
        lblNumFactura.setText("#####");
        lblSaldoFact.setText("00000");
        lblSaldoTotal.setText("00000");
        lblFechaAbono.setText(formatter.format(fecha.getTime()));
        txtAbono.setText("");
        tblAbonos.getItems().clear();
        btnEditarAbono.setDisable(true);
        btnEliminarAbono.setDisable(true);

    }

    public void Inicializar_Componentes() {
        nAbono = new Abono();
        lblFechaAbono.setText(formatter.format(fecha.getTime()));
        txtAbono.setText("");
        btnEditarAbono.setDisable(true);
        btnEliminarAbono.setDisable(true);
        tblAbonos.getSelectionModel().clearSelection();
    }

    //++++++++++++++++++++++++++++++++++++++  Area de Otros Procesos ++++++++++++++++++++++++++++++++++++++++++++/
    public void LanzarBusqudad_CuentaxCobrar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_CuentaXCobrar.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initOwner(btnFiltrar.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
            Cuenta = (CuentaXCobrar) AppContext.getInstance().get("seleccion-Cuenta");
            if (Cuenta != null) {
                CargarInformacion();
            }

        } catch (Exception ex) {
            System.err.print(ex);
        }
    }

    private void CargarCliente(SearchComboBox<CuentaXCobrar> box) {
        try {
            List<CuentaXCobrar> listCuentaXCobrar = new CuentasXCobrarJPAController().ConsultarCuentasXCobrar().stream().filter(e -> e.getCueEstado().equals("A")).collect(Collectors.toList());

            ObservableList<CuentaXCobrar> OClienteList = FXCollections.observableArrayList(listCuentaXCobrar);

            box.setItems(OClienteList);
        } catch (Exception ex) {
            Message.getInstance().Error("Error:", "Ocurrió un error al cargar los clientes. Codigo de error: " + ex);

        }
    }

    private void ComboBoxCliente() {

        try {
            boxCliente_CxC = new SearchComboBox<>();
            boxCliente_CxC.setMinHeight(33);
            boxCliente_CxC.setMinWidth(200);
            boxCliente_CxC.getSelectionModel().select(0);
            boxCliente_CxC.setPromptText("Selecionar Cliente");
            boxCliente_CxC.setFilter((CuentaXCobrar t, String u) -> (t.getCueCliente().getPersona().getPerCedula()).toUpperCase().contains(u.toUpperCase()));
            CargarCliente(boxCliente_CxC);
            boxCliente_CxC.setOnAction((event) -> {
                if (boxCliente_CxC.getSelectionModel().getSelectedItem() != null) {
                    Cuenta = boxCliente_CxC.getSelectionModel().getSelectedItem();
                    CargarInformacion();
                } else {
                    Cuenta = null;
                }
            });
            SearchBoxGrid_Cliente.add(boxCliente_CxC, 0, 0);

        } catch (Exception ex) {

            Message.getInstance().Error("Error:", "Ocurrió un error al cargar los clientes. Codigo de error: " + ex);
        }

    }

    private void ProcesoGenerarAbono() {
        try {

            wd.exec("123", inputParam -> {
                try {
                    PrinterJob pj = PrinterJob.getPrinterJob();
                    pj.setPrintable(new PrinterManagerAbono(), getPageFormat(pj));
                    pj.print();
                    return 1;
                } catch (PrinterException ex) {
                    LoggerManager.Logger().info(ex.toString());
                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.ERROR, "Ocurrio un error al imprimir el abono. El codigo de error es "
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
    /*Variables de Clase------------------------------------------------------*/
    private SearchComboBox<CuentaXCobrar> boxCliente_CxC;
    WorkIndicatorDialog wd;

}
