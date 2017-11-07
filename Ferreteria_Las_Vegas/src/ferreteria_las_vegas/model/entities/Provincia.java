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
 * @author Usuario
 * Entidad Mapeada
 */
@Entity
@Table(name = "tb_provincias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Provincia.findAll", query = "SELECT p FROM Provincia p")
    , @NamedQuery(name = "Provincia.findByProID", query = "SELECT p FROM Provincia p WHERE p.proID = :proID")
    , @NamedQuery(name = "Provincia.findByProNombre", query = "SELECT p FROM Provincia p WHERE p.proNombre = :proNombre")
    , @NamedQuery(name = "Provincia.findByProDescripcion", query = "SELECT p FROM Provincia p WHERE p.proDescripcion = :proDescripcion")})
public class Provincia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Pro_ID")
    private Integer proID;
    @Basic(optional = false)
    @Column(name = "Pro_Nombre")
    private String proNombre;
    @Column(name = "Pro_Descripcion")
    private String proDescripcion;
    @OneToMany(mappedBy = "dirProvincia")
    private List<Direccion> direccionList;

    public Provincia() {
    }

    public Provincia(Integer proID) {
        this.proID = proID;
    }

    public Provincia(Integer proID, String proNombre) {
        this.proID = proID;
        this.proNombre = proNombre;
    }

    public Integer getProID() {
        return proID;
    }

    public void setProID(Integer proID) {
        this.proID = proID;
    }

    public String getProNombre() {
        return proNombre;
    }

    public void setProNombre(String proNombre) {
        this.proNombre = proNombre;
    }

    public String getProDescripcion() {
        return proDescripcion;
    }

    public void setProDescripcion(String proDescripcion) {
        this.proDescripcion = proDescripcion;
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
        hash += (proID != null ? proID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Provincia)) {
            return false;
        }
        Provincia other = (Provincia) object;
        if ((this.proID == null && other.proID != null) || (this.proID != null && !this.proID.equals(other.proID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Provincia[ proID=" + proID + " ]";
    }
    
}
