/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.controller;

import ferreteria_las_vegas.model.entities.Articulo;
import ferreteria_las_vegas.model.entities.Inventario;

/**
 *
 * @author wili
 */
public class InventarioCompleto {
    private Articulo articulo;
    private Inventario inventario;

    public InventarioCompleto() {
    }
    
    public InventarioCompleto(Articulo articulo, Inventario inventario) {
        this.articulo = articulo;
        this.inventario = inventario;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
    
    
}
