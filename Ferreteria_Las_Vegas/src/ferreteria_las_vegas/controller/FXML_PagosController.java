/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.FacturaJPAController;
import ferreteria_las_vegas.model.controller.InventarioJpaController;
import ferreteria_las_vegas.model.controller.NotaCreditoJPAController;
import ferreteria_las_vegas.model.controller.ParametroJPAController;
import ferreteria_las_vegas.model.controller.TipoPagoJPAController;
import ferreteria_las_vegas.model.entities.Factura;
import ferreteria_las_vegas.model.entities.Pago;
import ferreteria_las_vegas.model.entities.ArticuloXFactura;
import ferreteria_las_vegas.model.entities.Inventario;
import ferreteria_las_vegas.model.entities.NotaCredito;
import ferreteria_las_vegas.model.entities.Parametro;

import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.GeneralUtils;
import ferreteria_las_vegas.utils.Message;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author wili
 */
public class FXML_PagosController implements Initializable {

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnPagar;

    @FXML
    private TitledPane tpanelEfectivo;

    @FXML
    private Label lbl_TotalEfectivo;

    @FXML
    private ComboBox<String> cmbMoneda;

    @FXML
    private TextField txtMontoEfectivo;

    @FXML
    private TitledPane tpanelTarjeta;

    @FXML
    private Label lblTotalTarjeta;

    @FXML
    private TextField txtMontoTarjeta;

    @FXML
    private TextField txtCodigoTarjeta;

    @FXML
    private TitledPane tpanelNotaCredito;

    @FXML
    private Label lblTotalNotaCredito;

    @FXML
    private TextField txtNumeroVale;

    @FXML
    private Label lblMonto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        InicializarVariables();
        pFactura = (Factura) AppContext.getInstance().get("Factura");
        pParametro = ParametroJPAController.getInstance().ConsultarParametro_Ferrteria("3-98736-8799");
        pMontoTotal = pFactura.getFacTotal();
        ListArticulosXFactura = (List<ArticuloXFactura>) AppContext.getInstance().get("ArticulosXFactura");
        CargarTotales();
        AppContext.getInstance().set("pago", false);
        cmbMoneda.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                txtMontoEfectivo.setText("");
                RecalcularTotalPagar();
            }
        });

    }

    @FXML
    private void btnSalir_Click(ActionEvent event) {
        Stage stageAct = (Stage) btnSalir.getScene().getWindow();
        stageAct.close();
    }

    @FXML
    private void btnPagar_Click(ActionEvent event) {
        if ((!txtMontoEfectivo.getText().isEmpty() || !txtMontoTarjeta.getText().isEmpty() || !lblMonto.getText().isEmpty()) && pMontoTotal == 0) {
            ProcesarRegistroFactura();
            
            Stage stageAct = (Stage) btnPagar.getScene().getWindow();
        stageAct.close();
        } else {
            Message.getInstance().Warning("Advertencia", "Es necesario cancelar el monto total de la compra");
        }
    }

    @FXML
    private void txtMontoEfectivo_KeyTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtMontoEfectivo.getText().length(), 8, event);

        if (Character.isDigit(event.getCharacter().charAt(0)) || event.getCharacter().charAt(0) == 8) {

            txtMontoEfectivo.setText(txtMontoEfectivo.getText() + event.getCharacter().charAt(0));
            event.consume();
            txtMontoEfectivo.positionCaret(txtMontoEfectivo.getText().length());
            RecalcularTotalPagar();
        }

    }

    @FXML
    private void txtMontoTarjeta_KeyTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtMontoTarjeta.getText().length(), 8, event);

        if (Character.isDigit(event.getCharacter().charAt(0)) || event.getCharacter().charAt(0) == 8) {
            txtMontoTarjeta.setText(txtMontoTarjeta.getText() + event.getCharacter().charAt(0));
            event.consume();
            txtMontoTarjeta.positionCaret(txtMontoTarjeta.getText().length());
            RecalcularTotalPagar();
        }
    }

    @FXML
    private void txtCodigoTarjeta_KeyTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtCodigoTarjeta.getText().length(), 4, event);
    }

    @FXML
    private void txtNumeroVale_KeyTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtNumeroVale.getText().length(), 8, event);
        if (event.getCharacter().charAt(0) == 8) {
            pNotaCredito = new NotaCredito();
            lblMonto.setText("");
        }
    }

    @FXML
    void txtMontoEfectivo_OnAction(ActionEvent event) {
   
    }

    @FXML
    void txtNumeroVale_OnAction(ActionEvent event) {
        ConsultarVale();
    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++Procesos fundamentales++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    /**
     * Proceso mediante el cual se realizan las validaciones de existencia de
     * los articulos de la factura y la elaboracion de los pagos.Ademas se
     * registra la factura en la base de datos y se realiza la disminución del
     * inventario.
     */
    public void ProcesarRegistroFactura() {
        if (pFactura != null && !ListArticulosXFactura.isEmpty()) {
            ProcesoPago();
            CalcularVuelto();
            ValidarExistencias();
            if (ListExistencias.isEmpty() || ListExistencias == null) {
                pFactura.setFactEstadoPago("I");
                if (FacturaJPAController.getInstance().AgregarFactura(pFactura, ListArticulosXFactura, listPagos) != null) {
                    RebajarInventario();
                    AppContext.getInstance().set("Vuelto", Vuelto);
                    Lanzar_FXMLVuelto();
                    AppContext.getInstance().set("pago", true);
                  
                } else {
                    Message.getInstance().Error("Error", "No se ha logrado efectuar el pago");
                }
            } else {
                String Articulos = "";
                for (ArticuloXFactura pArt : ListExistencias) {
                    Articulos = Articulos + "\n " + pArt.getArtArticulo().getArtNombre();
                }
                Message.getInstance().Error("Error", "Los articulos sin existencias son:" + Articulos);
            }
        }else{
            Message.getInstance().Information("Informació","La factura ya ha sido cancelada");
        }
    }

    //Se elabora la disminución en inventario de las cantidades vendidas en la factura.
    public void RebajarInventario() {
        Inventario pInventario = new Inventario();
        if (!ListArticulosXFactura.isEmpty()) {
            for (ArticuloXFactura pArtXFact : ListArticulosXFactura) {
                if (InventarioJpaController.getInstance().ConsultarInventarioCodigoProducto(pArtXFact.getArtArticulo().getArtCodigo()) != null) {
                    pInventario = InventarioJpaController.getInstance().ConsultarInventarioCodigoProducto(pArtXFact.getArtArticulo().getArtCodigo());
                    if (pInventario.getInvCantidad() >= pArtXFact.getArtCantidad()) {
                        pInventario.setInvCantidad(pInventario.getInvCantidad() - pArtXFact.getArtCantidad());
                        InventarioJpaController.getInstance().ModificarInventario(pInventario);
                    } else {
                        Message.getInstance().Information("Información", "Cantidad insuficiente del Articulo= " + pArtXFact.getArtArticulo().getArtNombre());
                    }
                }
            }
        }
    }

    /*Procesdo mediante el cual se extrae los montos pagados en la factura y el tipo de pago
      y se procede a registrar los pagos
     */
    public void ProcesoPago() {
        if (!txtMontoEfectivo.getText().isEmpty()) {
            if (cmbMoneda.getSelectionModel().getSelectedItem().equalsIgnoreCase("Dolares")) {
                ProcesarPagos(Double.valueOf(txtMontoEfectivo.getText()) * pParametro.getParTipoCambio(), "Efectivo");
            } else {
                ProcesarPagos(Double.valueOf(txtMontoEfectivo.getText()), "Efectivo");
            }
        }
        if (!lblMonto.getText().isEmpty()) {
            ProcesarPagos(Double.valueOf(lblMonto.getText()), "NotaCredito");
        }
        if (!txtMontoTarjeta.getText().isEmpty()) {
            ProcesarPagos(Double.valueOf(txtMontoTarjeta.getText()), "Tarjeta");
        }
    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++Metodos importantes que no son procesos+++++++++++++++++++++++++++++++++++++++++*/
    //Se agregan los pagos con montos mayores a 0 a la lista de pagos
    public void ProcesarPagos(double pMonto, String TipoPago) {
        pPago = new Pago();
        if (pMonto > 0) {
            pPago.setPagEstado("A");
            pPago.setPagMonto(pMonto);
            pPago.setPagFactura(pFactura);
            pPago.setPagCodigo(Integer.SIZE);
            pPago.setPagTipoMoneda(" ");
            if (TipoPago.equals("Efectivo")) {
                if (cmbMoneda.getSelectionModel().getSelectedItem().equalsIgnoreCase("Dolares")) {
                    pPago.setPagTipoMoneda("Dolares");
                } else {
                    pPago.setPagTipoMoneda("Colones");
                }
                pPago.setPagTipoCambio(pParametro.getParTipoCambio());
                pPago.setPagTipoPago(TipoPagoJPAController.getInstance().Consultar_TipoPagoNombre("Contado"));
            }
            if (TipoPago.equals("Tarjeta")) {
                pPago.setPagTipoPago(TipoPagoJPAController.getInstance().Consultar_TipoPagoNombre("Credito"));
            }
            if (TipoPago.equals("NotaCredito")) {
                pPago.setPagNotaCredito(pNotaCredito);
            }
            listPagos.add(pPago);
        }
    }

    /*
      Se realiza la consulta de una nota de credito para verificar su existencia
      y cargar los montos en la vista
     */
    public void ConsultarVale() {
        pNotaCredito = new NotaCredito();
        if (!txtNumeroVale.getText().isEmpty()) {
            pNotaCredito = NotaCreditoJPAController.getInstance().ConsultarNotaCredito(Integer.parseInt(txtNumeroVale.getText()));
            if (pNotaCredito != null && pNotaCredito.getNotEstado().equalsIgnoreCase("A")) {
                if (pNotaCredito.getNotFactura().getFacCliente() == pFactura.getFacCliente()) {
                    lblMonto.setText(String.valueOf(pNotaCredito.getNotMonto()));
                    RecalcularTotalPagar();

                } else {
                    txtNumeroVale.setText("");
                    Message.getInstance().Warning("Advertencia", "El vale no pertenece a este Cliente");
                }
            } else {
                txtNumeroVale.setText("");
                Message.getInstance().Error("Error", "El vale es invalido o no existe");
            }
        } else {
            lblMonto.setText("");
        }
    }

    /*
     Se realiza el calculo del vuelto que debe entregar el vendedor
     */
    public void CalcularVuelto() {
        double subTotal = 0;
        if (!listPagos.isEmpty()) {
            for (Pago p : listPagos) {

                subTotal += p.getPagMonto();

            }
            if (subTotal > pFactura.getFacTotal()) {
                Vuelto = subTotal - pFactura.getFacTotal();
            } else {
                Vuelto = 0;
            }
        }
    }

    //Se comprueba que exista una cantidad correcta del articulo en inventario
    public void ValidarExistencias() {
        Inventario pInventario = new Inventario();
        ListExistencias= new ArrayList<>();
        if (!ListArticulosXFactura.isEmpty()) {
            for (ArticuloXFactura pArtXFact : ListArticulosXFactura) {
                if (InventarioJpaController.getInstance().ConsultarInventarioCodigoProducto(pArtXFact.getArtArticulo().getArtCodigo()) != null) {
                    pInventario = InventarioJpaController.getInstance().ConsultarInventarioCodigoProducto(pArtXFact.getArtArticulo().getArtCodigo());
                    if (pInventario.getInvCantidad() < pArtXFact.getArtCantidad()) {
                        ListExistencias.add(pArtXFact);
                    }
                }
            }
        }
    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++Otros metodos+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    // Recalcula el monto total a pagar 
    public void RecalcularTotalPagar() {
        pMontoTotal = pFactura.getFacTotal();
        
        if (!txtMontoEfectivo.getText().isEmpty() && Double.valueOf(txtMontoEfectivo.getText()) > 0) {
            if (cmbMoneda.getSelectionModel().getSelectedItem().equalsIgnoreCase("Colones")) {
                CalcularTotal(Double.valueOf(txtMontoEfectivo.getText()));
            } else {
                if (cmbMoneda.getSelectionModel().getSelectedItem().equalsIgnoreCase("Dolares")) {
                    double SubMonto = Double.valueOf(txtMontoEfectivo.getText()) * pParametro.getParTipoCambio();
                    CalcularTotal(SubMonto);
                }
            }
        }
        

        if (!txtMontoTarjeta.getText().isEmpty() && pMontoTotal > 0) {
            CalcularTotal(Double.valueOf(txtMontoTarjeta.getText()));
        }
        if (!lblMonto.getText().isEmpty() && pMontoTotal > 0) {
            CalcularTotal(Double.valueOf(lblMonto.getText()));
        }
        CargarTotales();
    }

    public void CalcularTotal(double pPago) {
        if (pPago < pMontoTotal) {
            pMontoTotal -= pPago;
        } else {
            pMontoTotal = 0;
        }

    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++Metodos GUI-++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    public void CargarTotales() {
        lblTotalNotaCredito.setText(String.format("%.2f",pMontoTotal));
        lbl_TotalEfectivo.setText(String.format("%.2f",pMontoTotal));
        lblTotalTarjeta.setText(String.format("%.2f",pMontoTotal));
    }

    //Limpia los campos de la vista
    public void limpiarVista() {
        lblMonto.setText("");
        lblTotalNotaCredito.setText("");
        lblTotalTarjeta.setText("");
        lbl_TotalEfectivo.setText("");
        txtCodigoTarjeta.setText("");
        txtMontoEfectivo.setText("");
        txtNumeroVale.setText("");
        txtMontoTarjeta.setText("");
    }

    public void InicializarVariables() {

        listPagos = new ArrayList<>();
        ListArticulosXFactura = new ArrayList<>();
        ListExistencias = new ArrayList<>();
        pFactura = null;
        pParametro = null;
        pMontoTotal = 0;
        listMoneda = FXCollections.observableArrayList();
        listMoneda.addAll("Colones", "Dolares");
        cmbMoneda.setItems(listMoneda);
        cmbMoneda.getSelectionModel().select("Colones");
        
        limpiarVista();

    }

    /*++++++++++++++++++++++++++++++++++++++++++++++++++++++Metodos Lanzadores+++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    public void Lanzar_FXMLVuelto() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Vuelto.fxml"));

            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnPagar.getScene().getWindow());
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(FXML_FacturaciónController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++Variables de Clase++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    Factura pFactura;
    Parametro pParametro;
    Pago pPago;
    NotaCredito pNotaCredito;
    double pMontoTotal;
    double Vuelto;
    ObservableList<String> listMoneda;
    List<Pago> listPagos;
    List<ArticuloXFactura> ListArticulosXFactura;
    List<ArticuloXFactura> ListExistencias;
}
