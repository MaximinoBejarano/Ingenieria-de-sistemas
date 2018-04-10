/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.utils.Message;

import ferreteria_las_vegas.model.controller.ArticuloJpaController;
import ferreteria_las_vegas.model.entities.Articulo;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.GeneralUtils;
import ferreteria_las_vegas.utils.LoggerManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author MaxBejarano
 */
public class FXML_ProductosController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnEliminarProducto;
    @FXML
    private Button btnEditarProducto;
    @FXML
    private Button btnBuscarProducto;
    @FXML
    private Button btnAgregarProducto;
    @FXML
    private Button btnLimpiar;
    @FXML
    private TextField txtCodBarras;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtDescuento;
    @FXML
    private TextField txtUndMedida;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void btnFinalizarProceso_Click(ActionEvent event) {
        Stage stageAct = (Stage) btnSalir.getScene().getWindow();
        stageAct.close();
    }

    @FXML
    private void btnEliminarProductos_Click(ActionEvent event) {
        EliminarProductos();
    }

    @FXML
    private void btnEditarProductos_Click(ActionEvent event) {
        if (txtNombre.getText().isEmpty()
                || txtDescripcion.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
            Message.getInstance().Warning("Advertencia:", "Es necesario completar los campos requeridos");
        } else {
            EditarProductos();
        }
    }

    @FXML
    private void btnBusquedadProductos_Click(ActionEvent event) {
        Lanzador_BusquedadProductos();
    }

    @FXML
    private void btnAgregarProducto_Click(ActionEvent e) {
        if (txtNombre.getText().isEmpty() || txtDescripcion.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
            Message.getInstance().Warning("Advertencia:", "Es necesario completar los campos requeridos");
        } else {
            GuardarProducto();
        }
    }

    @FXML
    private void btnLimpiarCampos_Click(ActionEvent event) {
        LimpiarCampos();
    }

    @FXML
    private void KeyTypeCodBarras(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtCodBarras.getText().length(), 30, event);
    }

    @FXML
    private void KeyType_txtNombre(KeyEvent event) {
    }

    @FXML
    private void KeyType_txtMarca(KeyEvent event) {
    }

    @FXML
    private void KeyTyped_txtPrecio(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtPrecio.getText().length(), 10, event);
    }

    @FXML
    private void KeyType_txtDescuento(KeyEvent event) {
        GeneralUtils.getInstance().ValidarCampos(true, txtDescuento.getText().length(), 4, event);
        txtDescuento.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (txtDescuento.getText().length() == 1) {
                    txtDescuento.setText("0.0" + txtDescuento.getText());
                } else {
                    txtDescuento.setText("0." + txtDescuento.getText());
                    txtDescuento.setText(txtDescuento.getText().replace("0.0.", "0."));
                }
            }
        });
    }

    @FXML
    private void KeyTyped_txtUndMedida(KeyEvent event) {
    }

    @FXML
    void txtCodBarras_OnAction(ActionEvent event) {

    }

    //*****************************************************++ Area de Procesos ++****************************************************************+
    /**
     * Se realiza la insercion de articulos a la Base de datos
     */
    private void GuardarProducto() {
        try {
            Articulo articulo = new Articulo();
            articulo = ExtraerDatos(articulo);
            if (ArticuloJpaController.getInstance().ConsultarArticuloCodBarras(articulo.getArtCodBarra()) == null) {
                if (ArticuloJpaController.getInstance().ComprobarExistenciaArticulo(articulo) == null) {
                    articulo = ArticuloJpaController.getInstance().InsertarArticulo(articulo);
                    AppContext.getInstance().set("articulo-Ingresado", articulo);
                    if (articulo != null) {
                        Message.getInstance().Information("Información:", "Se han ingresado los datos de forma exitosa ");
                        LimpiarCampos();
                        Stage stageAct = (Stage) btnSalir.getScene().getWindow();
                        stageAct.close();
                    } else {
                        Message.getInstance().Error("Error:", "No se han guardado los datos");
                    }
                } else {
                    Message.getInstance().Warning("Cuidado:", "Este articulo ya existe registrado");
                }
            } else {
                Message.getInstance().Warning("Cuidado:", "Existe un articulo con este Codigo");
            }
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString().concat(" Error en el proceso del registro del producto"));
        }
    }

    /**
     * Se realiza la edicion del articulo validando antes que se encuentre
     * registrado en la base de datos
     */
    private void EditarProductos() {
        try {
            Articulo pArticulo = (Articulo) AppContext.getInstance().get("seleccion-Articulo");
            if (pArticulo != null) {
                pArticulo = ArticuloJpaController.getInstance().ConsultarArticuloCodigo(pArticulo.getArtCodigo());
                if (pArticulo != null) {
                    pArticulo = ExtraerDatos(pArticulo);
                    pArticulo = ArticuloJpaController.getInstance().ModificarArticulos(pArticulo);
                    if (pArticulo != null) {
                        Message.getInstance().Confirmation("Confirmación", "Se edito el articulo con exito");
                        btnEditarProducto.setDisable(true);
                        btnEliminarProducto.setDisable(true);
                        LimpiarCampos();
                    } else {
                        Message.getInstance().Error("Error:", "Ocurrio un error y no se pudo editar el Producto");
                    }
                } else {
                    Message.getInstance().Warning("Cuidado:", "No existe un Producto con el codigo ingresado");
                }
            } else {
                Message.getInstance().Warning("Cuidado:", "Debe consultar un producto existente");
            }
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString().concat(" Error en el proceso de modificación del producto"));
        }
    }

    /**
     * Se elimina los datos de la base de datos Se cambia el estado del producto
     * de Activo a Inactivo
     */
    private void EliminarProductos() {
        try {
            Articulo pArticulo = (Articulo) AppContext.getInstance().get("seleccion-Articulo");
            if (pArticulo != null) {
                pArticulo = ArticuloJpaController.getInstance().ConsultarArticuloCodigo(pArticulo.getArtCodigo());
                pArticulo.setArtEstado("I");
                if (pArticulo != null) {
                    pArticulo = ArticuloJpaController.getInstance().ModificarArticulos(pArticulo);
                    if (pArticulo != null) {
                        Message.getInstance().Confirmation("Confirmación", "Se elimino el Producto con exito");
                        btnEditarProducto.setDisable(true);
                        btnEliminarProducto.setDisable(true);
                        LimpiarCampos();
                    } else {
                        Message.getInstance().Error("Error:", "Ocurrio un error y no se pudo eliminar el Producto");
                    }
                } else {
                    Message.getInstance().Warning("Cuidado:", "No existe un Producto con el codigo ingresado");
                }
            }
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString().concat(" Error en el proceso de eliminación del producto"));
        }
    }

    //++++++++++++++++++++++++++++++++++++++  Area de procesos en GUI ++++++++++++++++++++++++++++++++++++++++++++
    /**
     * Procesos de Extracion,carga y eliminación de datos en la vista
     */
    private Articulo ExtraerDatos(Articulo pArticulo) {
        try {
            double Precio = Double.valueOf(txtPrecio.getText());
            double Descuento;
            if (pArticulo.getArtCodigo() == null) {
                pArticulo = new Articulo(Integer.SIZE, txtNombre.getText(), txtDescripcion.getText(), txtMarca.getText(), txtUndMedida.getText(), Precio, "A");
            } else {
                pArticulo.setArtNombre(txtNombre.getText());
                pArticulo.setArtDescripcion(txtDescripcion.getText());
                pArticulo.setArtMarca(txtMarca.getText());
                pArticulo.setArtUnidadMedida(txtUndMedida.getText());
                pArticulo.setArtPrecio(Precio);
                pArticulo.setArtEstado("A");
            }
            if (txtCodBarras.getText().isEmpty()) {
                pArticulo.setArtCodBarra(null);
            } else {
                pArticulo.setArtCodBarra(txtCodBarras.getText());
            }

            if (!txtDescuento.getText().isEmpty()) {
                Descuento = Double.valueOf(txtDescuento.getText());
                pArticulo.setArtDescuento(Descuento);
                pArticulo.setArtEstadoDescuento("A");
            } else {
                Descuento = 0;
                pArticulo.setArtDescuento(Descuento);
                pArticulo.setArtEstadoDescuento("I");
            }

            return pArticulo;
        } catch (Exception e) {
            System.err.println(e);
            LoggerManager.Logger().info(e.toString().concat(" Error al extrar los datos de la vista en articulos"));
            return null;
        }
    }

    public void LimpiarCampos() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtCodBarras.setText("");
        txtMarca.setText("");
        txtUndMedida.setText("");
        txtPrecio.setText("");
        txtDescuento.setText("");
    }

    public void CargasDatos() {
        try {
            Articulo pArticulo = (Articulo) AppContext.getInstance().get("seleccion-Articulo");
            if (pArticulo != null) {
                txtNombre.setText(pArticulo.getArtNombre());
                txtDescripcion.setText(pArticulo.getArtDescripcion());
                txtMarca.setText(pArticulo.getArtMarca());
                txtUndMedida.setText(pArticulo.getArtUnidadMedida());
                txtPrecio.setText(String.format("%.2f", pArticulo.getArtPrecio()));
                if (pArticulo.getArtCodBarra() != null) {
                    txtCodBarras.setText(pArticulo.getArtCodBarra());
                } else {
                    txtCodBarras.setText("");
                }
                if (pArticulo.getArtDescuento() != null) {
                    txtDescuento.setText(pArticulo.getArtDescuento().toString());
                } else {
                    txtDescuento.setText("");
                }
                btnEditarProducto.setDisable(false);
                btnEliminarProducto.setDisable(false);
            } else {
                btnEditarProducto.setDisable(true);
                btnEliminarProducto.setDisable(true);
                LimpiarCampos();

            }
        } catch (Exception e) {
            System.err.println(e);
            LoggerManager.Logger().info(e.toString().concat(" Error en el proceso de carga de datos en productos"));
        }
    }

    //++++++++++++++++++++++++++++++++++++++  Area de metodos Lanzadores a pantallas ++++++++++++++++++++++++++++++++++++++++++++    
    void Lanzador_BusquedadProductos() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ferreteria_las_vegas/view/FXML_Buscar_Productos.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage(StageStyle.UTILITY);
            stage.initOwner(btnBuscarProducto.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
            CargasDatos();

        } catch (Exception ex) {
            System.err.print(ex);
        }
    }

}
