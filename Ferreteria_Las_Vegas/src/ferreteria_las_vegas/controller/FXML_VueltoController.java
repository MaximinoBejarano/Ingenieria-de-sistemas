/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.utils.AppContext;
import ferreteria_las_vegas.utils.LoggerManager;
import ferreteria_las_vegas.utils.Message;
import ferreteria_las_vegas.utils.PrinterManagerFacturacion;
import ferreteria_las_vegas.utils.WorkIndicatorDialog;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Maximino
 */
public class FXML_VueltoController implements Initializable {

    @FXML
    private Button btnSalir;

    @FXML
    private Label lblVuelto;
    
    @FXML
    private AnchorPane acpVuelto;

        @FXML
    private Button btnImprimir;

 

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lblVuelto.setText("");
        
        CargarVueto();
    }    

    @FXML
    private void btnSalir_Click(ActionEvent event) {
        Stage stageAct = (Stage) btnSalir.getScene().getWindow();
        stageAct.close();
    }


    public void CargarVueto(){
     double vuelto=(double)AppContext.getInstance().get("Vuelto");
     if(vuelto>0){
         lblVuelto.setText("₡ "+String.format("%.2f",vuelto));
     }else{
       lblVuelto.setText("0");
     }
    }
  /*++++++++++++++++++++++++++++++++++++++++++++++++++++++Metodos Lanzadores+++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

       @FXML
    void btnImprimirClick(ActionEvent event) {
         AppContext.getInstance().set("seleccion-FacReimprecion", false);
        ProcesoGenerarFactura();
    }
    
    public void Lanzar_FXML_Facturacion() {
        try {
            ScenesManager.getInstance().LoadSceneFacturacion();
        } catch (IOException ex) {
            Message.getInstance().Error("Error", "Ocurrió un error y no se pudo lanzar la pantalla de Facturación.");
            LoggerManager.Logger().info(ex.toString());
        }
    }
   
    private void ProcesoGenerarFactura() {
        try {
            wd = new WorkIndicatorDialog(btnImprimir.getScene().getWindow(), "Imprimiendo...");

            wd.exec("123", inputParam -> {
                try {
                    PrinterJob pj = PrinterJob.getPrinterJob();
                    pj.setPrintable(new PrinterManagerFacturacion(), getPageFormat(pj));
                    pj.print();
                    return 1;
                } catch (PrinterException ex) {
                    LoggerManager.Logger().info(ex.toString());
                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.ERROR, "Ocurrio un error al imprimir la factura. El codigo de error es "
                                + "el siguiente: " + ex, ButtonType.OK).showAndWait();
                    });
                    return 2;
                }
            });
            
        } catch (Exception ex) {
            LoggerManager.Logger().info(ex.toString());
            new Alert(Alert.AlertType.ERROR, "Ocurrio un error al generar la factura. El codigo de error es " + "el siguiente: " + ex, ButtonType.OK).showAndWait();
        }
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    public PageFormat getPageFormat(PrinterJob pj) {
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double headerHeight = 2.0;
        double middleHeight = 8.0;
        double footerHeight = 2.0;
        double width = convert_CM_To_PPI(10);
        double height = convert_CM_To_PPI(40);
        paper.setSize(width, height);
        paper.setImageableArea(
                0,
                10,
                width,
                height - convert_CM_To_PPI(1)
        );
        pf.setOrientation(PageFormat.PORTRAIT);
        pf.setPaper(paper);

        return pf;
    }

    private static double convert_CM_To_PPI(double cm) {
        return toPPI(cm * 0.393600787);
    }

    private static double toPPI(double inch) {
        return inch * 72d;
    }

    /*--------------------------------------------------------------------------------------------------------------*/
    WorkIndicatorDialog wd;

}
