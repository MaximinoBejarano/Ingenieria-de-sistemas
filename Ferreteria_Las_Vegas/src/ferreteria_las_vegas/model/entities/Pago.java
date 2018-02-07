/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Johan
 */
@Entity
@Table(name = "tb_pagos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p")
    , @NamedQuery(name = "Pago.findByPagCodigo", query = "SELECT p FROM Pago p WHERE p.pagCodigo = :pagCodigo")
    , @NamedQuery(name = "Pago.findByPagMonto", query = "SELECT p FROM Pago p WHERE p.pagMonto = :pagMonto")
    , @NamedQuery(name = "Pago.findByPagTipoMoneda", query = "SELECT p FROM Pago p WHERE p.pagTipoMoneda = :pagTipoMoneda")
    , @NamedQuery(name = "Pago.findByPagTipoCambio", query = "SELECT p FROM Pago p WHERE p.pagTipoCambio = :pagTipoCambio")
    , @NamedQuery(name = "Pago.findByPagEstado", query = "SELECT p FROM Pago p WHERE p.pagEstado = :pagEstado")})
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Pag_Codigo")
    private Integer pagCodigo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Pag_Monto")
    private Double pagMonto;
    @Basic(optional = false)
    @Column(name = "Pag_TipoMoneda")
    private String pagTipoMoneda;
    @Column(name = "Pag_TipoCambio")
    private Double pagTipoCambio;
    @Basic(optional = false)
    @Column(name = "Pag_Estado")
    private String pagEstado;
    @JoinColumn(name = "Pag_NotaCredito", referencedColumnName = "Not_Codigo")
    @ManyToOne
    private NotaCredito pagNotaCredito;
    @JoinColumn(name = "Pag_TipoPago", referencedColumnName = "Tip_Codigo")
    @ManyToOne(optional = false)
    private TipoPago pagTipoPago;
    @JoinColumn(name = "Pag_Factura", referencedColumnName = "Fac_Codigo")
    @ManyToOne(optional = false)
    private Factura pagFactura;

    public Pago() {
    }

    public Pago(Integer pagCodigo) {
        this.pagCodigo = pagCodigo;
    }

    public Pago(Integer pagCodigo, String pagTipoMoneda, String pagEstado) {
        this.pagCodigo = pagCodigo;
        this.pagTipoMoneda = pagTipoMoneda;
        this.pagEstado = pagEstado;
    }

    public Integer getPagCodigo() {
        return pagCodigo;
    }

    public void setPagCodigo(Integer pagCodigo) {
        this.pagCodigo = pagCodigo;
    }

    public Double getPagMonto() {
        return pagMonto;
    }

    public void setPagMonto(Double pagMonto) {
        this.pagMonto = pagMonto;
    }

    public String getPagTipoMoneda() {
        return pagTipoMoneda;
    }

    public void setPagTipoMoneda(String pagTipoMoneda) {
        this.pagTipoMoneda = pagTipoMoneda;
    }

    public Double getPagTipoCambio() {
        return pagTipoCambio;
    }

    public void setPagTipoCambio(Double pagTipoCambio) {
        this.pagTipoCambio = pagTipoCambio;
    }

    public String getPagEstado() {
        return pagEstado;
    }

    public void setPagEstado(String pagEstado) {
        this.pagEstado = pagEstado;
    }

    public NotaCredito getPagNotaCredito() {
        return pagNotaCredito;
    }

    public void setPagNotaCredito(NotaCredito pagNotaCredito) {
        this.pagNotaCredito = pagNotaCredito;
    }

    public TipoPago getPagTipoPago() {
        return pagTipoPago;
    }

    public void setPagTipoPago(TipoPago pagTipoPago) {
        this.pagTipoPago = pagTipoPago;
    }

    public Factura getPagFactura() {
        return pagFactura;
    }

    public void setPagFactura(Factura pagFactura) {
        this.pagFactura = pagFactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pagCodigo != null ? pagCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.pagCodigo == null && other.pagCodigo != null) || (this.pagCodigo != null && !this.pagCodigo.equals(other.pagCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Pago[ pagCodigo=" + pagCodigo + " ]";
    }
    
}
