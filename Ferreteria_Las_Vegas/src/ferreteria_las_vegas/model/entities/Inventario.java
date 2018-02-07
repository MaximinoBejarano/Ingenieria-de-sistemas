/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Johan
 */
@Entity
@Table(name = "tb_inventarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventario.findAll", query = "SELECT i FROM Inventario i")
    , @NamedQuery(name = "Inventario.findByInvCodigo", query = "SELECT i FROM Inventario i WHERE i.invCodigo = :invCodigo")
    , @NamedQuery(name = "Inventario.findByInvFecha", query = "SELECT i FROM Inventario i WHERE i.invFecha = :invFecha")
    , @NamedQuery(name = "Inventario.findByInvCantidad", query = "SELECT i FROM Inventario i WHERE i.invCantidad = :invCantidad")
    , @NamedQuery(name = "Inventario.findByInvEstado", query = "SELECT i FROM Inventario i WHERE i.invEstado = :invEstado")})
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Inv_Codigo")
    private Integer invCodigo;
    @Basic(optional = false)
    @Column(name = "Inv_Fecha")
    @Temporal(TemporalType.DATE)
    private Date invFecha;
    @Basic(optional = false)
    @Column(name = "Inv_Cantidad")
    private int invCantidad;
    @Basic(optional = false)
    @Column(name = "Inv_Estado")
    private String invEstado;
    @JoinColumn(name = "Inv_Articulo", referencedColumnName = "Art_Codigo")
    @OneToOne(optional = false)
    private Articulo invArticulo;
    @JoinColumn(name = "Inv_Bodega", referencedColumnName = "Bod_Codigo")
    @ManyToOne
    private Bodega invBodega;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "detInventario")
    private List<DetalleInventario> detalleInventarioList;

    public Inventario() {
    }

    public Inventario(Integer invCodigo) {
        this.invCodigo = invCodigo;
    }

    public Inventario(Integer invCodigo, Date invFecha, int invCantidad, String invEstado) {
        this.invCodigo = invCodigo;
        this.invFecha = invFecha;
        this.invCantidad = invCantidad;
        this.invEstado = invEstado;
    }

    public Integer getInvCodigo() {
        return invCodigo;
    }

    public void setInvCodigo(Integer invCodigo) {
        this.invCodigo = invCodigo;
    }

    public Date getInvFecha() {
        return invFecha;
    }

    public void setInvFecha(Date invFecha) {
        this.invFecha = invFecha;
    }

    public int getInvCantidad() {
        return invCantidad;
    }

    public void setInvCantidad(int invCantidad) {
        this.invCantidad = invCantidad;
    }

    public String getInvEstado() {
        return invEstado;
    }

    public void setInvEstado(String invEstado) {
        this.invEstado = invEstado;
    }

    public Articulo getInvArticulo() {
        return invArticulo;
    }

    public void setInvArticulo(Articulo invArticulo) {
        this.invArticulo = invArticulo;
    }

    public Bodega getInvBodega() {
        return invBodega;
    }

    public void setInvBodega(Bodega invBodega) {
        this.invBodega = invBodega;
    }

    @XmlTransient
    public List<DetalleInventario> getDetalleInventarioList() {
        return detalleInventarioList;
    }

    public void setDetalleInventarioList(List<DetalleInventario> detalleInventarioList) {
        this.detalleInventarioList = detalleInventarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invCodigo != null ? invCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventario)) {
            return false;
        }
        Inventario other = (Inventario) object;
        if ((this.invCodigo == null && other.invCodigo != null) || (this.invCodigo != null && !this.invCodigo.equals(other.invCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Inventario[ invCodigo=" + invCodigo + " ]";
    }
    
}
