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
@Table(name = "tb_facturas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f")
    , @NamedQuery(name = "Factura.findByFacCodigo", query = "SELECT f FROM Factura f WHERE f.facCodigo = :facCodigo")
    , @NamedQuery(name = "Factura.findByFacFecha", query = "SELECT f FROM Factura f WHERE f.facFecha = :facFecha")
    , @NamedQuery(name = "Factura.findByFatSubtotal", query = "SELECT f FROM Factura f WHERE f.fatSubtotal = :fatSubtotal")
    , @NamedQuery(name = "Factura.findByFacDescuento", query = "SELECT f FROM Factura f WHERE f.facDescuento = :facDescuento")
    , @NamedQuery(name = "Factura.findByFacTotal", query = "SELECT f FROM Factura f WHERE f.facTotal = :facTotal")
    , @NamedQuery(name = "Factura.findByFacImpVentas", query = "SELECT f FROM Factura f WHERE f.facImpVentas = :facImpVentas")
    , @NamedQuery(name = "Factura.findByFactTipoFact", query = "SELECT f FROM Factura f WHERE f.factTipoFact = :factTipoFact")
    , @NamedQuery(name = "Factura.findByFactEstadoPago", query = "SELECT f FROM Factura f WHERE f.factEstadoPago = :factEstadoPago")
    , @NamedQuery(name = "Factura.findByFacEstado", query = "SELECT f FROM Factura f WHERE f.facEstado = :facEstado")})
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Fac_Codigo")
    private Integer facCodigo;
    @Basic(optional = false)
    @Column(name = "Fac_Fecha")
    @Temporal(TemporalType.DATE)
    private Date facFecha;
    @Basic(optional = false)
    @Column(name = "Fat_Subtotal")
    private double fatSubtotal;
    @Basic(optional = false)
    @Column(name = "Fac_Descuento")
    private double facDescuento;
    @Basic(optional = false)
    @Column(name = "Fac_Total")
    private double facTotal;
    @Basic(optional = false)
    @Column(name = "Fac_ImpVentas")
    private double facImpVentas;
    @Basic(optional = false)
    @Column(name = "Fact_TipoFact")
    private String factTipoFact;
    @Basic(optional = false)
    @Column(name = "Fact_EstadoPago")
    private String factEstadoPago;
    @Basic(optional = false)
    @Column(name = "Fac_Estado")
    private String facEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cueFactura")
    private List<CuentaXCobrar> cuentaXCobrarList;
    @JoinColumn(name = "Fac_Cliente", referencedColumnName = "Cli_Persona")
    @ManyToOne(optional = false)
    private Cliente facCliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artFactura")
    private List<ArticuloXFactura> articuloXFacturaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pagFactura")
    private List<Pago> pagoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notFactura")
    private List<NotaCredito> notaCreditoList;

    public Factura() {
    }

    public Factura(Integer facCodigo) {
        this.facCodigo = facCodigo;
    }

    public Factura(Integer facCodigo, Date facFecha, double fatSubtotal, double facDescuento, double facTotal, double facImpVentas, String factTipoFact, String factEstadoPago, String facEstado) {
        this.facCodigo = facCodigo;
        this.facFecha = facFecha;
        this.fatSubtotal = fatSubtotal;
        this.facDescuento = facDescuento;
        this.facTotal = facTotal;
        this.facImpVentas = facImpVentas;
        this.factTipoFact = factTipoFact;
        this.factEstadoPago = factEstadoPago;
        this.facEstado = facEstado;
    }

    public Integer getFacCodigo() {
        return facCodigo;
    }

    public void setFacCodigo(Integer facCodigo) {
        this.facCodigo = facCodigo;
    }

    public Date getFacFecha() {
        return facFecha;
    }

    public void setFacFecha(Date facFecha) {
        this.facFecha = facFecha;
    }

    public double getFatSubtotal() {
        return fatSubtotal;
    }

    public void setFatSubtotal(double fatSubtotal) {
        this.fatSubtotal = fatSubtotal;
    }

    public double getFacDescuento() {
        return facDescuento;
    }

    public void setFacDescuento(double facDescuento) {
        this.facDescuento = facDescuento;
    }

    public double getFacTotal() {
        return facTotal;
    }

    public void setFacTotal(double facTotal) {
        this.facTotal = facTotal;
    }

    public double getFacImpVentas() {
        return facImpVentas;
    }

    public void setFacImpVentas(double facImpVentas) {
        this.facImpVentas = facImpVentas;
    }

    public String getFactTipoFact() {
        return factTipoFact;
    }

    public void setFactTipoFact(String factTipoFact) {
        this.factTipoFact = factTipoFact;
    }

    public String getFactEstadoPago() {
        return factEstadoPago;
    }

    public void setFactEstadoPago(String factEstadoPago) {
        this.factEstadoPago = factEstadoPago;
    }

    public String getFacEstado() {
        return facEstado;
    }

    public void setFacEstado(String facEstado) {
        this.facEstado = facEstado;
    }

    @XmlTransient
    public List<CuentaXCobrar> getCuentaXCobrarList() {
        return cuentaXCobrarList;
    }

    public void setCuentaXCobrarList(List<CuentaXCobrar> cuentaXCobrarList) {
        this.cuentaXCobrarList = cuentaXCobrarList;
    }

    public Cliente getFacCliente() {
        return facCliente;
    }

    public void setFacCliente(Cliente facCliente) {
        this.facCliente = facCliente;
    }

    @XmlTransient
    public List<ArticuloXFactura> getArticuloXFacturaList() {
        return articuloXFacturaList;
    }

    public void setArticuloXFacturaList(List<ArticuloXFactura> articuloXFacturaList) {
        this.articuloXFacturaList = articuloXFacturaList;
    }

    @XmlTransient
    public List<Pago> getPagoList() {
        return pagoList;
    }

    public void setPagoList(List<Pago> pagoList) {
        this.pagoList = pagoList;
    }

    @XmlTransient
    public List<NotaCredito> getNotaCreditoList() {
        return notaCreditoList;
    }

    public void setNotaCreditoList(List<NotaCredito> notaCreditoList) {
        this.notaCreditoList = notaCreditoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facCodigo != null ? facCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.facCodigo == null && other.facCodigo != null) || (this.facCodigo != null && !this.facCodigo.equals(other.facCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Factura[ facCodigo=" + facCodigo + " ]";
    }
    
}
