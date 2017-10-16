/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "tb_detalles_inventario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleInventario.findAll", query = "SELECT d FROM DetalleInventario d")
    , @NamedQuery(name = "DetalleInventario.findByDetCodigo", query = "SELECT d FROM DetalleInventario d WHERE d.detCodigo = :detCodigo")
    , @NamedQuery(name = "DetalleInventario.findByDetFecha", query = "SELECT d FROM DetalleInventario d WHERE d.detFecha = :detFecha")
    , @NamedQuery(name = "DetalleInventario.findByDetCantidad", query = "SELECT d FROM DetalleInventario d WHERE d.detCantidad = :detCantidad")
    , @NamedQuery(name = "DetalleInventario.findByDetPrecio", query = "SELECT d FROM DetalleInventario d WHERE d.detPrecio = :detPrecio")})
public class DetalleInventario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Det_Codigo")
    private Integer detCodigo;
    @Basic(optional = false)
    @Column(name = "Det_Fecha")
    @Temporal(TemporalType.DATE)
    private Date detFecha;
    @Basic(optional = false)
    @Column(name = "Det_Cantidad")
    private int detCantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "Det_Precio")
    private BigDecimal detPrecio;
    @JoinColumn(name = "Det_Inventario", referencedColumnName = "Inv_Codigo")
    @ManyToOne(optional = false)
    private Inventario detInventario;

    public DetalleInventario() {
    }

    public DetalleInventario(Integer detCodigo) {
        this.detCodigo = detCodigo;
    }

    public DetalleInventario(Integer detCodigo, Date detFecha, int detCantidad, BigDecimal detPrecio) {
        this.detCodigo = detCodigo;
        this.detFecha = detFecha;
        this.detCantidad = detCantidad;
        this.detPrecio = detPrecio;
    }

    public Integer getDetCodigo() {
        return detCodigo;
    }

    public void setDetCodigo(Integer detCodigo) {
        this.detCodigo = detCodigo;
    }

    public Date getDetFecha() {
        return detFecha;
    }

    public void setDetFecha(Date detFecha) {
        this.detFecha = detFecha;
    }

    public int getDetCantidad() {
        return detCantidad;
    }

    public void setDetCantidad(int detCantidad) {
        this.detCantidad = detCantidad;
    }

    public BigDecimal getDetPrecio() {
        return detPrecio;
    }

    public void setDetPrecio(BigDecimal detPrecio) {
        this.detPrecio = detPrecio;
    }

    public Inventario getDetInventario() {
        return detInventario;
    }

    public void setDetInventario(Inventario detInventario) {
        this.detInventario = detInventario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detCodigo != null ? detCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleInventario)) {
            return false;
        }
        DetalleInventario other = (DetalleInventario) object;
        if ((this.detCodigo == null && other.detCodigo != null) || (this.detCodigo != null && !this.detCodigo.equals(other.detCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.DetalleInventario[ detCodigo=" + detCodigo + " ]";
    }
    
}
