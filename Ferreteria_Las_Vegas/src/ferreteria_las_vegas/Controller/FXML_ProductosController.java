/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.Controller;

import ferreteria_las_vegas.utils.AppContext;

import ferreteria_las_vegas.model.controller.ArticuloJpaController;
import ferreteria_las_vegas.model.entities.Articulo;
import java.math.BigDecimal;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author MaxBejarano
 */
public class FXML_ProductosController implements Initializable {

    @FXML
    private Button btnEliminarProducto;
    @FXML
    private Button btnEditarProducto;
    @FXML
    private Button btnBuscarProducto;
    @FXML
    private Button btnAgregarProducto;
    @FXML
    private Button btnSalir;
    @FXML
    private TextField txtCodBarras;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtUndMedida;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtDescuento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void FinalizarProceso(ActionEvent event) {
        Stage stageAct = (Stage) btnSalir.getScene().getWindow();
        stageAct.close();
    }

    @FXML
    private void EliminarProductos(ActionEvent event) {
        EliminarProductos();
    }

    @FXML
    private void EditarProductos(ActionEvent event) {
        if (txtNombre.getText().isEmpty()
                || txtDescripcion.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Advertencia: Es necesario completar los campos requeridos", ButtonType.OK).showAndWait();
        } else {
            EditarProductos();
        }
    }

    @FXML
    private void BusquedadProductos(ActionEvent event) {
        ProcesoBusquedad();
        btnEditarProducto.setDisable(false);
        btnEliminarProducto.setDisable(false);
    }

    @FXML
    private void AgregarProducto(ActionEvent e) {
        if (txtNombre.getText().isEmpty() || txtDescripcion.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Advertencia: Es necesario completar los campos requeridos", ButtonType.OK).showAndWait();
        } else {
            GuardarProducto();
        }
    }

    //*****************************************************++ Area de funciones ++****************************************************************+
    /**
     * Se realiza la insercion de articulos a la Base de datos
     */
    private void GuardarProducto() {
        Articulo articulo = new Articulo();
        articulo = ArticuloJpaController.getInstance().InsertarArticulo(ExtraerDatos(articulo));
        if (articulo != null) {
            new Alert(Alert.AlertType.INFORMATION, "Información: Se han ingresado los datos de forma exitosa ", ButtonType.OK).showAndWait();
            LimpiarCampos();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error: No se han guardado los datos", ButtonType.OK).showAndWait();
        }
    }

    private void EditarProductos() {
        Articulo pArticulo = (Articulo) AppContext.getInstance().get("selected-Articulo");
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

    private void EliminarProductos() {
        Articulo pArticulo = (Articulo) AppContext.getInstance().get("selected-Articulo");
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

    /**
     * Lanza la ventana de busqueda
     */
    void ProcesoBusquedad() {
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
            // mandar al servidor al log de errores
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }

    //++++++++++++++++++++++++++++++++++++++  Area de procesos  ++++++++++++++++++++++++++++++++++++++++++++
    /**
     * Procesos de Extracion,carga y eliminación de datos en la vista
     */
    private Articulo ExtraerDatos(Articulo pArticulo) {
        try {
            BigDecimal Precio = new BigDecimal(txtPrecio.getText());
            BigDecimal Descuento;
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
           /*if (!txtCodBarras.getText().isEmpty() || txtCodBarras.getText() != null || txtCodBarras.getText() != "") {
                pArticulo.setArtCodBarra(txtCodBarras.getText());
            }else{
               if(pArticulo.getArtCodBarra()!=null){
                new Alert(Alert.AlertType.WARNING, "No es posible cambiar el codigo existente", ButtonType.OK).showAndWait();
               }
            }*/
            
            if (!txtDescuento.getText().isEmpty()) {
                Descuento = new BigDecimal(txtDescuento.getText());
                pArticulo.setArtDescuento(Descuento);
                pArticulo.setArtEstadoDescuento("A");
            } else {
                Descuento = BigDecimal.ZERO;
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
        Articulo pArticulo = (Articulo) AppContext.getInstance().get("selected-Articulo");
        if (pArticulo != null) {
            txtNombre.setText(pArticulo.getArtNombre());
            txtDescripcion.setText(pArticulo.getArtDescripcion());
            txtCodBarras.setText(pArticulo.getArtCodBarra());
            txtMarca.setText(pArticulo.getArtMarca());
            txtUndMedida.setText(pArticulo.getArtUnidadMedida());
            txtPrecio.setText(pArticulo.getArtPrecio().toString());
            if (pArticulo.getArtDescuento() != null) {
                txtDescuento.setText(pArticulo.getArtDescuento().toString());
            } else {
                txtDescuento.setText("");
            }
        } else {
            btnEditarProducto.setDisable(true);
            btnEliminarProducto.setDisable(true);
            LimpiarCampos();
            
        }

    }
    /*public void validarNumero(KeyEvent ke) {
        if (!(ke.getKeyChar() >= KeyEvent.VK_0 && ke.getKeyChar() <= KeyEvent.VK_9)) {
            ke.consume();
        }
    }*/
}
