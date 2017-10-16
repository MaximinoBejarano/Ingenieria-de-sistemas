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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "tb_cuentasxcobrar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaXCobrar.findAll", query = "SELECT c FROM CuentaXCobrar c")
    , @NamedQuery(name = "CuentaXCobrar.findByCueCodigo", query = "SELECT c FROM CuentaXCobrar c WHERE c.cueCodigo = :cueCodigo")
    , @NamedQuery(name = "CuentaXCobrar.findByCueSaldo", query = "SELECT c FROM CuentaXCobrar c WHERE c.cueSaldo = :cueSaldo")
    , @NamedQuery(name = "CuentaXCobrar.findByCueSaldoFac", query = "SELECT c FROM CuentaXCobrar c WHERE c.cueSaldoFac = :cueSaldoFac")})
public class CuentaXCobrar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Cue_Codigo")
    private Integer cueCodigo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "Cue_Saldo")
    private BigDecimal cueSaldo;
    @Basic(optional = false)
    @Column(name = "Cue_SaldoFac")
    private BigDecimal cueSaldoFac;
    @ManyToMany(mappedBy = "cuentaXCobrarList")
    private List<Abono> abonoList;
    @JoinColumn(name = "Cue_Cliente", referencedColumnName = "Cli_Persona")
    @ManyToOne(optional = false)
    private Cliente cueCliente;
    @JoinColumn(name = "Cue_Factura", referencedColumnName = "Fac_Cliente")
    @ManyToOne(optional = false)
    private Factura cueFactura;

    public CuentaXCobrar() {
    }

    public CuentaXCobrar(Integer cueCodigo) {
        this.cueCodigo = cueCodigo;
    }

    public CuentaXCobrar(Integer cueCodigo, BigDecimal cueSaldo, BigDecimal cueSaldoFac) {
        this.cueCodigo = cueCodigo;
        this.cueSaldo = cueSaldo;
        this.cueSaldoFac = cueSaldoFac;
    }

    public Integer getCueCodigo() {
        return cueCodigo;
    }

    public void setCueCodigo(Integer cueCodigo) {
        this.cueCodigo = cueCodigo;
    }

    public BigDecimal getCueSaldo() {
        return cueSaldo;
    }

    public void setCueSaldo(BigDecimal cueSaldo) {
        this.cueSaldo = cueSaldo;
    }

    public BigDecimal getCueSaldoFac() {
        return cueSaldoFac;
    }

    public void setCueSaldoFac(BigDecimal cueSaldoFac) {
        this.cueSaldoFac = cueSaldoFac;
    }

    @XmlTransient
    public List<Abono> getAbonoList() {
        return abonoList;
    }

    public void setAbonoList(List<Abono> abonoList) {
        this.abonoList = abonoList;
    }

    public Cliente getCueCliente() {
        return cueCliente;
    }

    public void setCueCliente(Cliente cueCliente) {
        this.cueCliente = cueCliente;
    }

    public Factura getCueFactura() {
        return cueFactura;
    }

    public void setCueFactura(Factura cueFactura) {
        this.cueFactura = cueFactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cueCodigo != null ? cueCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuentaXCobrar)) {
            return false;
        }
        CuentaXCobrar other = (CuentaXCobrar) object;
        if ((this.cueCodigo == null && other.cueCodigo != null) || (this.cueCodigo != null && !this.cueCodigo.equals(other.cueCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.CuentaXCobrar[ cueCodigo=" + cueCodigo + " ]";
    }
    
}
