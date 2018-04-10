/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.utils;

import ferreteria_las_vegas.model.entities.ArticuloXFactura;
import ferreteria_las_vegas.model.entities.Cliente;
import ferreteria_las_vegas.model.entities.Factura;
import ferreteria_las_vegas.model.entities.Pago;
import ferreteria_las_vegas.model.entities.Usuario;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author wili
 */
public class PrinterManagerFacturacion implements Printable {

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {

        boolean rImprecion = (boolean) AppContext.getInstance().get("seleccion-FacReimprecion");
        Factura pFactura = (Factura) AppContext.getInstance().get("seleccion-FacCliente");
        Usuario pUsuario = (Usuario) AppContext.getInstance().get("user");
        String Vuelto = "0";

        int result = NO_SUCH_PAGE;
        if (pageIndex == 0) {
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
            try {
                int y = 0;
                int x = 5;
                int yShift = 10;
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));

                g2d.drawString("-----------------------------------------", x, y);
                y += yShift;
                g2d.drawString(" FERRETERIA LAS VEGAS", x, y);
                y += yShift;
                g2d.drawString(" PEJIBAYE, PERÉZ ZELEDÓN", x, y);
                y += yShift;
                g2d.drawString(" Cédula: 3-98736-8799", x, y);
                y += yShift;
                g2d.drawString(" Fecha: " + new SimpleDateFormat("dd/MM/yyyy").format(pFactura.getFacFecha()), x, y);
                y += yShift;
                g2d.drawString(" Número de Factura: " + String.format("%08d", pFactura.getFacCodigo()), x, y);
                y += yShift;
                g2d.drawString("-----------------------------------------", x, y);
                y += yShift;
                g2d.drawString(" Cliente: " + pFactura.getFacCliente().getPersona().getPerNombre() + " " + pFactura.getFacCliente().getPersona().getPerPApellido(), x, y);
                y += yShift;
                g2d.drawString(" Número de Cédula: " + pFactura.getFacCliente().getPersona().getPerCedula(), x, y);
                y += yShift;
                g2d.drawString(" Tipo de Factura: " + pFactura.getFactTipoFact().replace("E", "Cóntado").replace("K", "Crédito"), x, y);
                y += yShift;
                g2d.drawString("-----------------------------------------", x, y);
                y += yShift;
                for (ArticuloXFactura articuloXFacturaList : pFactura.getArticuloXFacturaList()) {
                    g2d.drawString(articuloXFacturaList.getArtArticulo().getArtNombre() + " " + articuloXFacturaList.getArtArticulo().getArtMarca() + " " + articuloXFacturaList.getArtArticulo().getArtUnidadMedida(), x, y);
                    y += yShift;
                    g2d.drawString(String.format("%08d", articuloXFacturaList.getArtCantidad()) + "    " + articuloXFacturaList.getArtArticulo().getArtCodigo().toString() + "        "
                            + String.format("%.2f", articuloXFacturaList.getArtPrecio() + (articuloXFacturaList.getArtPrecio() * 0.13)), x, y);
                    y += yShift;
                    y += yShift;
                }
                g2d.drawString("-----------------------------------------", x, y);
                y += yShift;
                g2d.drawString("SubTotal: " +  String.format("%.2f",pFactura.getFatSubtotal()), x, y);
                y += yShift;
                g2d.drawString("Descuento: " +  String.format("%.2f",pFactura.getFacDescuento()), x, y);
                y += yShift;
                g2d.drawString("Impuesto: " +  String.format("%.2f",pFactura.getFacImpVentas()), x, y);
                y += yShift;
                g2d.drawString("Total: " +  String.format("%.2f",pFactura.getFacTotal()), x, y);
                y += yShift;
                double TotalVuelto = 0;
                double TotalPago = 0;
                if (pFactura.getFactTipoFact().equals("E")) {
                    for (Pago pagoList : pFactura.getPagoList()) {
                        TotalPago = TotalPago + pagoList.getPagMonto();
                    }
                    TotalVuelto = TotalPago - pFactura.getFacTotal();
                } 
                g2d.drawString("Vuelto: " + String.format("%.2f", TotalVuelto), x, y);
                y += yShift;
                g2d.drawString("-----------------------------------------", x, y);
                y += yShift;
                g2d.drawString("MUCHAS GRACIAS POR SU COMPRA ", x, y);
                y += yShift;
                g2d.drawString("TEL: 2736-1012 ", x, y);
                y += yShift;

                if (rImprecion == true) {
                    g2d.drawString("Impreción:" + pUsuario.getPersona().getPerNombre() + " " + pUsuario.getPersona().getPerPApellido(), x, y);
                    y += yShift;
                    g2d.drawString("Reimpreción de la Factura", x, y);
                    y += yShift;
                } else {
                    g2d.drawString("VENDEDOR:" + pUsuario.getPersona().getPerNombre() + " " + pUsuario.getPersona().getPerPApellido(), x, y);
                    y += yShift;
                }
                g2d.drawString("PRECIOS INCLUYEN IMPUESTO DE VENTA ", x, y);
                y += yShift;

                g2d.drawString("-----------------------------------------", x, y);
                y += yShift;
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
