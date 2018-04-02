/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.entities.Articulo;

/**
 *
 * @author wili
 */
public class InventarioCompleto {
    private Articulo articulo;
    private int cantArticulo;
    private double descuentoComercio;
    private double precioArt;

    public InventarioCompleto() {
    }

    public InventarioCompleto(Articulo articulo, int cantArticulo, double descuentoComercio, double precioArt) {
        this.articulo = articulo;
        this.cantArticulo = cantArticulo;
        this.descuentoComercio = descuentoComercio;
        this.precioArt = precioArt;
   
    }

   

    public double getDescuentoComercio() {
        return descuentoComercio;
    }

    public void setDescuentoComercio(double descuentoComercio) {
        this.descuentoComercio = descuentoComercio;
    }
    

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getCantArticulo() {
        return cantArticulo;
    }

    public void setCantArticulo(int cantArticulo) {
        this.cantArticulo = cantArticulo;
    }

    public double getPrecioArt() {
        return precioArt;
    }

    public void setPrecioArt(double precioArt) {
        this.precioArt = precioArt;
    }

    
}
