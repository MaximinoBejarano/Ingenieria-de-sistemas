/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "tb_tipospago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPago.findAll", query = "SELECT t FROM TipoPago t")
    , @NamedQuery(name = "TipoPago.findByTipCodigo", query = "SELECT t FROM TipoPago t WHERE t.tipCodigo = :tipCodigo")
    , @NamedQuery(name = "TipoPago.findByTipNombre", query = "SELECT t FROM TipoPago t WHERE t.tipNombre = :tipNombre")
    , @NamedQuery(name = "TipoPago.findByTipDescripcion", query = "SELECT t FROM TipoPago t WHERE t.tipDescripcion = :tipDescripcion")})
public class TipoPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Tip_Codigo")
    private Integer tipCodigo;
    @Basic(optional = false)
    @Column(name = "Tip_Nombre")
    private String tipNombre;
    @Column(name = "Tip_Descripcion")
    private String tipDescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aboTipoPago")
    private List<Abono> abonoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pagTipoPago")
    private List<Pago> pagoList;

    public TipoPago() {
    }

    public TipoPago(Integer tipCodigo) {
        this.tipCodigo = tipCodigo;
    }

    public TipoPago(Integer tipCodigo, String tipNombre) {
        this.tipCodigo = tipCodigo;
        this.tipNombre = tipNombre;
    }

    public Integer getTipCodigo() {
        return tipCodigo;
    }

    public void setTipCodigo(Integer tipCodigo) {
        this.tipCodigo = tipCodigo;
    }

    public String getTipNombre() {
        return tipNombre;
    }

    public void setTipNombre(String tipNombre) {
        this.tipNombre = tipNombre;
    }

    public String getTipDescripcion() {
        return tipDescripcion;
    }

    public void setTipDescripcion(String tipDescripcion) {
        this.tipDescripcion = tipDescripcion;
    }

    @XmlTransient
    public List<Abono> getAbonoList() {
        return abonoList;
    }

    public void setAbonoList(List<Abono> abonoList) {
        this.abonoList = abonoList;
    }

    @XmlTransient
    public List<Pago> getPagoList() {
        return pagoList;
    }

    public void setPagoList(List<Pago> pagoList) {
        this.pagoList = pagoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipCodigo != null ? tipCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPago)) {
            return false;
        }
        TipoPago other = (TipoPago) object;
        if ((this.tipCodigo == null && other.tipCodigo != null) || (this.tipCodigo != null && !this.tipCodigo.equals(other.tipCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.TipoPago[ tipCodigo=" + tipCodigo + " ]";
    }
    
}
