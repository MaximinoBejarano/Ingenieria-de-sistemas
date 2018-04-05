/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.ArticuloJpaController;
import ferreteria_las_vegas.model.entities.Factura;
import ferreteria_las_vegas.model.entities.Articulo;
import ferreteria_las_vegas.model.entities.ArticuloXFactura;
import ferreteria_las_vegas.model.entities.Cliente;
import ferreteria_las_vegas.model.entities.Persona;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.GeneralUtils;
import ferreteria_las_vegas.utils.Message;

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
        ListArticulos = new ArrayList<>();
    }

    @FXML
    private void btnBuscarArticulo_Click(ActionEvent event) {
        Lanzar_FXMLBuscarArticulo();
    }

    @FXML
    private void btnSalir_Click(ActionEvent event) {
        ScenesManager.getInstance().LoadSceneMenu();
    }

    @FXML
    private void btnCredito_Click(ActionEvent event) {
        Lanzar_FXML_Abonos();
    }

    @FXML
    private void btnCobrarFactura_Click(ActionEvent event) {
        Lanzar_FXMLPagos();
    }

    @FXML
    private void btnGuardarPedido_Click(ActionEvent event) {

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
 /*+++++++++++++++++++++++++++++++++++++++++++++++++Metodos importantes que no son procesos+++++++++++++++++++++++++++++++++++++++++*/
 /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++Otros metodos+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    public void Calcular_Total() {
        //Calculo el subtotal y el descuento de cada uno de los articulos
        Subtotal = Descuento = Total = ImpuestoVenta = 0;
        for (InventarioCompleto art : ListArticulos) {
            Subtotal += art.getCantArticulo() * art.getArticulo().getArtPrecio();
            Descuento += (art.getCantArticulo() * art.getArticulo().getArtPrecio()) * art.getArticulo().getArtDescuento();
        }

        ImpuestoVenta = (Subtotal - Descuento) * 0.13;
        Total = ((Subtotal - Descuento) + ImpuestoVenta);
        lblTotal.setText(String.valueOf(Total));

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
            colPrecio.setCellValueFactory((cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getArticulo().getArtPrecio()))));

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
                if (!Date.getNewValue().isEmpty() || Integer.valueOf(Date.getNewValue()) > 0) {
                    Date.getRowValue().setCantArticulo(Integer.valueOf(Date.getNewValue()));
                    Calcular_Total();

                }
            });
        } catch (Exception ex) {
            System.err.println(ex);
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
        boolean bandera = false;
        try {
            if (!ListArticulos.isEmpty()) {

//                Iterator<InventarioCompleto> pArCompleto = ListArticulos.iterator();
//                while (pArCompleto.hasNext()) 
                for (InventarioCompleto pArCompleto : ListArticulos) {
                    if (pArCompleto.getArticulo().equals(pCompleto.getArticulo())) {
                        ListArticulos.remove(pArCompleto);
                        pArCompleto.setCantArticulo(pArCompleto.getCantArticulo() + 1);//Aumento la cantidad de articulos
                        ListArticulos.add(pArCompleto);
                    } else {
                        bandera = true;
                    }
                }
                if (bandera) {
                    pCompleto.setCantArticulo(1);
                    ListArticulos.add(pCompleto);
                }
            } else {
                pCompleto.setCantArticulo(1);
                ListArticulos.add(pCompleto);
            }
            Calcular_Total();
            CargarTabla();

        } catch (Exception ex) {
            System.err.println(ex);
        }

    }

    public void Limpiar_Vista() {
        lblTotal.setText("");
        lblCliente.setText("");
        lblFecha.setText(formatter.format(fecha.getTime()));
        txtCodigoArticulo.setText("");
        tbl_Factura.getItems().clear();
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

    public void Lanzar_FXML_Abonos() {
        ScenesManager.getInstance().LoadSceneAbonos();
    }
    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++Variables de Clase++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    Cliente cliente;
    Articulo pArticulo;
    List<InventarioCompleto> ListArticulos;
    double Total, Subtotal, Descuento, ImpuestoVenta;
}
