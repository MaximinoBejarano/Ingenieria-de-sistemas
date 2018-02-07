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
@Table(name = "tb_cantones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Canton.findAll", query = "SELECT c FROM Canton c")
    , @NamedQuery(name = "Canton.findByCanCodigo", query = "SELECT c FROM Canton c WHERE c.canCodigo = :canCodigo")
    , @NamedQuery(name = "Canton.findByCanNombre", query = "SELECT c FROM Canton c WHERE c.canNombre = :canNombre")
    , @NamedQuery(name = "Canton.findByCanDescripcion", query = "SELECT c FROM Canton c WHERE c.canDescripcion = :canDescripcion")})
public class Canton implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Can_Codigo")
    private Integer canCodigo;
    @Basic(optional = false)
    @Column(name = "Can_Nombre")
    private String canNombre;
    @Column(name = "Can_Descripcion")
    private String canDescripcion;
    @OneToMany(mappedBy = "dirCanton")
    private List<Direccion> direccionList;

    public Canton() {
    }

    public Canton(Integer canCodigo) {
        this.canCodigo = canCodigo;
    }

    public Canton(Integer canCodigo, String canNombre) {
        this.canCodigo = canCodigo;
        this.canNombre = canNombre;
    }

    public Integer getCanCodigo() {
        return canCodigo;
    }

    public void setCanCodigo(Integer canCodigo) {
        this.canCodigo = canCodigo;
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
        hash += (canCodigo != null ? canCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Canton)) {
            return false;
        }
        Canton other = (Canton) object;
        if ((this.canCodigo == null && other.canCodigo != null) || (this.canCodigo != null && !this.canCodigo.equals(other.canCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Canton[ canCodigo=" + canCodigo + " ]";
    }
    
}
