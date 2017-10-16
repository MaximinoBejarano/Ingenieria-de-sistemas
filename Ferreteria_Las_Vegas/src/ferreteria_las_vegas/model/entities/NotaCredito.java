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
 *Entidad DB
 * @author Usuario
 */
@Entity
@Table(name = "tb_notascredito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotaCredito.findAll", query = "SELECT n FROM NotaCredito n")
    , @NamedQuery(name = "NotaCredito.findByNotCodigo", query = "SELECT n FROM NotaCredito n WHERE n.notCodigo = :notCodigo")
    , @NamedQuery(name = "NotaCredito.findByNotFecha", query = "SELECT n FROM NotaCredito n WHERE n.notFecha = :notFecha")
    , @NamedQuery(name = "NotaCredito.findByNotMonto", query = "SELECT n FROM NotaCredito n WHERE n.notMonto = :notMonto")
    , @NamedQuery(name = "NotaCredito.findByNotJustificacion", query = "SELECT n FROM NotaCredito n WHERE n.notJustificacion = :notJustificacion")})
public class NotaCredito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Not_Codigo")
    private Integer notCodigo;
    @Basic(optional = false)
    @Column(name = "Not_Fecha")
    @Temporal(TemporalType.DATE)
    private Date notFecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "Not_Monto")
    private BigDecimal notMonto;
    @Column(name = "Not_Justificacion")
    private String notJustificacion;
    @OneToMany(mappedBy = "pagNotaCredito")
    private List<Pago> pagoList;
    @JoinColumn(name = "Not_Factura", referencedColumnName = "Fac_Cliente")
    @ManyToOne(optional = false)
    private Factura notFactura;

    public NotaCredito() {
    }

    public NotaCredito(Integer notCodigo) {
        this.notCodigo = notCodigo;
    }

    public NotaCredito(Integer notCodigo, Date notFecha, BigDecimal notMonto) {
        this.notCodigo = notCodigo;
        this.notFecha = notFecha;
        this.notMonto = notMonto;
    }

    public Integer getNotCodigo() {
        return notCodigo;
    }

    public void setNotCodigo(Integer notCodigo) {
        this.notCodigo = notCodigo;
    }

    public Date getNotFecha() {
        return notFecha;
    }

    public void setNotFecha(Date notFecha) {
        this.notFecha = notFecha;
    }

    public BigDecimal getNotMonto() {
        return notMonto;
    }

    public void setNotMonto(BigDecimal notMonto) {
        this.notMonto = notMonto;
    }

    public String getNotJustificacion() {
        return notJustificacion;
    }

    public void setNotJustificacion(String notJustificacion) {
        this.notJustificacion = notJustificacion;
    }

    @XmlTransient
    public List<Pago> getPagoList() {
        return pagoList;
    }

    public void setPagoList(List<Pago> pagoList) {
        this.pagoList = pagoList;
    }

    public Factura getNotFactura() {
        return notFactura;
    }

    public void setNotFactura(Factura notFactura) {
        this.notFactura = notFactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notCodigo != null ? notCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotaCredito)) {
            return false;
        }
        NotaCredito other = (NotaCredito) object;
        if ((this.notCodigo == null && other.notCodigo != null) || (this.notCodigo != null && !this.notCodigo.equals(other.notCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.NotaCredito[ notCodigo=" + notCodigo + " ]";
    }
    
}
