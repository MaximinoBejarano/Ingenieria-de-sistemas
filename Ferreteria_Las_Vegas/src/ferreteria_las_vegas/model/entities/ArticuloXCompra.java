/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author Usuarios
 */
@Entity
@Table(name = "tb_articulosxcompra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArticuloXCompra.findAll", query = "SELECT a FROM ArticuloXCompra a")
    , @NamedQuery(name = "ArticuloXCompra.findByArtID", query = "SELECT a FROM ArticuloXCompra a WHERE a.artID = :artID")
    , @NamedQuery(name = "ArticuloXCompra.findByArtPrecio", query = "SELECT a FROM ArticuloXCompra a WHERE a.artPrecio = :artPrecio")
    , @NamedQuery(name = "ArticuloXCompra.findByArtCantidad", query = "SELECT a FROM ArticuloXCompra a WHERE a.artCantidad = :artCantidad")})
public class ArticuloXCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Art_ID")
    private Integer artID;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "Art_Precio")
    private BigDecimal artPrecio;
    @Basic(optional = false)
    @Column(name = "Art_Cantidad")
    private int artCantidad;
    @JoinColumn(name = "Art_Articulo", referencedColumnName = "Art_Codigo")
    @ManyToOne(optional = false)
    private Articulo artArticulo;
    @JoinColumn(name = "Art_Compra", referencedColumnName = "Com_Numero")
    @ManyToOne(optional = false)
    private Compra artCompra;

    public ArticuloXCompra() {
    }

    public ArticuloXCompra(Integer artID) {
        this.artID = artID;
    }

    public ArticuloXCompra(Integer artID, BigDecimal artPrecio, int artCantidad) {
        this.artID = artID;
        this.artPrecio = artPrecio;
        this.artCantidad = artCantidad;
    }

    public Integer getArtID() {
        return artID;
    }

    public void setArtID(Integer artID) {
        this.artID = artID;
    }

    public BigDecimal getArtPrecio() {
        return artPrecio;
    }

    public void setArtPrecio(BigDecimal artPrecio) {
        this.artPrecio = artPrecio;
    }

    public int getArtCantidad() {
        return artCantidad;
    }

    public void setArtCantidad(int artCantidad) {
        this.artCantidad = artCantidad;
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
        hash += (artID != null ? artID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArticuloXCompra)) {
            return false;
        }
        ArticuloXCompra other = (ArticuloXCompra) object;
        if ((this.artID == null && other.artID != null) || (this.artID != null && !this.artID.equals(other.artID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.ArticuloXCompra[ artID=" + artID + " ]";
    }
    
}
