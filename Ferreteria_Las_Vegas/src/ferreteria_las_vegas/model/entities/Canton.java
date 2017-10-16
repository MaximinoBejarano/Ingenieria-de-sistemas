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
@Table(name = "tb_cantones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Canton.findAll", query = "SELECT c FROM Canton c")
    , @NamedQuery(name = "Canton.findByCanID", query = "SELECT c FROM Canton c WHERE c.canID = :canID")
    , @NamedQuery(name = "Canton.findByCanNombre", query = "SELECT c FROM Canton c WHERE c.canNombre = :canNombre")
    , @NamedQuery(name = "Canton.findByCanDescripcion", query = "SELECT c FROM Canton c WHERE c.canDescripcion = :canDescripcion")})
public class Canton implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Can_ID")
    private Integer canID;
    @Basic(optional = false)
    @Column(name = "Can_Nombre")
    private String canNombre;
    @Column(name = "Can_Descripcion")
    private String canDescripcion;
    @OneToMany(mappedBy = "dirCanton")
    private List<Direccion> direccionList;

    public Canton() {
    }

    public Canton(Integer canID) {
        this.canID = canID;
    }

    public Canton(Integer canID, String canNombre) {
        this.canID = canID;
        this.canNombre = canNombre;
    }

    public Integer getCanID() {
        return canID;
    }

    public void setCanID(Integer canID) {
        this.canID = canID;
    }

    public String getCanNombre() {
        return canNombre;
    }

    public void setCanNombre(String canNombre) {
        this.canNombre = canNombre;
    }

    public String getCanDescripcion() {
        return canDescripcion;
    }

    public void setCanDescripcion(String canDescripcion) {
        this.canDescripcion = canDescripcion;
    }

    @XmlTransient
    public List<Direccion> getDireccionList() {
        return direccionList;
    }

    public void setDireccionList(List<Direccion> direccionList) {
        this.direccionList = direccionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (canID != null ? canID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Canton)) {
            return false;
        }
        Canton other = (Canton) object;
        if ((this.canID == null && other.canID != null) || (this.canID != null && !this.canID.equals(other.canID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Canton[ canID=" + canID + " ]";
    }
    
}
