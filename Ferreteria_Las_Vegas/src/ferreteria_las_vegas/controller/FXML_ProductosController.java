/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.ArticuloJpaController;
import ferreteria_las_vegas.model.entities.Articulo;
import ferreteria_las_vegas.utils.AppContext;
import java.net.URL;
import java.util.ResourceBundle;
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
            new Alert(Alert.AlertType.WARNING, "Advertencia: Es necesario completar los campos requeridos", ButtonType.OK).showAndWait();
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
            new Alert(Alert.AlertType.WARNING, "Advertencia: Es necesario completar los campos requeridos", ButtonType.OK).showAndWait();
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
        validarNumero(event);
    }

    @FXML
    private void KeyType_txtNombre(KeyEvent event) {
    }

    @FXML
    private void KeyType_txtMarca(KeyEvent event) {
    }

    @FXML
    private void KeyTyped_txtPrecio(KeyEvent event) {
        validarNumero(event);
    }

    @FXML
    private void KeyType_txtDescuento(KeyEvent event) {
        validarNumero(event);
    }

    @FXML
    private void KeyTyped_txtUndMedida(KeyEvent event) {
    }

    //*****************************************************++ Area de Procesos ++****************************************************************+
    /**
     * Se realiza la insercion de articulos a la Base de datos
     */
    private void GuardarProducto() {
        Articulo articulo = new Articulo();
        articulo = ExtraerDatos(articulo);
        if (ArticuloJpaController.getInstance().ConsultarArticuloCodBarras(articulo.getArtCodBarra()) == null) {
            if (ArticuloJpaController.getInstance().ComprobarExistenciaArticulo(articulo)==null) {
                articulo = ArticuloJpaController.getInstance().InsertarArticulo(articulo);
                AppContext.getInstance().set("articulo-Ingresado", articulo);
                if (articulo != null) {
                    new Alert(Alert.AlertType.INFORMATION, "Información: Se han ingresado los datos de forma exitosa ", ButtonType.OK).showAndWait();
                    LimpiarCampos();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error: No se han guardado los datos", ButtonType.OK).showAndWait();
                }
            }else{
               new Alert(Alert.AlertType.WARNING, "Este articulo ya existe registrado", ButtonType.OK).showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Existe un articulo con este Codigo", ButtonType.OK).showAndWait();
        }
    }
    /**
     * Se realiza la edicion del articulo
     * validando antes que se encuentre registrado en la base de datos
     */

    private void EditarProductos() {
        Articulo pArticulo = (Articulo) AppContext.getInstance().get("seleccion-Articulo");
        if (pArticulo != null) {
            pArticulo = ArticuloJpaController.getInstance().ConsultarArticuloCodigo(pArticulo.getArtCodigo());
            if (pArticulo != null) {
                pArticulo = ExtraerDatos(pArticulo);
                pArticulo = ArticuloJpaController.getInstance().ModificarArticulos(pArticulo);
                if (pArticulo != null) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Se edito el articulo con exito", ButtonType.OK).showAndWait();
                    btnEditarProducto.setDisable(true);
                    btnEliminarProducto.setDisable(true);
                    LimpiarCampos();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Ocurrio un error y no se pudo editar el Producto.", ButtonType.OK).showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "No existe un Producto con el codigo ingresado.", ButtonType.OK).showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Debe consultar un producto existente", ButtonType.OK).showAndWait();
        }
    }
   /**
    * Se elimina los datos de la base de datos
    * Se cambia el estado del producto de Activo a Inactivo
    */  
    private void EliminarProductos() {
        Articulo pArticulo = (Articulo) AppContext.getInstance().get("seleccion-Articulo");
        if (pArticulo != null) {
            pArticulo = ArticuloJpaController.getInstance().ConsultarArticuloCodigo(pArticulo.getArtCodigo());
            pArticulo.setArtEstado("I");
            if (pArticulo != null) {
                pArticulo = ArticuloJpaController.getInstance().ModificarArticulos(pArticulo);
                if (pArticulo != null) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Se elimino el Producto con exito", ButtonType.OK).showAndWait();
                    btnEditarProducto.setDisable(true);
                    btnEliminarProducto.setDisable(true);
                    LimpiarCampos();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Ocurrio un error y no se pudo eliminar el Producto.", ButtonType.OK).showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "No existe un Producto con el codigo ingresado.", ButtonType.OK).showAndWait();
            }
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
        Articulo pArticulo = (Articulo) AppContext.getInstance().get("seleccion-Articulo");
        if (pArticulo != null) {
            txtNombre.setText(pArticulo.getArtNombre());
            txtDescripcion.setText(pArticulo.getArtDescripcion());
            txtMarca.setText(pArticulo.getArtMarca());
            txtUndMedida.setText(pArticulo.getArtUnidadMedida());
            txtPrecio.setText(String.valueOf(pArticulo.getArtPrecio()));
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
    }

    //++++++++++++++++++++++++++++++++++++++  Area de validaciones de componetes de la GUI ++++++++++++++++++++++++++++++++++++++++++++
    public void validarNumero(KeyEvent event) {
        String character = event.getCharacter();
        if (!checkNumerico(character)) {
            event.consume();
            new Alert(Alert.AlertType.WARNING, "Este campo solo acepta numeros", ButtonType.OK).showAndWait();
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