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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Johan
 */
@Entity
@Table(name = "tb_proveedores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p")
    , @NamedQuery(name = "Proveedor.findByProCodigo", query = "SELECT p FROM Proveedor p WHERE p.proCodigo = :proCodigo")
    , @NamedQuery(name = "Proveedor.findByProNombre", query = "SELECT p FROM Proveedor p WHERE p.proNombre = :proNombre")
    , @NamedQuery(name = "Proveedor.findByProCedulaJuridica", query = "SELECT p FROM Proveedor p WHERE p.proCedulaJuridica = :proCedulaJuridica")
    , @NamedQuery(name = "Proveedor.findByProEstado", query = "SELECT p FROM Proveedor p WHERE p.proEstado = :proEstado")})
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Pro_Codigo")
    private Integer proCodigo;
    @Basic(optional = false)
    @Column(name = "Pro_Nombre")
    private String proNombre;
    @Basic(optional = false)
    @Column(name = "Pro_CedulaJuridica")
    private String proCedulaJuridica;
    @Basic(optional = false)
    @Column(name = "Pro_Estado")
    private String proEstado;
    @ManyToMany(mappedBy = "proveedorList")
    private List<Contacto> contactoList;
    @ManyToMany(mappedBy = "proveedorList")
    private List<Direccion> direccionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cueProveedor")
    private List<CuentaXPagar> cuentaXPagarList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comProveedor")
    private List<Compra> compraList;

    public Proveedor() {
    }

    public Proveedor(Integer proCodigo) {
        this.proCodigo = proCodigo;
    }

    public Proveedor(Integer proCodigo, String proNombre, String proCedulaJuridica, String proEstado) {
        this.proCodigo = proCodigo;
        this.proNombre = proNombre;
        this.proCedulaJuridica = proCedulaJuridica;
        this.proEstado = proEstado;
    }

    public Integer getProCodigo() {
        return proCodigo;
    }

    public void setProCodigo(Integer proCodigo) {
        this.proCodigo = proCodigo;
    }

    public String getProNombre() {
        return proNombre;
    }

    public void setProNombre(String proNombre) {
        this.proNombre = proNombre;
    }

    public String getProCedulaJuridica() {
        return proCedulaJuridica;
    }

    public void setProCedulaJuridica(String proCedulaJuridica) {
        this.proCedulaJuridica = proCedulaJuridica;
    }

    public String getProEstado() {
        return proEstado;
    }

    public void setProEstado(String proEstado) {
        this.proEstado = proEstado;
    }

    @XmlTransient
    public List<Contacto> getContactoList() {
        return contactoList;
    }

    public void setContactoList(List<Contacto> contactoList) {
        this.contactoList = contactoList;
    }

    @XmlTransient
    public List<Direccion> getDireccionList() {
        return direccionList;
    }

    public void setDireccionList(List<Direccion> direccionList) {
        this.direccionList = direccionList;
    }

    @XmlTransient
    public List<CuentaXPagar> getCuentaXPagarList() {
        return cuentaXPagarList;
    }

    public void setCuentaXPagarList(List<CuentaXPagar> cuentaXPagarList) {
        this.cuentaXPagarList = cuentaXPagarList;
    }

    @XmlTransient
    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proCodigo != null ? proCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.proCodigo == null && other.proCodigo != null) || (this.proCodigo != null && !this.proCodigo.equals(other.proCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Proveedor[ proCodigo=" + proCodigo + " ]";
    }
    
}
