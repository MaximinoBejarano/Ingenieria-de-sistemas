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
@Table(name = "tb_tiposcontacto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoContacto.findAll", query = "SELECT t FROM TipoContacto t")
    , @NamedQuery(name = "TipoContacto.findByTipID", query = "SELECT t FROM TipoContacto t WHERE t.tipID = :tipID")
    , @NamedQuery(name = "TipoContacto.findByTipNombre", query = "SELECT t FROM TipoContacto t WHERE t.tipNombre = :tipNombre")
    , @NamedQuery(name = "TipoContacto.findByTipDescripcion", query = "SELECT t FROM TipoContacto t WHERE t.tipDescripcion = :tipDescripcion")})
public class TipoContacto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Tip_ID")
    private Integer tipID;
    @Basic(optional = false)
    @Column(name = "Tip_Nombre")
    private String tipNombre;
    @Basic(optional = false)
    @Column(name = "Tip_Descripcion")
    private String tipDescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conTipoContacto")
    private List<Contacto> contactoList;

    public TipoContacto() {
    }

    public TipoContacto(Integer tipID) {
        this.tipID = tipID;
    }

    public TipoContacto(Integer tipID, String tipNombre, String tipDescripcion) {
        this.tipID = tipID;
        this.tipNombre = tipNombre;
        this.tipDescripcion = tipDescripcion;
    }

    public Integer getTipID() {
        return tipID;
    }

    public void setTipID(Integer tipID) {
        this.tipID = tipID;
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
    public List<Contacto> getContactoList() {
        return contactoList;
    }

    public void setContactoList(List<Contacto> contactoList) {
        this.contactoList = contactoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipID != null ? tipID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoContacto)) {
            return false;
        }
        TipoContacto other = (TipoContacto) object;
        if ((this.tipID == null && other.tipID != null) || (this.tipID != null && !this.tipID.equals(other.tipID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.TipoContacto[ tipID=" + tipID + " ]";
    }
    
}
