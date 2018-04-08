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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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
        listPagos = new ArrayList<>();
        ListArticulosXFactura = new ArrayList<>();
        listMoneda = FXCollections.observableArrayList();
        listMoneda.addAll("Colones", "Dolares");

        pFactura = (Factura) AppContext.getInstance().get("Factura");
        pParametro = ParametroJPAController.getInstance().ConsultarParametro_Ferrteria("3-98736-8799");
        pMontoTotal = pFactura.getFacTotal();
        ListArticulosXFactura = (List<ArticuloXFactura>) AppContext.getInstance().get("ArticulosXFactura");

        cmbMoneda.setItems(listMoneda);
        cmbMoneda.getSelectionModel().select("Colones");
        cmbMoneda.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                txtMontoEfectivo.setText("");
                RecalcularTotalPagar();
            }
        });
        limpiarVista();
        CargarTotales();
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
        RecalcularTotalPagar();
    }

    @FXML
    void txtNumeroVale_OnAction(ActionEvent event) {
        ConsultarVale();
    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++Procesos fundamentales++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    public void ProcesarRegistroFactura() {
        ProcesoPago();
        CalcularVuelto();
        if (FacturaJPAController.getInstance().AgregarFactura(pFactura, ListArticulosXFactura, listPagos) != null) {
            RebajarInventario();
            //nuevaVentana
            Message.getInstance().Error("Error", "Vuelto: " + Vuelto);
        } else {
            Message.getInstance().Error("Error", "No se ha logrado efectuar el pago");
        }
    }

    public void RebajarInventario() {
        Inventario pInventario = new Inventario();
        if (!ListArticulosXFactura.isEmpty()) {
            for (ArticuloXFactura pArtXFact : ListArticulosXFactura) {
                if (InventarioJpaController.getInstance().ConsultarInventarioCodigoProducto(pArtXFact.getArtArticulo().getArtCodigo()) != null) {
                    pInventario = InventarioJpaController.getInstance().ConsultarInventarioCodigoProducto(pArtXFact.getArtArticulo().getArtCodigo());
                    if (pInventario.getInvCantidad() >= pArtXFact.getArtCantidad()) {
                        pInventario.setInvCantidad(pInventario.getInvCantidad() - pArtXFact.getArtCantidad());
                        pInventario=InventarioJpaController.getInstance().ModificarInventario(pInventario);
                    } else {
                        Message.getInstance().Information("Información", "Cantidad insuficiente del Articulo= " + pArtXFact.getArtArticulo().getArtNombre());
                    }
                }
            }
        }
    }

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
                    pPago.setPagTipoMoneda("Dolarea");
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

//    public double CalcularVuelto(double pPago) {
//        if (pMontoTotal != 0) {
//            if (pPago > pMontoTotal) {
//                Vuelto= pPago - pMontoTotal;
//                return Vuelto;
//            } else {
//                return 0;
//            }
//        } else {
//            return 0;
//        }
//    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++Metodos GUI-++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    public void CargarTotales() {
        lblTotalNotaCredito.setText(String.valueOf(pMontoTotal));
        lbl_TotalEfectivo.setText(String.valueOf(pMontoTotal));
        lblTotalTarjeta.setText(String.valueOf(pMontoTotal));
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

    /*++++++++++++++++++++++++++++++++++++++++++++++++++++++Metodos Lanzadores+++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
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
}
