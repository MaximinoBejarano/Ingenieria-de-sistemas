/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.utils;

import ferreteria_las_vegas.model.entities.Abono;
import ferreteria_las_vegas.model.entities.Factura;
import ferreteria_las_vegas.model.entities.NotaCredito;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Maximino
 */
public class PrinterManagerAbono  implements Printable{
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

        Factura pFactura=(Factura)  AppContext.getInstance().get("Factura-Abono");
        Abono pAbono= (Abono)AppContext.getInstance().get("Registro-Abono");
        
        int result = NO_SUCH_PAGE;
        if (pageIndex == 0) {
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
            try {
                int y = 0;
                int x = 5;
                int yShift = 10;
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                y += yShift;
                g2d.drawString("-----------------------------------------", x, y);
                y += yShift;
                g2d.drawString(" FERRETERIA LAS VEGAS", x, y);
                y += yShift;
                g2d.drawString(" PEJIBAYE, PERÉZ ZELEDÓN", x, y);
                y += yShift;
                g2d.drawString(" Cédula: 3-98736-8799", x, y);
                y += yShift;
                 g2d.drawString(" Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(pAbono.getAboFecha()), x, y);
                y += yShift;
                g2d.drawString("-----------------------------------------", x, y);
                y += yShift;
                y += yShift;
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 15));
                g2d.drawString("Número de Abono: "+ String.format("%08d",pAbono.getAboCodigo()), x, y);
                y += yShift;
                y += yShift;
                y += yShift;
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 15));
                g2d.drawString(" Valor: " + String.format("%.2f", pAbono.getAboMonto()), x, y);
                y += yShift;
                y += yShift;
                y += yShift;
                g2d.drawString("-----------------------------------------", x, y);
                y += yShift;
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                g2d.drawString(" Factura Abonada: " + String.format("%08d", pFactura.getFacCodigo()), x, y);
                y += yShift;
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                g2d.drawString(" Cedula Cliente: " + pFactura.getFacCliente().getCliPersona(), x, y);
                y += yShift;
                g2d.drawString("-----------------------------------------", x, y);
                y += yShift;
                y += yShift;
                y += yShift;
                /**
                 * **********************************************************************
                 */

            } catch (Exception ex) {
                System.out.println(ex);
            }
            result = PAGE_EXISTS;
        }
        return result;
    }

}
