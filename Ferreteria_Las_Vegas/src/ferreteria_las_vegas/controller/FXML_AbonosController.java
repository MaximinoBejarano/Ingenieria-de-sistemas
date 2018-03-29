/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.AbonosJPAController;
import ferreteria_las_vegas.model.controller.CuentasXCobrarJPAController;
import ferreteria_las_vegas.model.controller.TipoPagoJPAController;
import ferreteria_las_vegas.model.entities.CuentaXCobrar;
import ferreteria_las_vegas.model.entities.Abono;
import ferreteria_las_vegas.model.entities.TipoPago;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.Message;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private ComboBox<?> cmbCliente;
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
    TipoPago tipPago;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    Date fecha = new Date();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        nAbono = new Abono();
        tipPago = new TipoPago();
        Cuenta = null;
        lblFechaAbono.setText(formatter.format(fecha.getTime()));
        fecha = new Date();

    }

    @FXML
    private void btnSalir_Click(ActionEvent event) {
        ScenesManager.getInstance().LoadSceneMenu();
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
                        nAbono = new Abono();
                        lblFechaAbono.setText(formatter.format(fecha.getTime()));
                        txtAbono.setText("");
                        btnEditarAbono.setDisable(true);
                        btnEliminarAbono.setDisable(true);
                    }
                }
            }
        } else {
            Message.getInstance().Warning("Advertencia:", "Por favor consulte la cuenta a abonar");
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
    void tblMouse_Click(MouseEvent event
    ) { // se carga en los componentes el item selecionado
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
        validarNumero(event);
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

                if (CuentasXCobrarJPAController.getInstance().Modificar_CuentaXCobrar_Abonos(Cuenta, nAbono) != null) {
                    Message.getInstance().Information("Información:", "Se han ingresado los datos de forma exitosa");
                    CargarInformacion(); //Actualiza la pantalla con los nuevos cambios
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
            nAbono.setAboMonto(Double.parseDouble(txtAbono.getText()));
            saldo -= nAbono.getAboMonto();
            Cuenta.setCueSaldo(saldo);
            if (CuentasXCobrarJPAController.getInstance().Modificar_CuentaXCobrar(Cuenta) != null
                    && AbonosJPAController.getInstance().ModificarAbono(nAbono) != null) {
                Message.getInstance().Information("Información:", "Se han modificado los datos de forma exitosa");
                CargarInformacion(); //Actualiza la pantalla con los nuevos cambios
            } else {
                Message.getInstance().Error("Error:", "No se han guardado los datos");
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
        tipPago = TipoPagoJPAController.getInstance().Consultar_TipoPagoCodigo("Contado");
        return tipPago;
    }

    //++++++++++++++++++++++++++++++++++++++  Area de procesos en GUI ++++++++++++++++++++++++++++++++++++++++++++/
    /*
       Extrae los datos de la vista, para crear un abono
     */
    public Abono Extraerdatos(Abono pAbono) {
        try {
            //no borrar Date.from(pickFecha.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())
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
                        //Cuenta.setCueEstado("I");
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

    public void CargarInformacion() {
        Limpiar_Vista();
        nAbono = new Abono();
        Cuenta = CuentasXCobrarJPAController.getInstance().Consultar_CuentaXCobrarCodigo(Cuenta.getCueCodigo());
        if (Cuenta.getCueEstado() != "I") {
            lblNumFactura.setText(Cuenta.getCueFactura().getFacCodigo().toString());
            lblSaldoFact.setText(String.valueOf(Cuenta.getCueSaldoFac()));
            lblSaldoTotal.setText(String.valueOf(Cuenta.getCueSaldo()));
            CargarTabla(Cuenta);
        }
    }

    public void CargarTabla(CuentaXCobrar Cuenta) {
        try {
            colAbono.setCellValueFactory((cellData -> new SimpleObjectProperty<Double>(cellData.getValue().getAboMonto())));
            colFecha.setCellValueFactory((cellData -> new SimpleObjectProperty<String>(formatter.format(cellData.getValue().getAboFecha()))));
            colNumAbono.setCellValueFactory((cellData -> new SimpleObjectProperty(cellData.getValue().getAboCodigo())));

            //  List<Abono> abonosList = Cuenta.getAbonoList();   
            //  abonosList = abonosList.stream().filter(x -> x.getAboEstado().equals("A")).collect(Collectors.toList());   
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
        lblNumFactura.setText("######");
        lblSaldoFact.setText("00000");
        lblSaldoTotal.setText("00000");
        lblFechaAbono.setText(formatter.format(fecha.getTime()));
        txtAbono.setText("");
        tblAbonos.getItems().clear();
        btnEditarAbono.setDisable(true);
        btnEliminarAbono.setDisable(true);

    }
    //++++++++++++++++++++++++++++++++++++++  Area de validaciones de componetes de la GUI ++++++++++++++++++++++++++++++++++++++++++++

    public void validarNumero(KeyEvent event) {
        String character = event.getCharacter();
        if (!checkNumerico(character)) {
            event.consume();
            Message.getInstance().Warning("Cuidado:", "Este campo solo acepta numeros");
        }

    }

    public boolean checkNumerico(String value) {
        String number = value.replaceAll("\\s+", "");
        for (int j = 0; j < number.length(); j++) {
            if (!(((int) number.charAt(j) >= 47 && (int) number.charAt(j) <= 57)) && !((int) number.charAt(j) == 8)) {
                return false;
            }
        }
        return true;
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
            CargarInformacion();

        } catch (Exception ex) {
            System.err.print(ex);
        }
    }
}
