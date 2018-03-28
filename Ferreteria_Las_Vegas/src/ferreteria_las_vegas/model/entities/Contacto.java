/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Johan
 */
@Entity
@Table(name = "tb_contactos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contacto.findAll", query = "SELECT c FROM Contacto c")
    , @NamedQuery(name = "Contacto.findByConCodigo", query = "SELECT c FROM Contacto c WHERE c.conCodigo = :conCodigo")
    , @NamedQuery(name = "Contacto.findByConContacto", query = "SELECT c FROM Contacto c WHERE c.conContacto = :conContacto")
    , @NamedQuery(name = "Contacto.findByConTipoContacto", query = "SELECT c FROM Contacto c WHERE c.conTipoContacto = :conTipoContacto")})
public class Contacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Con_Codigo")
    private Integer conCodigo;
    @Basic(optional = false)
    @Column(name = "Con_Contacto")
    private String conContacto;
    @Basic(optional = false)
    @Column(name = "Con_TipoContacto")
    private String conTipoContacto;
    @ManyToMany(mappedBy = "contactoList")
    private List<Persona> personaList;    
    @ManyToMany(mappedBy = "contactoList")
    private List<Proveedor> proveedorList;

    public Contacto() {
    }

    public Contacto(Integer conCodigo) {
        this.conCodigo = conCodigo;
    }

    public Contacto(Integer conCodigo, String conContacto, String conTipoContacto) {
        this.conCodigo = conCodigo;
        this.conContacto = conContacto;
        this.conTipoContacto = conTipoContacto;
    }

    public Integer getConCodigo() {
        return conCodigo;
    }

    public void setConCodigo(Integer conCodigo) {
        this.conCodigo = conCodigo;
    }

    public String getConContacto() {
        return conContacto;
    }

    public void setConContacto(String conContacto) {
        this.conContacto = conContacto;
    }

    public String getConTipoContacto() {
        return conTipoContacto;
    }

    public void setConTipoContacto(String conTipoContacto) {
        this.conTipoContacto = conTipoContacto;
    }

    @XmlTransient
    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
    }

    @XmlTransient
    public List<Proveedor> getProveedorList() {
        return proveedorList;
    }

    public void setProveedorList(List<Proveedor> proveedorList) {
        this.proveedorList = proveedorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conCodigo != null ? conCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contacto)) {
            return false;
        }
        Contacto other = (Contacto) object;
        if ((this.conCodigo == null && other.conCodigo != null) || (this.conCodigo != null && !this.conCodigo.equals(other.conCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Contacto[ conCodigo=" + conCodigo + " ]";
    }
    
}
