/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.TipoPagoJPAController;
import ferreteria_las_vegas.model.entities.Factura;
import ferreteria_las_vegas.model.entities.Pago;
import ferreteria_las_vegas.model.entities.ArticuloXFactura;

import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.GeneralUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private ComboBox<?> cmbMoneda;

    @FXML
    private TextField txtMontoEfectivo;

    @FXML
    private Label lblVuelto;

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
    private Label lblVueltoNotaCredio;

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
        pFactura = (Factura) AppContext.getInstance().get("Factura");
        pMontoTotal = pFactura.getFacTotal();
        ListArticulosXFactura = (List<ArticuloXFactura>) AppContext.getInstance().get("ArticulosXFactura");
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

    }

    @FXML
    private void txtMontoEfectivo_KeyTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtMontoEfectivo.getText().length(), 10, event);
    }

    @FXML
    private void txtMontoTarjeta_KeyTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtMontoTarjeta.getText().length(), 10, event);
//        txtMontoEfectivo.textProperty().addListener(new ChangeListener<String>{});
    }

    @FXML
    private void txtCodigoTarjeta_KeyTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtCodigoTarjeta.getText().length(), 4, event);
    }

    @FXML
    private void txtNumeroVale_KeyTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtNumeroVale.getText().length(), 8, event);
    }

    @FXML
    void txtMontoEfectivo_OnAction(ActionEvent event) {
        if (!txtMontoEfectivo.getText().isEmpty()) {
            lblVuelto.setText(String.valueOf(CalcularVuelto(Double.valueOf(txtMontoEfectivo.getText()))));
            RecalcularTotalPagar();
        }
        RecalcularTotalPagar();
    }

    @FXML
    void txtNumeroVale_OnAction(ActionEvent event) {
//        RecalcularTotalPagar();
//        lblVueltoNotaCredio.setText(String.valueOf(CalcularVuelto(Double.valueOf(lblMonto.getText()))));
    }

    @FXML
    void txtMontoEfectivo_TextChange(InputMethodEvent event) {
      
            if (!txtMontoEfectivo.getText().isEmpty()) {
                lblVuelto.setText(String.valueOf(CalcularVuelto(Double.valueOf(txtMontoEfectivo.getText()))));
                RecalcularTotalPagar();
            } else {
                lblVuelto.setText("");
                RecalcularTotalPagar();
            }
        
    }

    @FXML
    void txtMontoTarjeta_TextChange(InputMethodEvent event) {
        if (!txtMontoTarjeta.getText().isEmpty()) {
            RecalcularTotalPagar();
        } else {
            RecalcularTotalPagar();
        }
    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++Procesos fundamentales++++++++++++++++++++++++++++++++++++++++++++++++++++*/
 /*+++++++++++++++++++++++++++++++++++++++++++++++++Metodos importantes que no son procesos+++++++++++++++++++++++++++++++++++++++++*/
    public void ProcesoPago(double pMonto, String TipoPago) {
        pPago = new Pago();
        if (pMonto > 0) {
            pPago.setPagEstado("A");
            pPago.setPagMonto(pMonto);
            if (TipoPago.equals("Efectivo")) {
                //pPago.setPagTipoMoneda(TipoPago);
                //pPago.setPagTipoCambio(pMonto);
                pPago.setPagTipoPago(TipoPagoJPAController.getInstance().Consultar_TipoPagoNombre("Contado"));
            }
            if (TipoPago.equals("Tarjeta")) {
                pPago.setPagTipoPago(TipoPagoJPAController.getInstance().Consultar_TipoPagoNombre("Credito"));
            }
            if (TipoPago.equals("NotaCredito")) {
                // pPago.setPagNotaCredito(pagNotaCredito);
            }
            listPagos.add(pPago);
        }
    }

    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++Otros metodos+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    // Recalcula el monto total a pagar 
    public void RecalcularTotalPagar() {
        pMontoTotal = pFactura.getFacTotal();
        if (!txtMontoEfectivo.getText().isEmpty() && Double.valueOf(txtMontoEfectivo.getText()) > 0) {
            if (Double.valueOf(txtMontoEfectivo.getText()) < pMontoTotal) {
                pMontoTotal -= Double.valueOf(txtMontoEfectivo.getText());
            } else {
                pMontoTotal = 0;
            }
        }
        if (!txtMontoTarjeta.getText().isEmpty() && pMontoTotal > 0) {
            if (Double.valueOf(txtMontoTarjeta.getText()) < pMontoTotal) {
                pMontoTotal -= Double.valueOf(txtMontoTarjeta.getText());
            } else {
                pMontoTotal = 0;
            }
        }
        if (!lblMonto.getText().isEmpty() && pMontoTotal > 0) {
            if (Double.valueOf(lblMonto.getText()) < pMontoTotal) {
                pMontoTotal -= Double.valueOf(lblMonto.getText());
            } else {
                pMontoTotal = 0;
            }
        }
        CargarTotales();
    }

    public double CalcularVuelto(double pPago) {
        if (pPago > pMontoTotal) {
            return pPago - pMontoTotal;
        } else {
            return 0;
        }
    }

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
        lblVuelto.setText("");
        lblVueltoNotaCredio.setText("");
        txtCodigoTarjeta.setText("");
        txtMontoEfectivo.setText("");
        txtNumeroVale.setText("");
        txtMontoTarjeta.setText("");
    }

    /*++++++++++++++++++++++++++++++++++++++++++++++++++++++Metodos Lanzadores+++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
 /*+++++++++++++++++++++++++++++++++++++++++++++++++++++Variables de Clase++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    Factura pFactura;
    Pago pPago;
    double pMontoTotal;
    List<Pago> listPagos;
    List<ArticuloXFactura> ListArticulosXFactura;
}
