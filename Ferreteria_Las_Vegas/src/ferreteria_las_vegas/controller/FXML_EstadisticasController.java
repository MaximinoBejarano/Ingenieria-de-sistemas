/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.controller.EstadisticasJPAController;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;

/**
 * FXML Controller class
 *
 * @author johan
 */
public class FXML_EstadisticasController implements Initializable {

    @FXML
    private Button btnCheck;

    @FXML
    private Button btnSalir;

    @FXML
    private TabPane tabPanel;

    /*---------Parte de Mas---------*/
    @FXML
    private BarChart<String, BigDecimal> bcArtMasVendido;

    @FXML
    private CategoryAxis xAxisArtMasVendido;

    @FXML
    private NumberAxis yAxisArtMasVendido;

    @FXML
    private DatePicker dpkArtMVFechaInicial;

    @FXML
    private DatePicker dpkArtMVFechaFinal;

    /*---------Parte de Menos---------*/
    @FXML
    private BarChart<String, BigDecimal> bcArtMenosVendido;

    @FXML
    private CategoryAxis xAxisArtMenosVendido;

    @FXML
    private NumberAxis yAxisArtMenosVendido;

    @FXML
    private DatePicker dpkArtPVFechaInicial;

    @FXML
    private DatePicker dpkArtPVFechaFinal;
    
    /*---------Parte de Clientes---------*/
     @FXML
    private BarChart<String, Long> bcArtMejoreCliente;

    @FXML
    private CategoryAxis xAxisMejorCliente;

    @FXML
    private NumberAxis yAxisMejorCliente;

    @FXML
    private DatePicker dpkMCFechaInicial;

    @FXML
    private DatePicker dpkMCFechaFinal;


    /*--------------------------------------------------------------------------------------------------------------*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.amv = true;
        this.mc = false;
        this.apv = false;

        dpkArtMVFechaInicial.setValue(LocalDate.now());
        dpkArtMVFechaFinal.setValue(LocalDate.now());
        dpkArtPVFechaInicial.setValue(LocalDate.now());
        dpkArtPVFechaFinal.setValue(LocalDate.now());
        dpkMCFechaInicial.setValue(LocalDate.now());
        dpkMCFechaFinal.setValue(LocalDate.now());
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    @FXML
    void btnCheckClick(ActionEvent event) {
        if (this.amv) {
            if (dpkArtMVFechaInicial.getValue() != null && dpkArtMVFechaFinal.getValue() != null) {                
                if (CargarArticuloMasVendidos() == null) {
                    Message.getInstance().Warning("Datos no encontrados", "No existen datos o no se pudieron cargar.");
                    xAxisArtMasVendido.getCategories().clear();
                    bcArtMasVendido.getData().clear();
                }
            } else {
                Message.getInstance().Warning("Fechas", "Debe seleccionar una fecha inicial y final.");
            }
        } else if (this.apv) {
            if (dpkArtPVFechaInicial.getValue() != null && dpkArtPVFechaFinal.getValue() != null) {                
                if (CargarArticuloMenosVendidos() == null) {
                    Message.getInstance().Warning("Datos no encontrados", "No existen datos o no se pudieron cargar.");
                    xAxisArtMenosVendido.getCategories().clear();
                    bcArtMenosVendido.getData().clear();
                }
            } else {
                Message.getInstance().Warning("Fechas", "Debe seleccionar una fecha inicial y final.");
            }
        } else if (this.mc) {
            if (dpkMCFechaInicial.getValue() != null && dpkMCFechaFinal.getValue() != null) {                
                if (CargarMejoresClientes()== null) {
                    Message.getInstance().Warning("Datos no encontrados", "No existen datos o no se pudieron cargar.");
                    xAxisMejorCliente.getCategories().clear();
                    bcArtMejoreCliente.getData().clear();
                }
            } else {
                Message.getInstance().Warning("Fechas", "Debe seleccionar una fecha inicial y final.");
            }
        }
    }

    @FXML
    void btnSalirClick(ActionEvent event) {
        try {
            ScenesManager.getInstance().LoadSceneMenu();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo volver a la pantalla de menú.");
            LoggerManager.Logger().info(ex.toString());
        }
    }

    /*---------Parte de Mas---------*/
    @FXML
    void dpkArtMVFechaInicialClick(ActionEvent event) {
        if (dpkArtMVFechaInicial.getValue() != null && dpkArtMVFechaFinal.getValue() != null) {
            if (dpkArtMVFechaInicial.getValue().isAfter(dpkArtMVFechaFinal.getValue())) {
                dpkArtMVFechaFinal.setValue(dpkArtMVFechaInicial.getValue());
            }
        }
    }

    @FXML
    void dpkArtMVFechaFinalClick(ActionEvent event) {
        if (dpkArtMVFechaFinal.getValue() != null && dpkArtMVFechaInicial.getValue() != null) {
            if (dpkArtMVFechaFinal.getValue().isBefore(dpkArtMVFechaInicial.getValue())) {
                dpkArtMVFechaInicial.setValue(dpkArtMVFechaFinal.getValue());
            }
        }
    }
    
    @FXML
    void tabMasVendidoChange(Event event) {
        this.amv = true;
        this.apv = false;
        this.mc = false;        
    }
    
    /*---------Parte de Menos---------*/    
    @FXML
    void dpkArtPVFechaInicialClick(ActionEvent event) {
        if (dpkArtPVFechaInicial.getValue() != null && dpkArtPVFechaFinal.getValue() != null) {
            if (dpkArtPVFechaInicial.getValue().isAfter(dpkArtPVFechaFinal.getValue())) {
                dpkArtPVFechaFinal.setValue(dpkArtPVFechaInicial.getValue());
            }
        }
    }
    
    @FXML
    void dpkArtPVFechaFinalClick(ActionEvent event) {
        if (dpkArtPVFechaFinal.getValue() != null && dpkArtPVFechaInicial.getValue() != null) {
            if (dpkArtPVFechaFinal.getValue().isBefore(dpkArtPVFechaInicial.getValue())) {
                dpkArtPVFechaInicial.setValue(dpkArtPVFechaFinal.getValue());
            }
        }
    }    
    
    @FXML
    void tabMenosVendidoChange(Event event) {
        this.amv = false;
        this.apv = true;
        this.mc = false;        
    }

    /*---------Parte de Clientes---------*/    
    @FXML
    void dpkMCFechaInicialClick(ActionEvent event) {
        if (dpkMCFechaInicial.getValue() != null && dpkMCFechaFinal.getValue() != null) {
            if (dpkMCFechaInicial.getValue().isAfter(dpkMCFechaFinal.getValue())) {
                dpkMCFechaFinal.setValue(dpkMCFechaInicial.getValue());
            }
        }
    }
    
    @FXML
    void dpkMCFechaFinalClick(ActionEvent event) {
        if (dpkMCFechaFinal.getValue() != null && dpkMCFechaInicial.getValue() != null) {
            if (dpkMCFechaFinal.getValue().isBefore(dpkMCFechaInicial.getValue())) {
                dpkMCFechaInicial.setValue(dpkMCFechaFinal.getValue());
            }
        }
    }    
    
    @FXML
    void tabMejorClienteChange(Event event) {
        this.amv = false;
        this.apv = false;
        this.mc = true;        
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    
    /*---------Parte de Mas---------*/    
    List<Object[]> CargarArticuloMasVendidos() {
        List<Integer> ids = new ArrayList<>();
        List<String> nombres = new ArrayList<>();
        List<BigDecimal> cantidades = new ArrayList<>();
        xAxisArtMasVendido.setAutoRanging(true);
        xAxisArtMasVendido.setLabel("Articulos y Marca");
        yAxisArtMasVendido.setAnimated(true);
        yAxisArtMasVendido.setLabel("Cantidad");

        List<Object[]> result = EstadisticasJPAController.getInstance().ConsultarArticuloMasVendido(dpkArtMVFechaInicial.getValue(), dpkArtMVFechaFinal.getValue());
        if (!result.isEmpty()) {
            for (Object[] objs : result) {
                ids.add((Integer) objs[0]);
                nombres.add((String) objs[1]);
                cantidades.add((BigDecimal) objs[2]);
            }

            ObservableList<String> ArtNames = FXCollections.observableArrayList(nombres);

            xAxisArtMasVendido.getCategories().clear();
            xAxisArtMasVendido.setCategories(ArtNames);

            XYChart.Series<String, BigDecimal> series = new XYChart.Series<>();

            for (int i = 0; i < nombres.size(); i++) {
                series.getData().add(new XYChart.Data<>(nombres.get(i), cantidades.get(i)));
            }
            bcArtMasVendido.getData().clear();
            bcArtMasVendido.getData().add(series);

            return result;
        }
        return null;
    }
    
    /*---------Parte de Menos---------*/    
    List<Object[]> CargarArticuloMenosVendidos() {
        List<Integer> ids = new ArrayList<>();
        List<String> nombres = new ArrayList<>();
        List<BigDecimal> cantidades = new ArrayList<>();
        xAxisArtMenosVendido.setAutoRanging(true);
        xAxisArtMenosVendido.setLabel("Articulos y Marca");
        yAxisArtMenosVendido.setAnimated(true);
        yAxisArtMenosVendido.setLabel("Cantidad");

        List<Object[]> result = EstadisticasJPAController.getInstance().ConsultarArticuloMenosVendido(dpkArtPVFechaInicial.getValue(), dpkArtPVFechaFinal.getValue());
        if (!result.isEmpty()) {
            for (Object[] objs : result) {
                ids.add((Integer) objs[0]);
                nombres.add((String) objs[1]);
                cantidades.add((BigDecimal) objs[2]);
            }

            ObservableList<String> ArtNames = FXCollections.observableArrayList(nombres);

            xAxisArtMenosVendido.getCategories().clear();
            xAxisArtMenosVendido.setCategories(ArtNames);

            XYChart.Series<String, BigDecimal> series = new XYChart.Series<>();

            for (int i = 0; i < nombres.size(); i++) {
                series.getData().add(new XYChart.Data<>(nombres.get(i), cantidades.get(i)));
            }
            bcArtMenosVendido.getData().clear();
            bcArtMenosVendido.getData().add(series);

            return result;
        }
        return null;
    }
    
    /*---------Parte de Clientes---------*/    
    
    List<Object[]> CargarMejoresClientes() {
        List<String> cedulas = new ArrayList<>();
        List<String> nombres = new ArrayList<>();
        List<Long> cantidades = new ArrayList<>();
        List<Double> totales = new ArrayList<>();
        xAxisMejorCliente.setAutoRanging(true);
        xAxisMejorCliente.setLabel("Cliente y Total");
        yAxisMejorCliente.setAnimated(true);
        yAxisMejorCliente.setLabel("Cantidad Compras");

        List<Object[]> result = EstadisticasJPAController.getInstance().ConsultarMejorCliente(dpkMCFechaInicial.getValue(), dpkMCFechaFinal.getValue());
        if (!result.isEmpty()) {
            for (Object[] objs : result) {
                cedulas.add((String) objs[0]);
                nombres.add((String) objs[1]+" / ₡"+ Double.valueOf(objs[3].toString()).intValue());
                cantidades.add((Long) objs[2]);
                totales.add((Double) objs[3]);
            }

            ObservableList<String> ArtNames = FXCollections.observableArrayList(nombres);

            xAxisMejorCliente.getCategories().clear();
            xAxisMejorCliente.setCategories(ArtNames);

            XYChart.Series<String, Long> series = new XYChart.Series<>();

            for (int i = 0; i < nombres.size(); i++) {
                series.getData().add(new XYChart.Data<>(nombres.get(i), cantidades.get(i)));
            }
            bcArtMejoreCliente.getData().clear();
            bcArtMejoreCliente.getData().add(series);

            return result;
        }
        return null;
    }

    private boolean amv;
    private boolean mc;
    private boolean apv;
}
