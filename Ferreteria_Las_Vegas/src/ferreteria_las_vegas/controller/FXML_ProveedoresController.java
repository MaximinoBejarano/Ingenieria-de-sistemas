/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.CuentasXPagarJpaController;
import ferreteria_las_vegas.model.controller.ProveedorJpaController;
import ferreteria_las_vegas.model.entities.Contacto;
import ferreteria_las_vegas.model.entities.CuentaXPagar;
import ferreteria_las_vegas.model.entities.Direccion;
import ferreteria_las_vegas.model.entities.Proveedor;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.GeneralUtils;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Johan
 */
public class FXML_ProveedoresController implements Initializable {
    
    @FXML
    private TabPane tabPanel;

    /*---------Parte de Proveedores---------*/
    @FXML
    private Button btnSalir;

    @FXML
    private Button btnEliminar;

    @FXML
    private Tooltip totEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private Tooltip totBuscar;

    @FXML
    private Tooltip totEditar;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Tooltip totAgregar;

    @FXML
    private TextField txtCedulaJuridica;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtTelefono2;

    @FXML
    private TextArea txtDireccion;

    /*---------Parte de Cuentas por Pagar---------*/
    @FXML
    private TextField txtFiltro;

    @FXML
    private CheckBox chbPagadas;

    @FXML
    private TableView<CuentaXPagar> tblCuentasXPagar;

    @FXML
    private TextField txtNumeroDeposito;

    @FXML
    private TextField txtMontoAbono;

    @FXML
    private Button btnLimpiarAbono;

    @FXML
    private Button btnHistoriaAbonos;

    @FXML
    private Button btnAgregarAbono;

    @FXML
    private DatePicker dpkFechaAbono;

    /*--------------------------------------------------------------------------------------------------------------*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        proFlag = true;
        comFlag = false;
        aboFlag = false;
        CargarCuentasXPagar();
    }

    /*--------------------------------------------------------------------------------------------------------------*/
 /*---------Parte de Proveedores---------*/
    @FXML
    void btnAgregarClick(ActionEvent event) {
        if (proFlag) {
            if (txtCedulaJuridica.getText().isEmpty() || txtNombre.getText().isEmpty()
                    || txtDireccion.getText().isEmpty() || txtTelefono.getText().isEmpty()) {
                Message.getInstance().Warning("Información requerida", "Debe completar todos los campos requeridos.");
            } else {
                ProcesoAgregarProveedor();
            }
        }
    }

    @FXML
    void btnBuscarClick(ActionEvent event) {
        if (proFlag) {
            System.out.println("ferreteria_las_vegas.controller.FXML_ProveedoresController.btnBuscarClick()");
            ProcesoBuscarProveedor();
        }
    }

    @FXML
    void btnEditarClick(ActionEvent event) {
        if (proFlag) {
            if (txtCedulaJuridica.getText().isEmpty() || txtNombre.getText().isEmpty()
                    || txtDireccion.getText().isEmpty() || txtTelefono.getText().isEmpty()) {
                Message.getInstance().Warning("Información requerida", "Debe completar todos los campos requeridos.");
            } else {
                ProcesoEditarProveedor();
            }
        }
    }

    @FXML
    void btnEliminarClick(ActionEvent event) {
        if (proFlag) {
            if (txtCedulaJuridica.getText().isEmpty()) {
                Message.getInstance().Warning("Información requerida", "Debe completar todos los campos requeridos.");
            } else {
                ProcesoEliminarProveedor();
            }
        } else if (comFlag) {

        } else if (aboFlag) {

        }
    }

    @FXML
    void btnSalirClick(ActionEvent event) {
        ScenesManager.getInstance().LoadSceneMenu();
    }

    @FXML
    void txtCedulaJuridicaTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtCedulaJuridica.getText().length(), 30, event);
    }

    @FXML
    void txtNombreTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtNombre.getText().length(), 30, event);
    }

    @FXML
    void txtCorreoTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtCorreo.getText().length(), 30, event);
    }

    @FXML
    void txtTelefonoTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtTelefono.getText().length(), 30, event);
    }

    @FXML
    void txtTelefono2Typed(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtTelefono2.getText().length(), 30, event);
    }

    @FXML
    void txtDireccionTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtDireccion.getText().length(), 30, event);
    }

    @FXML
    void chbPagadasClick(ActionEvent event) {
        CargarCuentasXPagar();
    }

    @FXML
    void tabProveedoresChange(Event event) {
        proFlag = true;
        comFlag = false;
        aboFlag = false;
        ProveedoresGUI();
    }

    @FXML
    void tabCuentasChange(Event event) {
        proFlag = false;
        comFlag = true;
        aboFlag = false;
        CuentasGUI();
    }

    @FXML
    void tabAbonosChange(Event event) {
        proFlag = false;
        comFlag = false;
        aboFlag = true;
        AbonosGUI();
    }

    /*---------Parte de Cuentas por Pagar---------*/
    @FXML
    void btnAgregarAbonoClick(ActionEvent event) {
        ProcesoAgregarAbono();
    }

    @FXML
    void btnHistoriaAbonosClick(ActionEvent event) {
        tabPanel.getSelectionModel().selectNext();
        CargarHistorialAbonos();
    }

    @FXML
    void btnLimpiarAbonoClick(ActionEvent event) {
        LimpiarControlesCuentasGUI();
    }

    @FXML
    void txtNumeroDepositoTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(false, txtNumeroDeposito.getText().length(), 30, event);
    }

    @FXML
    void txtMontoAbonoTyped(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtMontoAbono.getText().length(), 8, event);
    }

    /*--------------------------------------------------------------------------------------------------------------*/
 /*---------Parte de Proveedores---------*/
    void ProcesoAgregarProveedor() {
        try {
            Proveedor proveedor = ProveedorJpaController.getInstance().ConsultarProveedorCedulaJuridica(txtCedulaJuridica.getText());
            if (proveedor == null) {
                proveedor = new Proveedor(Integer.SIZE, txtNombre.getText(), txtCedulaJuridica.getText(), "A");

                String mail = "No tiene";
                String tel2 = "No tiene";

                if (!txtTelefono2.getText().isEmpty()) {
                    tel2 = txtTelefono2.getText();
                }

                if (!txtCorreo.getText().isEmpty()) {
                    mail = txtCorreo.getText();
                }

                Contacto contactoTel = new Contacto(Integer.SIZE, txtTelefono.getText(), "TEL");
                Contacto contactoTel2 = new Contacto(Integer.SIZE, tel2, "TEL2");
                Contacto contactoEma = new Contacto(Integer.SIZE, mail, "EMAIL");
                Direccion direcion = new Direccion(Integer.SIZE, txtDireccion.getText());

                proveedor = ProveedorJpaController.getInstance().AgregarProveedor(proveedor, direcion, contactoTel, contactoTel2, contactoEma);
                if (proveedor != null) {
                    Message.getInstance().Information("Acción exitosa", "Proveedor agregado corectamente.");
                    LimpiarControlesProveedorGUI();
                } else {
                    Message.getInstance().Error("Accion no exitosa", "Ocurrió un error y no se pudo agregar el proveedor.");
                }
            } else {
                if (Message.getInstance().Confirmation("Proveedor Existente", "Ya existe un proveedor registrado con los mismos datos.\n"
                        + "¿Desea actualizar la informacón?")) {
                    ProcesoEditarProveedor();
                }
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo agregar el proveedor. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void ProcesoBuscarProveedor() {
        LanzarBusquedaProveedores();
        Proveedor proveedor = (Proveedor) AppContext.getInstance().get("selected-proveedor");
        if (proveedor != null) {
            CargarDatosUsuario(proveedor);
        }
    }

    void ProcesoEditarProveedor() {
        try {
            Proveedor proveedor = ProveedorJpaController.getInstance().ConsultarProveedorCedulaJuridica(txtCedulaJuridica.getText());
            if (proveedor != null) {

                proveedor.setProNombre(txtNombre.getText());

                Contacto contactoTel = BuscarContactoTipo(proveedor, "TEL");
                Contacto contactoTel2 = BuscarContactoTipo(proveedor, "TEL2");
                Contacto contactoEma = BuscarContactoTipo(proveedor, "EMAIL");

                Direccion direcion = proveedor.getDireccionList().get(0);

                contactoTel.setConContacto(txtTelefono.getText());

                if (contactoTel2 != null) {
                    contactoTel2.setConContacto(txtTelefono2.getText());
                }

                if (contactoEma != null) {
                    contactoEma.setConContacto(txtCorreo.getText());
                }
                direcion.setDirDirExacta(txtDireccion.getText());

                proveedor.setProEstado("A");

                proveedor = ProveedorJpaController.getInstance().ModificarProveedor(proveedor);
                if (proveedor != null) {
                    Message.getInstance().Information("Acción exitosa", "Proveedor editado corectamente.");
                } else {
                    Message.getInstance().Error("Accion no exitosa", "Ocurrió un error y no se pudieron editar los datos del Proveedor.");
                }
            } else {
                Message.getInstance().Warning("Proveedor no existente", "No existe un Proveedor con la información ingresada.");
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo editar el proveedor. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void ProcesoEliminarProveedor() {
        try {
            Proveedor proveedor = ProveedorJpaController.getInstance().ConsultarProveedorCedulaJuridica(txtCedulaJuridica.getText());
            if (proveedor != null) {
                proveedor.setProEstado("I");
                ProveedorJpaController.getInstance().ModificarProveedor(proveedor);
                Message.getInstance().Information("Acción exitosa", "Empleado eliminado corectamente.");
                LimpiarControlesProveedorGUI();
            } else {
                Message.getInstance().Warning("Empleado no existente", "No existe un empleado con la cédula ingresada.");
            }
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo eliminar el proveedor. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    /*---------Parte de Cuentas por Pagar---------*/
    void ProcesoAgregarAbono() {

    }

    /*--------------------------------------------------------------------------------------------------------------*/
    
    /*---------Parte de Proveedores---------*/
    void CargarDatosUsuario(Proveedor proveedor) {
        try {
            txtCedulaJuridica.setText(proveedor.getProCedulaJuridica());
            txtNombre.setText(proveedor.getProNombre());
            txtTelefono.setText(BuscarContactoContacto(proveedor, "TEL"));
            txtTelefono2.setText(BuscarContactoContacto(proveedor, "TEL2"));
            txtCorreo.setText(BuscarContactoContacto(proveedor, "EMAIL"));
            txtDireccion.setText(proveedor.getDireccionList().get(0).getDirDirExacta());

        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo cargar la información del proveedor. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    Contacto BuscarContactoTipo(Proveedor pProveedor, String Tipo) {
        for (Contacto con : pProveedor.getContactoList()) {
            if (con.getConTipoContacto().equalsIgnoreCase(Tipo)) {
                return con;
            }
        }
        return null;
    }

    String BuscarContactoContacto(Proveedor pProveedor, String Tipo) {
        for (Contacto con : pProveedor.getContactoList()) {
            if (con.getConTipoContacto().equalsIgnoreCase(Tipo)) {
                return con.getConContacto();
            }
        }
        return "";
    }

    /*---------Parte de Cuentas por Pagar---------*/
    void CargarCuentasXPagar() {
        try {
            tblCuentasXPagar.setItems(null);
            tblCuentasXPagar.getColumns().clear();

            TableColumn<CuentaXPagar, String> colInfoCuenta = new TableColumn<>("Información de Cuentas");
            TableColumn<CuentaXPagar, String> colCompra = new TableColumn<>("Factura de Compra");
            TableColumn<CuentaXPagar, String> colProveedor = new TableColumn<>("Proveedor");
            TableColumn<CuentaXPagar, String> colSaldoOriginal = new TableColumn<>("Saldo Original");
            TableColumn<CuentaXPagar, String> colSaldoActual = new TableColumn<>("Saldo Actual");

            colInfoCuenta.getColumns().addAll(colCompra, colProveedor, colSaldoOriginal, colSaldoActual);
            tblCuentasXPagar.getColumns().add(colInfoCuenta);

            colCompra.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getCueCompra().getComNumeroFact()));
            colProveedor.setCellValueFactory((cellData) -> new SimpleStringProperty(cellData.getValue().getCueProveedor().getProNombre()));
            colSaldoOriginal.setCellValueFactory((cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCueSaldoCompra())));
            colSaldoActual.setCellValueFactory((cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCueSaldo())));

            List<CuentaXPagar> CuentasList = CuentasXPagarJpaController.getInstance().ConsultarCuentasXPagar().stream().filter(e -> e.getCueEstado().equalsIgnoreCase("A")).collect(Collectors.toList());

            if (!chbPagadas.isSelected()) {
                CuentasList = CuentasList.stream().filter(e -> e.getCueSaldo() > 0).collect(Collectors.toList());
            }

            ObservableList<CuentaXPagar> OCuentasList = FXCollections.observableArrayList(CuentasList);
            tblCuentasXPagar.setItems(OCuentasList);

            FiltroCuentasXPagar(OCuentasList);
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo cargar la información de la tabla de cuentas. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    void FiltroCuentasXPagar(ObservableList<CuentaXPagar> OList) {
        FilteredList<CuentaXPagar> filteredData = new FilteredList<>(OList, p -> true);
        txtFiltro.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredData.setPredicate((CuentaXPagar cuenta) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (cuenta.getCueCompra().getComNumeroFact().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (cuenta.getCueProveedor().getProNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(cuenta.getCueSaldoCompra()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(cuenta.getCueSaldo()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<CuentaXPagar> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblCuentasXPagar.comparatorProperty());
        tblCuentasXPagar.setItems(sortedData);
    }
    
    /*---------Parte de Cuentas por Pagar---------*/
    void CargarHistorialAbonos(){
        
    }

    /*--------------------------------------------------------------------------------------------------------------*/
 /*---------Parte de Proveedores---------*/
    void ProveedoresGUI() {
        btnAgregar.setDisable(false);
        btnBuscar.setDisable(false);
        btnEditar.setDisable(false);
        btnEliminar.setDisable(false);
        totAgregar.setText("Agregar Proveedor");
        totBuscar.setText("Buscar Proveedores");
        totEditar.setText("Editar Información de Proveedor");
        totEliminar.setText("Eliminar Proveedor");
    }

    void LimpiarControlesProveedorGUI() {
        txtCedulaJuridica.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtTelefono2.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
    }

    /*---------Parte de Cuentas por Pagar---------*/
    void CuentasGUI() {
        btnAgregar.setDisable(true);
        btnBuscar.setDisable(true);
        btnEditar.setDisable(true);
        btnEliminar.setDisable(false);
        totAgregar.setText("Agregar Abono");
        totBuscar.setText("Opción no disponible");
        totEditar.setText("Opción no disponible");
        totEliminar.setText("Eliminar Cuenta");
    }

    void LimpiarControlesCuentasGUI() {
        txtNumeroDeposito.setText("");
        txtMontoAbono.setText("");
        dpkFechaAbono.setValue(null);
    }


    /*---------Parte de Abonos---------*/
    void AbonosGUI() {
        btnAgregar.setDisable(true);
        btnBuscar.setDisable(true);
        btnEditar.setDisable(true);
        btnEliminar.setDisable(false);
        totAgregar.setText("Opción no disponible");
        totBuscar.setText("Opción no disponible");
        totEditar.setText("Opción no disponible");
        totEliminar.setText("Eliminar Abono");
    }

    /*--------------------------------------------------------------------------------------------------------------*/
 /*---------Parte de Proveedores---------*/
    void LanzarBusquedaProveedores() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Proveedores.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initOwner(btnBuscar.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de búsqueda."
                    + "El código de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    private boolean proFlag;
    private boolean comFlag;
    private boolean aboFlag;
}
