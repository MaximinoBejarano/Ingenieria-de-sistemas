/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Johan
 */
@Entity
@Table(name = "tb_articulosxcompra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArticuloXCompra.findAll", query = "SELECT a FROM ArticuloXCompra a")
    , @NamedQuery(name = "ArticuloXCompra.findByArtCodigo", query = "SELECT a FROM ArticuloXCompra a WHERE a.artCodigo = :artCodigo")
    , @NamedQuery(name = "ArticuloXCompra.findByArtPrecio", query = "SELECT a FROM ArticuloXCompra a WHERE a.artPrecio = :artPrecio")
    , @NamedQuery(name = "ArticuloXCompra.findByArtCantidad", query = "SELECT a FROM ArticuloXCompra a WHERE a.artCantidad = :artCantidad")
    , @NamedQuery(name = "ArticuloXCompra.findByArtEstado", query = "SELECT a FROM ArticuloXCompra a WHERE a.artEstado = :artEstado")})
public class ArticuloXCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Art_Codigo")
    private Integer artCodigo;
    @Basic(optional = false)
    @Column(name = "Art_Precio")
    private double artPrecio;
    @Basic(optional = false)
    @Column(name = "Art_Cantidad")
    private int artCantidad;
    @Basic(optional = false)
    @Column(name = "Art_Estado")
    private String artEstado;
    @JoinColumn(name = "Art_Articulo", referencedColumnName = "Art_Codigo")
    @ManyToOne(optional = false)
    private Articulo artArticulo;
    @JoinColumn(name = "Art_Compra", referencedColumnName = "Com_Codigo")
    @ManyToOne(optional = false)
    private Compra artCompra;

    public ArticuloXCompra() {
    }

    public ArticuloXCompra(Integer artCodigo) {
        this.artCodigo = artCodigo;
    }

    public ArticuloXCompra(Integer artCodigo, double artPrecio, int artCantidad, String artEstado) {
        this.artCodigo = artCodigo;
        this.artPrecio = artPrecio;
        this.artCantidad = artCantidad;
        this.artEstado = artEstado;
    }

    public Integer getArtCodigo() {
        return artCodigo;
    }

    public void setArtCodigo(Integer artCodigo) {
        this.artCodigo = artCodigo;
    }

    public double getArtPrecio() {
        return artPrecio;
    }

    public void setArtPrecio(double artPrecio) {
        this.artPrecio = artPrecio;
    }

    public int getArtCantidad() {
        return artCantidad;
    }

    public void setArtCantidad(int artCantidad) {
        this.artCantidad = artCantidad;
    }

    public String getArtEstado() {
        return artEstado;
    }

    public void setArtEstado(String artEstado) {
        this.artEstado = artEstado;
    }

    public Articulo getArtArticulo() {
        return artArticulo;
    }

    public void setArtArticulo(Articulo artArticulo) {
        this.artArticulo = artArticulo;
    }

    public Compra getArtCompra() {
        return artCompra;
    }

    public void setArtCompra(Compra artCompra) {
        this.artCompra = artCompra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artCodigo != null ? artCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArticuloXCompra)) {
            return false;
        }
        ArticuloXCompra other = (ArticuloXCompra) object;
        if ((this.artCodigo == null && other.artCodigo != null) || (this.artCodigo != null && !this.artCodigo.equals(other.artCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.ArticuloXCompra[ artCodigo=" + artCodigo + " ]";
    }
    
}
