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
@Table(name = "tb_compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c")
    , @NamedQuery(name = "Compra.findByComCodigo", query = "SELECT c FROM Compra c WHERE c.comCodigo = :comCodigo")
    , @NamedQuery(name = "Compra.findByComFecha", query = "SELECT c FROM Compra c WHERE c.comFecha = :comFecha")
    , @NamedQuery(name = "Compra.findByComNombre", query = "SELECT c FROM Compra c WHERE c.comNombre = :comNombre")
    , @NamedQuery(name = "Compra.findByComTotal", query = "SELECT c FROM Compra c WHERE c.comTotal = :comTotal")
    , @NamedQuery(name = "Compra.findByComDescuento", query = "SELECT c FROM Compra c WHERE c.comDescuento = :comDescuento")
    , @NamedQuery(name = "Compra.findByComImpVenta", query = "SELECT c FROM Compra c WHERE c.comImpVenta = :comImpVenta")
    , @NamedQuery(name = "Compra.findByComFlete", query = "SELECT c FROM Compra c WHERE c.comFlete = :comFlete")
    , @NamedQuery(name = "Compra.findByComFleteC", query = "SELECT c FROM Compra c WHERE c.comFleteC = :comFleteC")
    , @NamedQuery(name = "Compra.findByComServCarga", query = "SELECT c FROM Compra c WHERE c.comServCarga = :comServCarga")
    , @NamedQuery(name = "Compra.findByComSubTotal", query = "SELECT c FROM Compra c WHERE c.comSubTotal = :comSubTotal")
    , @NamedQuery(name = "Compra.findByComTipoFact", query = "SELECT c FROM Compra c WHERE c.comTipoFact = :comTipoFact")
    , @NamedQuery(name = "Compra.findByComNumeroFact", query = "SELECT c FROM Compra c WHERE c.comNumeroFact = :comNumeroFact")
    , @NamedQuery(name = "Compra.findByComEstado", query = "SELECT c FROM Compra c WHERE c.comEstado = :comEstado")})
public class Compra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Com_Codigo")
    private Integer comCodigo;
    @Basic(optional = false)
    @Column(name = "Com_Fecha")
    @Temporal(TemporalType.DATE)
    private Date comFecha;
    @Column(name = "Com_Nombre")
    private String comNombre;
    @Basic(optional = false)
    @Column(name = "Com_Total")
    private double comTotal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Com_Descuento")
    private Double comDescuento;
    @Basic(optional = false)
    @Column(name = "Com_ImpVenta")
    private double comImpVenta;
    @Column(name = "Com_Flete")
    private Double comFlete;
    @Column(name = "Com_FleteC")
    private Double comFleteC;
    @Basic(optional = false)
    @Column(name = "Com_ServCarga")
    private double comServCarga;
    @Basic(optional = false)
    @Column(name = "Com_SubTotal")
    private double comSubTotal;
    @Basic(optional = false)
    @Column(name = "Com_TipoFact")
    private String comTipoFact;
    @Basic(optional = false)
    @Column(name = "Com_NumeroFact")
    private String comNumeroFact;
    @Basic(optional = false)
    @Column(name = "Com_Estado")
    private String comEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cueCompra")
    private List<CuentaXPagar> cuentaXPagarList;
    @JoinColumn(name = "Com_Proveedor", referencedColumnName = "Pro_Codigo")
    @ManyToOne(optional = false)
    private Proveedor comProveedor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artCompra")
    private List<ArticuloXCompra> articuloXCompraList;

    public Compra() {
    }

    public Compra(Integer comCodigo) {
        this.comCodigo = comCodigo;
    }

    public Compra(Integer comCodigo, Date comFecha, double comTotal, double comImpVenta, double comServCarga, double comSubTotal, String comTipoFact, String comNumeroFact, String comEstado) {
        this.comCodigo = comCodigo;
        this.comFecha = comFecha;
        this.comTotal = comTotal;
        this.comImpVenta = comImpVenta;
        this.comServCarga = comServCarga;
        this.comSubTotal = comSubTotal;
        this.comTipoFact = comTipoFact;
        this.comNumeroFact = comNumeroFact;
        this.comEstado = comEstado;
    }

    public Integer getComCodigo() {
        return comCodigo;
    }

    public void setComCodigo(Integer comCodigo) {
        this.comCodigo = comCodigo;
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

    public double getComTotal() {
        return comTotal;
    }

    public void setComTotal(double comTotal) {
        this.comTotal = comTotal;
    }

    public Double getComDescuento() {
        return comDescuento;
    }

    public void setComDescuento(Double comDescuento) {
        this.comDescuento = comDescuento;
    }

    public double getComImpVenta() {
        return comImpVenta;
    }

    public void setComImpVenta(double comImpVenta) {
        this.comImpVenta = comImpVenta;
    }

    public Double getComFlete() {
        return comFlete;
    }

    public void setComFlete(Double comFlete) {
        this.comFlete = comFlete;
    }

    public Double getComFleteC() {
        return comFleteC;
    }

    public void setComFleteC(Double comFleteC) {
        this.comFleteC = comFleteC;
    }

    public double getComServCarga() {
        return comServCarga;
    }

    public void setComServCarga(double comServCarga) {
        this.comServCarga = comServCarga;
    }

    public double getComSubTotal() {
        return comSubTotal;
    }

    public void setComSubTotal(double comSubTotal) {
        this.comSubTotal = comSubTotal;
    }

    public String getComTipoFact() {
        return comTipoFact;
    }

    public void setComTipoFact(String comTipoFact) {
        this.comTipoFact = comTipoFact;
    }

    public String getComNumeroFact() {
        return comNumeroFact;
    }

    public void setComNumeroFact(String comNumeroFact) {
        this.comNumeroFact = comNumeroFact;
    }

    public String getComEstado() {
        return comEstado;
    }

    public void setComEstado(String comEstado) {
        this.comEstado = comEstado;
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
        hash += (comCodigo != null ? comCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.comCodigo == null && other.comCodigo != null) || (this.comCodigo != null && !this.comCodigo.equals(other.comCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Compra[ comCodigo=" + comCodigo + " ]";
    }
    
}
