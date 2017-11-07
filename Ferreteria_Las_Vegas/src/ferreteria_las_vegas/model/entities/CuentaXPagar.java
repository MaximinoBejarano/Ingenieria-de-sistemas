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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * Entidad Mapeada
 */
@Entity
@Table(name = "tb_cuentasxpagar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuentaXPagar.findAll", query = "SELECT c FROM CuentaXPagar c")
    , @NamedQuery(name = "CuentaXPagar.findByCueCodigo", query = "SELECT c FROM CuentaXPagar c WHERE c.cueCodigo = :cueCodigo")
    , @NamedQuery(name = "CuentaXPagar.findByCueSaldo", query = "SELECT c FROM CuentaXPagar c WHERE c.cueSaldo = :cueSaldo")
    , @NamedQuery(name = "CuentaXPagar.findByCueSaldoCompra", query = "SELECT c FROM CuentaXPagar c WHERE c.cueSaldoCompra = :cueSaldoCompra")})
public class CuentaXPagar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Cue_Codigo")
    private Integer cueCodigo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Cue_Saldo")
    private BigDecimal cueSaldo;
    @Basic(optional = false)
    @Column(name = "Cue_SaldoCompra")
    private BigDecimal cueSaldoCompra;
    @ManyToMany(mappedBy = "cuentaXPagarList")
    private List<Abono> abonoList;
    @JoinColumn(name = "Cue_Compra", referencedColumnName = "Com_Numero")
    @ManyToOne(optional = false)
    private Compra cueCompra;
    @JoinColumn(name = "Cue_Proveedor", referencedColumnName = "Pro_ID")
    @ManyToOne(optional = false)
    private Proveedor cueProveedor;

    public CuentaXPagar() {
    }

    public CuentaXPagar(Integer cueCodigo) {
        this.cueCodigo = cueCodigo;
    }

    public CuentaXPagar(Integer cueCodigo, BigDecimal cueSaldoCompra) {
        this.cueCodigo = cueCodigo;
        this.cueSaldoCompra = cueSaldoCompra;
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

    public BigDecimal getCueSaldoCompra() {
        return cueSaldoCompra;
    }

    public void setCueSaldoCompra(BigDecimal cueSaldoCompra) {
        this.cueSaldoCompra = cueSaldoCompra;
    }

    @XmlTransient
    public List<Abono> getAbonoList() {
        return abonoList;
    }

    public void setAbonoList(List<Abono> abonoList) {
        this.abonoList = abonoList;
    }

    public Compra getCueCompra() {
        return cueCompra;
    }

    public void setCueCompra(Compra cueCompra) {
        this.cueCompra = cueCompra;
    }

    public Proveedor getCueProveedor() {
        return cueProveedor;
    }

    public void setCueProveedor(Proveedor cueProveedor) {
        this.cueProveedor = cueProveedor;
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
        if (!(object instanceof CuentaXPagar)) {
            return false;
        }
        CuentaXPagar other = (CuentaXPagar) object;
        if ((this.cueCodigo == null && other.cueCodigo != null) || (this.cueCodigo != null && !this.cueCodigo.equals(other.cueCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.CuentaXPagar[ cueCodigo=" + cueCodigo + " ]";
    }
    
}
