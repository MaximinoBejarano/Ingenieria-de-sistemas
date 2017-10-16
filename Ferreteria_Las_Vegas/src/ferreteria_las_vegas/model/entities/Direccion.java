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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "tb_direcciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direccion.findAll", query = "SELECT d FROM Direccion d")
    , @NamedQuery(name = "Direccion.findByDirID", query = "SELECT d FROM Direccion d WHERE d.dirID = :dirID")
    , @NamedQuery(name = "Direccion.findByDirDirExacta", query = "SELECT d FROM Direccion d WHERE d.dirDirExacta = :dirDirExacta")})
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Dir_ID")
    private Integer dirID;
    @Basic(optional = false)
    @Column(name = "Dir_DirExacta")
    private String dirDirExacta;
    @JoinTable(name = "tb_personas_direcciones", joinColumns = {
        @JoinColumn(name = "RPD_Direccion", referencedColumnName = "Dir_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "RPD_Persona", referencedColumnName = "Per_Cedula")})
    @ManyToMany
    private List<Persona> personaList;
    @JoinTable(name = "tb_direcciones_proveedores", joinColumns = {
        @JoinColumn(name = "RDP_Direccion", referencedColumnName = "Dir_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "RDP_Proveedor", referencedColumnName = "Pro_ID")})
    @ManyToMany
    private List<Proveedor> proveedorList;
    @JoinColumn(name = "Dir_Canton", referencedColumnName = "Can_ID")
    @ManyToOne
    private Canton dirCanton;
    @JoinColumn(name = "Dir_Distrito", referencedColumnName = "Dis_ID")
    @ManyToOne
    private Distrito dirDistrito;
    @JoinColumn(name = "Dir_Provincia", referencedColumnName = "Pro_ID")
    @ManyToOne
    private Provincia dirProvincia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bobDireccion")
    private List<Bodega> bodegaList;

    public Direccion() {
    }

    public Direccion(Integer dirID) {
        this.dirID = dirID;
    }

    public Direccion(Integer dirID, String dirDirExacta) {
        this.dirID = dirID;
        this.dirDirExacta = dirDirExacta;
    }

    public Integer getDirID() {
        return dirID;
    }

    public void setDirID(Integer dirID) {
        this.dirID = dirID;
    }

    public String getDirDirExacta() {
        return dirDirExacta;
    }

    public void setDirDirExacta(String dirDirExacta) {
        this.dirDirExacta = dirDirExacta;
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

    public Canton getDirCanton() {
        return dirCanton;
    }

    public void setDirCanton(Canton dirCanton) {
        this.dirCanton = dirCanton;
    }

    public Distrito getDirDistrito() {
        return dirDistrito;
    }

    public void setDirDistrito(Distrito dirDistrito) {
        this.dirDistrito = dirDistrito;
    }

    public Provincia getDirProvincia() {
        return dirProvincia;
    }

    public void setDirProvincia(Provincia dirProvincia) {
        this.dirProvincia = dirProvincia;
    }

    @XmlTransient
    public List<Bodega> getBodegaList() {
        return bodegaList;
    }

    public void setBodegaList(List<Bodega> bodegaList) {
        this.bodegaList = bodegaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dirID != null ? dirID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Direccion)) {
            return false;
        }
        Direccion other = (Direccion) object;
        if ((this.dirID == null && other.dirID != null) || (this.dirID != null && !this.dirID.equals(other.dirID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Direccion[ dirID=" + dirID + " ]";
    }
    
}
