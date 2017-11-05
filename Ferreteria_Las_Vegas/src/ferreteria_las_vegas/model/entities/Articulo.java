/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "tb_articulos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulo.findAll", query = "SELECT a FROM Articulo a")
    , @NamedQuery(name = "Articulo.findByArtCodigo", query = "SELECT a FROM Articulo a WHERE a.artCodigo = :artCodigo")
    , @NamedQuery(name = "Articulo.findByArtNombre", query = "SELECT a FROM Articulo a WHERE a.artNombre = :artNombre")
    , @NamedQuery(name = "Articulo.findByArtDescripcion", query = "SELECT a FROM Articulo a WHERE a.artDescripcion = :artDescripcion")
    , @NamedQuery(name = "Articulo.findByArtMarca", query = "SELECT a FROM Articulo a WHERE a.artMarca = :artMarca")
    , @NamedQuery(name = "Articulo.findByArtUnidadMedida", query = "SELECT a FROM Articulo a WHERE a.artUnidadMedida = :artUnidadMedida")
    , @NamedQuery(name = "Articulo.findByArtPrecio", query = "SELECT a FROM Articulo a WHERE a.artPrecio = :artPrecio")
    , @NamedQuery(name = "Articulo.findByArtDescuento", query = "SELECT a FROM Articulo a WHERE a.artDescuento = :artDescuento")
    , @NamedQuery(name = "Articulo.findByArtEstadoDescuento", query = "SELECT a FROM Articulo a WHERE a.artEstadoDescuento = :artEstadoDescuento")
    , @NamedQuery(name = "Articulo.findByArtCodBarra", query = "SELECT a FROM Articulo a WHERE a.artCodBarra = :artCodBarra")
    , @NamedQuery(name = "Articulo.findByArtEstado", query = "SELECT a FROM Articulo a WHERE a.artEstado = :artEstado")})
public class Articulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Art_Codigo")
    private Integer artCodigo;
    @Basic(optional = false)
    @Column(name = "Art_Nombre")
    private String artNombre;
    @Basic(optional = false)
    @Column(name = "Art_Descripcion")
    private String artDescripcion;
    @Basic(optional = false)
    @Column(name = "Art_Marca")
    private String artMarca;
    @Basic(optional = false)
    @Column(name = "Art_UnidadMedida")
    private String artUnidadMedida;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "Art_Precio")
    private BigDecimal artPrecio;
    @Column(name = "Art_Descuento")
    private BigDecimal artDescuento;
    @Column(name = "Art_EstadoDescuento")
    private String artEstadoDescuento;
    @Column(name = "Art_CodBarra")
    private String artCodBarra;
    @Basic(optional = false)
    @Column(name = "Art_Estado")
    private String artEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invArticulo")
    private List<Inventario> inventarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artArticulo")
    private List<ArticuloXFactura> articuloXFacturaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artArticulo")
    private List<ArticuloXCompra> articuloXCompraList;

    public Articulo() {
    }

    public Articulo(Integer artCodigo) {
        this.artCodigo = artCodigo;
    }

    public Articulo(Integer artCodigo, String artNombre, String artDescripcion, String artMarca, String artUnidadMedida, BigDecimal artPrecio, String artEstado) {
        this.artCodigo = artCodigo;
        this.artNombre = artNombre;
        this.artDescripcion = artDescripcion;
        this.artMarca = artMarca;
        this.artUnidadMedida = artUnidadMedida;
        this.artPrecio = artPrecio;
        this.artEstado = artEstado;
    }

    public Integer getArtCodigo() {
        return artCodigo;
    }

    public void setArtCodigo(Integer artCodigo) {
        this.artCodigo = artCodigo;
    }

    public String getArtNombre() {
        return artNombre;
    }

    public void setArtNombre(String artNombre) {
        this.artNombre = artNombre;
    }

    public String getArtDescripcion() {
        return artDescripcion;
    }

    public void setArtDescripcion(String artDescripcion) {
        this.artDescripcion = artDescripcion;
    }

    public String getArtMarca() {
        return artMarca;
    }

    public void setArtMarca(String artMarca) {
        this.artMarca = artMarca;
    }

    public String getArtUnidadMedida() {
        return artUnidadMedida;
    }

    public void setArtUnidadMedida(String artUnidadMedida) {
        this.artUnidadMedida = artUnidadMedida;
    }

    public BigDecimal getArtPrecio() {
        return artPrecio;
    }

    public void setArtPrecio(BigDecimal artPrecio) {
        this.artPrecio = artPrecio;
    }

    public BigDecimal getArtDescuento() {
        return artDescuento;
    }

    public void setArtDescuento(BigDecimal artDescuento) {
        this.artDescuento = artDescuento;
    }

    public String getArtEstadoDescuento() {
        return artEstadoDescuento;
    }

    public void setArtEstadoDescuento(String artEstadoDescuento) {
        this.artEstadoDescuento = artEstadoDescuento;
    }

    public String getArtCodBarra() {
        return artCodBarra;
    }

    public void setArtCodBarra(String artCodBarra) {
        this.artCodBarra = artCodBarra;
    }

    public String getArtEstado() {
        return artEstado;
    }

    public void setArtEstado(String artEstado) {
        this.artEstado = artEstado;
    }

    @XmlTransient
    public List<Inventario> getInventarioList() {
        return inventarioList;
    }

    public void setInventarioList(List<Inventario> inventarioList) {
        this.inventarioList = inventarioList;
    }

    @XmlTransient
    public List<ArticuloXFactura> getArticuloXFacturaList() {
        return articuloXFacturaList;
    }

    public void setArticuloXFacturaList(List<ArticuloXFactura> articuloXFacturaList) {
        this.articuloXFacturaList = articuloXFacturaList;
    }

    @XmlTransient
    public List<ArticuloXCompra> getArticuloXCompraList() {
        return articuloXCompraList;
    }

    public void setArticuloXCompraList(List<ArticuloXCompra> articuloXCompraList) {
        this.articuloXCompraList = articuloXCompraList;
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
        if (!(object instanceof Articulo)) {
            return false;
        }
        Articulo other = (Articulo) object;
        if ((this.artCodigo == null && other.artCodigo != null) || (this.artCodigo != null && !this.artCodigo.equals(other.artCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Articulo[ artCodigo=" + artCodigo + " ]";
    }
    
}
