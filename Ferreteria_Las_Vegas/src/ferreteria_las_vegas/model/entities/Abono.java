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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "tb_abonos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Abono.findAll", query = "SELECT a FROM Abono a")
    , @NamedQuery(name = "Abono.findByAboCodigo", query = "SELECT a FROM Abono a WHERE a.aboCodigo = :aboCodigo")
    , @NamedQuery(name = "Abono.findByAboFecha", query = "SELECT a FROM Abono a WHERE a.aboFecha = :aboFecha")
    , @NamedQuery(name = "Abono.findByAboMonto", query = "SELECT a FROM Abono a WHERE a.aboMonto = :aboMonto")
    , @NamedQuery(name = "Abono.findByAboNumeroDeposito", query = "SELECT a FROM Abono a WHERE a.aboNumeroDeposito = :aboNumeroDeposito")
    , @NamedQuery(name = "Abono.findByAboEstado", query = "SELECT a FROM Abono a WHERE a.aboEstado = :aboEstado")})
public class Abono implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Abo_Codigo")
    private Integer aboCodigo;
    @Basic(optional = false)
    @Column(name = "Abo_Fecha")
    @Temporal(TemporalType.DATE)
    private Date aboFecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "Abo_Monto")
    private BigDecimal aboMonto;
    @Column(name = "Abo_NumeroDeposito")
    private String aboNumeroDeposito;
    @Basic(optional = false)
    @Column(name = "Abo_Estado")
    private String aboEstado;
    @JoinTable(name = "tb_cxp_abonos", joinColumns = {
        @JoinColumn(name = "RCA_Abono", referencedColumnName = "Abo_Codigo")}, inverseJoinColumns = {
        @JoinColumn(name = "RCA_CuentaPagar", referencedColumnName = "Cue_Codigo")})
    @ManyToMany
    private List<CuentaXPagar> cuentaXPagarList;
    @JoinTable(name = "tb_cxc_abonos", joinColumns = {
        @JoinColumn(name = "RCA_Abono", referencedColumnName = "Abo_Codigo")}, inverseJoinColumns = {
        @JoinColumn(name = "RCA_CuentaCobrar", referencedColumnName = "Cue_Codigo")})
    @ManyToMany
    private List<CuentaXCobrar> cuentaXCobrarList;
    @JoinColumn(name = "Abo_TipoPago", referencedColumnName = "Tip_Codigo")
    @ManyToOne(optional = false)
    private TipoPago aboTipoPago;

    public Abono() {
    }

    public Abono(Integer aboCodigo) {
        this.aboCodigo = aboCodigo;
    }

    public Abono(Integer aboCodigo, Date aboFecha, BigDecimal aboMonto, String aboEstado) {
        this.aboCodigo = aboCodigo;
        this.aboFecha = aboFecha;
        this.aboMonto = aboMonto;
        this.aboEstado = aboEstado;
    }

    public Integer getAboCodigo() {
        return aboCodigo;
    }

    public void setAboCodigo(Integer aboCodigo) {
        this.aboCodigo = aboCodigo;
    }

    public Date getAboFecha() {
        return aboFecha;
    }

    public void setAboFecha(Date aboFecha) {
        this.aboFecha = aboFecha;
    }

    public BigDecimal getAboMonto() {
        return aboMonto;
    }

    public void setAboMonto(BigDecimal aboMonto) {
        this.aboMonto = aboMonto;
    }

    public String getAboNumeroDeposito() {
        return aboNumeroDeposito;
    }

    public void setAboNumeroDeposito(String aboNumeroDeposito) {
        this.aboNumeroDeposito = aboNumeroDeposito;
    }

    public String getAboEstado() {
        return aboEstado;
    }

    public void setAboEstado(String aboEstado) {
        this.aboEstado = aboEstado;
    }

    @XmlTransient
    public List<CuentaXPagar> getCuentaXPagarList() {
        return cuentaXPagarList;
    }

    public void setCuentaXPagarList(List<CuentaXPagar> cuentaXPagarList) {
        this.cuentaXPagarList = cuentaXPagarList;
    }

    @XmlTransient
    public List<CuentaXCobrar> getCuentaXCobrarList() {
        return cuentaXCobrarList;
    }

    public void setCuentaXCobrarList(List<CuentaXCobrar> cuentaXCobrarList) {
        this.cuentaXCobrarList = cuentaXCobrarList;
    }

    public TipoPago getAboTipoPago() {
        return aboTipoPago;
    }

    public void setAboTipoPago(TipoPago aboTipoPago) {
        this.aboTipoPago = aboTipoPago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aboCodigo != null ? aboCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Abono)) {
            return false;
        }
        Abono other = (Abono) object;
        if ((this.aboCodigo == null && other.aboCodigo != null) || (this.aboCodigo != null && !this.aboCodigo.equals(other.aboCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Abono[ aboCodigo=" + aboCodigo + " ]";
    }
    
}
