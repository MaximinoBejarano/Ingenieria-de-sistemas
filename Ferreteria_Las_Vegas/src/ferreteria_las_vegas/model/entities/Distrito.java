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
@Table(name = "tb_distritos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Distrito.findAll", query = "SELECT d FROM Distrito d")
    , @NamedQuery(name = "Distrito.findByDisCodigo", query = "SELECT d FROM Distrito d WHERE d.disCodigo = :disCodigo")
    , @NamedQuery(name = "Distrito.findByDisNombre", query = "SELECT d FROM Distrito d WHERE d.disNombre = :disNombre")
    , @NamedQuery(name = "Distrito.findByDisDescripcion", query = "SELECT d FROM Distrito d WHERE d.disDescripcion = :disDescripcion")})
public class Distrito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Dis_Codigo")
    private Integer disCodigo;
    @Basic(optional = false)
    @Column(name = "Dis_Nombre")
    private String disNombre;
    @Column(name = "Dis_Descripcion")
    private String disDescripcion;
    @OneToMany(mappedBy = "dirDistrito")
    private List<Direccion> direccionList;

    public Distrito() {
    }

    public Distrito(Integer disCodigo) {
        this.disCodigo = disCodigo;
    }

    public Distrito(Integer disCodigo, String disNombre) {
        this.disCodigo = disCodigo;
        this.disNombre = disNombre;
    }

    public Integer getDisCodigo() {
        return disCodigo;
    }

    public void setDisCodigo(Integer disCodigo) {
        this.disCodigo = disCodigo;
    }

    public String getDisNombre() {
        return disNombre;
    }

    public void setDisNombre(String disNombre) {
        this.disNombre = disNombre;
    }

    public String getDisDescripcion() {
        return disDescripcion;
    }

    public void setDisDescripcion(String disDescripcion) {
        this.disDescripcion = disDescripcion;
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
        hash += (disCodigo != null ? disCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Distrito)) {
            return false;
        }
        Distrito other = (Distrito) object;
        if ((this.disCodigo == null && other.disCodigo != null) || (this.disCodigo != null && !this.disCodigo.equals(other.disCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Distrito[ disCodigo=" + disCodigo + " ]";
    }
    
}
