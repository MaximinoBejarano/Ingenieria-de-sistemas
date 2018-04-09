/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.entities.ArticuloXFactura;
import ferreteria_las_vegas.model.entities.Factura;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    }

    @FXML
    private void btnAnularFactura(ActionEvent event) {
    }

    @FXML
    private void btnBuscarFactura(ActionEvent event) {
        LanzarFacturas();
        try {
            RecargarTblListaAriticulos();
            Factura pFactura = (Factura) AppContext.getInstance().get("seleccion-FacCliente");
            if (pFactura != null) {
                lblCodigoFactura.setText(pFactura.getFacCodigo().toString());
                lblCliente.setText(pFactura.getFacCliente().getPersona().getPerNombre() + " "
                        + pFactura.getFacCliente().getPersona().getPerPApellido() + " "
                        + pFactura.getFacCliente().getPersona().getPerSApellido());
                lblFecha.setText(formateador.format(pFactura.getFacFecha()));
                lblUsuario.setText("null");
                lblMontoTotal.setText(String.format("%.2f", pFactura.getFacTotal()));
                lblTipoFactura.setText(pFactura.getFactTipoFact());
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error al intentar cargar los datos de la factura. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    @FXML
    private void btnReimprimirFactura(ActionEvent event) {

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

    public void LimpiarInterface(){
    
        lblCodigoFactura.setText("");
        lblCliente.setText("");
        lblFecha.setText("");
        lblUsuario.setText("");
        lblMontoTotal.setText("");
        lblTipoFactura.setText("");
        tblListaArticulos.getItems().clear();
        AppContext.getInstance().set("seleccion-FacCliente",null);
    }
    
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yy");
}
