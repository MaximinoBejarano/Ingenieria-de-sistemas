/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *Entidad DB
 * @author Usuario
 */
@Entity
@Table(name = "tb_compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c")
    , @NamedQuery(name = "Compra.findByComNumero", query = "SELECT c FROM Compra c WHERE c.comNumero = :comNumero")
    , @NamedQuery(name = "Compra.findByComFecha", query = "SELECT c FROM Compra c WHERE c.comFecha = :comFecha")
    , @NamedQuery(name = "Compra.findByComNombre", query = "SELECT c FROM Compra c WHERE c.comNombre = :comNombre")
    , @NamedQuery(name = "Compra.findByComTotal", query = "SELECT c FROM Compra c WHERE c.comTotal = :comTotal")
    , @NamedQuery(name = "Compra.findByComDescuento", query = "SELECT c FROM Compra c WHERE c.comDescuento = :comDescuento")
    , @NamedQuery(name = "Compra.findByComImpVenta", query = "SELECT c FROM Compra c WHERE c.comImpVenta = :comImpVenta")
    , @NamedQuery(name = "Compra.findByComFlete", query = "SELECT c FROM Compra c WHERE c.comFlete = :comFlete")
    , @NamedQuery(name = "Compra.findByComFleteC", query = "SELECT c FROM Compra c WHERE c.comFleteC = :comFleteC")
    , @NamedQuery(name = "Compra.findByComServCarga", query = "SELECT c FROM Compra c WHERE c.comServCarga = :comServCarga")
    , @NamedQuery(name = "Compra.findByComSubTotal", query = "SELECT c FROM Compra c WHERE c.comSubTotal = :comSubTotal")})
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Com_Numero")
    private String comNumero;
    @Basic(optional = false)
    @Column(name = "Com_Fecha")
    @Temporal(TemporalType.DATE)
    private Date comFecha;
    @Column(name = "Com_Nombre")
    private String comNombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "Com_Total")
    private BigDecimal comTotal;
    @Column(name = "Com_Descuento")
    private BigDecimal comDescuento;
    @Basic(optional = false)
    @Column(name = "Com_ImpVenta")
    private BigDecimal comImpVenta;
    @Column(name = "Com_Flete")
    private BigDecimal comFlete;
    @Column(name = "Com_FleteC")
    private BigDecimal comFleteC;
    @Basic(optional = false)
    @Column(name = "Com_ServCarga")
    private BigDecimal comServCarga;
    @Basic(optional = false)
    @Column(name = "Com_SubTotal")
    private String comSubTotal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cueCompra")
    private List<CuentaXPagar> cuentaXPagarList;
    @JoinColumn(name = "Com_Proveedor", referencedColumnName = "Pro_ID")
    @ManyToOne(optional = false)
    private Proveedor comProveedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artCompra")
    private List<ArticuloXCompra> articuloXCompraList;

    public Compra() {
    }

    public Compra(String comNumero) {
        this.comNumero = comNumero;
    }

    public Compra(String comNumero, Date comFecha, BigDecimal comTotal, BigDecimal comImpVenta, BigDecimal comServCarga, String comSubTotal) {
        this.comNumero = comNumero;
        this.comFecha = comFecha;
        this.comTotal = comTotal;
        this.comImpVenta = comImpVenta;
        this.comServCarga = comServCarga;
        this.comSubTotal = comSubTotal;
    }

    public String getComNumero() {
        return comNumero;
    }

    public void setComNumero(String comNumero) {
        this.comNumero = comNumero;
    }

    public Date getComFecha() {
        return comFecha;
    }

    public void setComFecha(Date comFecha) {
        this.comFecha = comFecha;
    }

    public String getComNombre() {
        return comNombre;
    }

    public void setComNombre(String comNombre) {
        this.comNombre = comNombre;
    }

    public BigDecimal getComTotal() {
        return comTotal;
    }

    public void setComTotal(BigDecimal comTotal) {
        this.comTotal = comTotal;
    }

    public BigDecimal getComDescuento() {
        return comDescuento;
    }

    public void setComDescuento(BigDecimal comDescuento) {
        this.comDescuento = comDescuento;
    }

    public BigDecimal getComImpVenta() {
        return comImpVenta;
    }

    public void setComImpVenta(BigDecimal comImpVenta) {
        this.comImpVenta = comImpVenta;
    }

    public BigDecimal getComFlete() {
        return comFlete;
    }

    public void setComFlete(BigDecimal comFlete) {
        this.comFlete = comFlete;
    }

    public BigDecimal getComFleteC() {
        return comFleteC;
    }

    public void setComFleteC(BigDecimal comFleteC) {
        this.comFleteC = comFleteC;
    }

    public BigDecimal getComServCarga() {
        return comServCarga;
    }

    public void setComServCarga(BigDecimal comServCarga) {
        this.comServCarga = comServCarga;
    }

    public String getComSubTotal() {
        return comSubTotal;
    }

    public void setComSubTotal(String comSubTotal) {
        this.comSubTotal = comSubTotal;
    }

    @XmlTransient
    public List<CuentaXPagar> getCuentaXPagarList() {
        return cuentaXPagarList;
    }

    public void setCuentaXPagarList(List<CuentaXPagar> cuentaXPagarList) {
        this.cuentaXPagarList = cuentaXPagarList;
    }

    public Proveedor getComProveedor() {
        return comProveedor;
    }

    public void setComProveedor(Proveedor comProveedor) {
        this.comProveedor = comProveedor;
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
        hash += (comNumero != null ? comNumero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.comNumero == null && other.comNumero != null) || (this.comNumero != null && !this.comNumero.equals(other.comNumero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Compra[ comNumero=" + comNumero + " ]";
    }
    
}
