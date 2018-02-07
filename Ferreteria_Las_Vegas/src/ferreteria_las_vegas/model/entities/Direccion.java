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
 * @author Johan
 */
@Entity
@Table(name = "tb_direcciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direccion.findAll", query = "SELECT d FROM Direccion d")
    , @NamedQuery(name = "Direccion.findByDirCodigo", query = "SELECT d FROM Direccion d WHERE d.dirCodigo = :dirCodigo")
    , @NamedQuery(name = "Direccion.findByDirDirExacta", query = "SELECT d FROM Direccion d WHERE d.dirDirExacta = :dirDirExacta")})
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Dir_Codigo")
    private Integer dirCodigo;
    @Basic(optional = false)
    @Column(name = "Dir_DirExacta")
    private String dirDirExacta;
    @ManyToMany(mappedBy = "direccionList")    
    private List<Persona> personaList;    
    @JoinTable(name = "tb_direcciones_proveedores", joinColumns = {
        @JoinColumn(name = "RDP_Direccion", referencedColumnName = "Dir_Codigo")}, inverseJoinColumns = {
        @JoinColumn(name = "RDP_Proveedor", referencedColumnName = "Pro_Codigo")})
    @ManyToMany
    private List<Proveedor> proveedorList;
    @JoinColumn(name = "Dir_Canton", referencedColumnName = "Can_Codigo")
    @ManyToOne
    private Canton dirCanton;
    @JoinColumn(name = "Dir_Distrito", referencedColumnName = "Dis_Codigo")
    @ManyToOne
    private Distrito dirDistrito;
    @JoinColumn(name = "Dir_Provincia", referencedColumnName = "Pro_Codigo")
    @ManyToOne
    private Provincia dirProvincia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bobDireccion")
    private List<Bodega> bodegaList;

    public Direccion() {
    }

    public Direccion(Integer dirCodigo) {
        this.dirCodigo = dirCodigo;
    }

    public Direccion(Integer dirCodigo, String dirDirExacta) {
        this.dirCodigo = dirCodigo;
        this.dirDirExacta = dirDirExacta;
    }

    public Integer getDirCodigo() {
        return dirCodigo;
    }

    public void setDirCodigo(Integer dirCodigo) {
        this.dirCodigo = dirCodigo;
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
        hash += (dirCodigo != null ? dirCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Direccion)) {
            return false;
        }
        Direccion other = (Direccion) object;
        if ((this.dirCodigo == null && other.dirCodigo != null) || (this.dirCodigo != null && !this.dirCodigo.equals(other.dirCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Direccion[ dirCodigo=" + dirCodigo + " ]";
    }
    
}
