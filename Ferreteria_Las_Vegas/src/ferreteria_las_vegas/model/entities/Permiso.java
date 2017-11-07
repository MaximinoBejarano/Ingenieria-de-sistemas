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
 * @author Usuario
 * Entidad Mapeada
 */
@Entity
@Table(name = "tb_permisos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permiso.findAll", query = "SELECT p FROM Permiso p")
    , @NamedQuery(name = "Permiso.findByPerID", query = "SELECT p FROM Permiso p WHERE p.perID = :perID")
    , @NamedQuery(name = "Permiso.findByPerNombre", query = "SELECT p FROM Permiso p WHERE p.perNombre = :perNombre")
    , @NamedQuery(name = "Permiso.findByPerModulo", query = "SELECT p FROM Permiso p WHERE p.perModulo = :perModulo")
    , @NamedQuery(name = "Permiso.findByPerDescripcion", query = "SELECT p FROM Permiso p WHERE p.perDescripcion = :perDescripcion")})
public class Permiso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Per_ID")
    private Integer perID;
    @Basic(optional = false)
    @Column(name = "Per_Nombre")
    private String perNombre;
    @Basic(optional = false)
    @Column(name = "Per_Modulo")
    private String perModulo;
    @Basic(optional = false)
    @Column(name = "Per_Descripcion")
    private String perDescripcion;
    
    @ManyToMany(mappedBy = "permisoList")
    private List<Usuario> usuarioList;   

    public Permiso() {
    }

    public Permiso(Integer perID) {
        this.perID = perID;
    }

    public Permiso(Integer perID, String perNombre, String perModulo, String perDescripcion) {
        this.perID = perID;
        this.perNombre = perNombre;
        this.perModulo = perModulo;
        this.perDescripcion = perDescripcion;
    }

    public Integer getPerID() {
        return perID;
    }

    public void setPerID(Integer perID) {
        this.perID = perID;
    }

    public String getPerNombre() {
        return perNombre;
    }

    public void setPerNombre(String perNombre) {
        this.perNombre = perNombre;
    }

    public String getPerModulo() {
        return perModulo;
    }

    public void setPerModulo(String perModulo) {
        this.perModulo = perModulo;
    }

    public String getPerDescripcion() {
        return perDescripcion;
    }

    public void setPerDescripcion(String perDescripcion) {
        this.perDescripcion = perDescripcion;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perID != null ? perID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permiso)) {
            return false;
        }
        Permiso other = (Permiso) object;
        if ((this.perID == null && other.perID != null) || (this.perID != null && !this.perID.equals(other.perID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Permiso[ perID=" + perID + " ]";
    }
    
}
