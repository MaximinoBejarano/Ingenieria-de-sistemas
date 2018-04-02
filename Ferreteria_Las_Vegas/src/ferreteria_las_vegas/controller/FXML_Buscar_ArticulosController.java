/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.ArticuloJpaController;
import ferreteria_las_vegas.model.entities.Articulo;
import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MaxBejarano
 */
public class FXML_Buscar_ArticulosController implements Initializable {

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnSeleccionar;
    @FXML
    private TextField txtCodigoArt;
    @FXML
    private TableView<Articulo> tblArticulos;
    @FXML
    private TableColumn<Articulo, String> colCodigo;
    @FXML
    private TableColumn<Articulo, String> colNombre;
    @FXML
    private TableColumn<Articulo, String> colMarca;
    @FXML
    private TableColumn<Articulo, String> colUndMedida;
    @FXML
    private TableColumn<Articulo, String> colDescripcion;
    @FXML
    private TableColumn<Articulo, BigDecimal> colPrecio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CargarDatosTabla();
    }

    @FXML
    private void btnFinalizarProceso_Click(ActionEvent event) {
        try {
            AppContext.getInstance().set("seleccion-Articulo", null);
            Stage stageAct = (Stage) btnSalir.getScene().getWindow();
            stageAct.close();
        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo cerrar la pantalla. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }

    }

    @FXML
    private void btnConfirmarArgregación_Click(ActionEvent event) {

        try {
            if (tblArticulos.getSelectionModel().getSelectedItem() != null) {
                AppContext.getInstance().set("seleccion-Articulo", tblArticulos.getSelectionModel().getSelectedItem());
                AppContext.getInstance().set("articulo-Ingresado", tblArticulos.getSelectionModel().getSelectedItem());
                Stage stageAct = (Stage) btnSeleccionar.getScene().getWindow();
                stageAct.close();
            } else {
                Message.getInstance().Warning("Cuidado:", "Debe selecionar una fila de la tabla");
            }

        } catch (Exception ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo seleccionar un registro de la tabla. "
                    + "El codigo de error es: " + ex.toString());
            LoggerManager.Logger().info(ex.toString());
        }

    }

    //++++++++++++++++++++++++++++++++++++++  Area de procesos en GUI ++++++++++++++++++++++++++++++++++++++++++++
    /**
     * Se encarga de cargar los datos de todos los productos en la vista
     */
    void CargarDatosTabla() {

        colCodigo.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().getArtCodBarra())));
        colNombre.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().getArtNombre())));
        colMarca.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().getArtMarca())));
        colUndMedida.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().getArtUnidadMedida())));
        colDescripcion.setCellValueFactory((cellData -> new SimpleStringProperty(cellData.getValue().getArtDescripcion())));
        colPrecio.setCellValueFactory((cellData -> new SimpleObjectProperty(String.valueOf(cellData.getValue().getArtPrecio()))));

        List<Articulo> ArticulosList = ArticuloJpaController.getInstance().ConsultarArticulos();
        ArticulosList = ArticulosList.stream().filter(x -> x.getArtEstado().equals("A")).collect(Collectors.toList());
        ObservableList<Articulo> LecturaList = FXCollections.observableArrayList(ArticulosList);
        tblArticulos.setItems(LecturaList);

        FiltroDatosTabla(LecturaList);
    }

    /**
     * Se encarga de filtrar la lista de articulos segun el dato ingresado por
     * el usuario
     *
     * @param OLecturaList
     */
    void FiltroDatosTabla(ObservableList<Articulo> OLecturaList) {

        FilteredList<Articulo> filteredData = new FilteredList<>(OLecturaList, p -> true);
        txtCodigoArt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredData.setPredicate((Articulo pArticulo) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (pArticulo.getArtNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (pArticulo.getArtMarca() != null && !pArticulo.getArtMarca().equals("")) {
                    if (pArticulo.getArtMarca().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                } else if (pArticulo.getArtCodBarra() != null && !pArticulo.getArtCodBarra().equals("")) {
                    if (pArticulo.getArtCodBarra().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
        });
        SortedList<Articulo> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblArticulos.comparatorProperty());
        tblArticulos.setItems(sortedData);
    }
}
