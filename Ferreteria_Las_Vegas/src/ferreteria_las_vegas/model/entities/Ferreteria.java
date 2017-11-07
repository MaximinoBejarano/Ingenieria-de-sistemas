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
 * @author Usuario
 * Entidad Mapeada
 */
@Entity
@Table(name = "tb_ferreteria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ferreteria.findAll", query = "SELECT f FROM Ferreteria f")
    , @NamedQuery(name = "Ferreteria.findByFerID", query = "SELECT f FROM Ferreteria f WHERE f.ferID = :ferID")
    , @NamedQuery(name = "Ferreteria.findByFerNombre", query = "SELECT f FROM Ferreteria f WHERE f.ferNombre = :ferNombre")
    , @NamedQuery(name = "Ferreteria.findByFerCedulaJurida", query = "SELECT f FROM Ferreteria f WHERE f.ferCedulaJurida = :ferCedulaJurida")})
public class Ferreteria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Fer_ID")
    private Integer ferID;
    @Basic(optional = false)
    @Column(name = "Fer_Nombre")
    private String ferNombre;
    @Basic(optional = false)
    @Column(name = "Fer_CedulaJurida")
    private String ferCedulaJurida;
    @ManyToMany(mappedBy = "ferreteriaList")
    private List<Persona> personaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bobFerreteria")
    private List<Bodega> bodegaList;

    public Ferreteria() {
    }

    public Ferreteria(Integer ferID) {
        this.ferID = ferID;
    }

    public Ferreteria(Integer ferID, String ferNombre, String ferCedulaJurida) {
        this.ferID = ferID;
        this.ferNombre = ferNombre;
        this.ferCedulaJurida = ferCedulaJurida;
    }

    public Integer getFerID() {
        return ferID;
    }

    public void setFerID(Integer ferID) {
        this.ferID = ferID;
    }

    public String getFerNombre() {
        return ferNombre;
    }

    public void setFerNombre(String ferNombre) {
        this.ferNombre = ferNombre;
    }

    public String getFerCedulaJurida() {
        return ferCedulaJurida;
    }

    public void setFerCedulaJurida(String ferCedulaJurida) {
        this.ferCedulaJurida = ferCedulaJurida;
    }

    @XmlTransient
    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
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
        hash += (ferID != null ? ferID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ferreteria)) {
            return false;
        }
        Ferreteria other = (Ferreteria) object;
        if ((this.ferID == null && other.ferID != null) || (this.ferID != null && !this.ferID.equals(other.ferID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Ferreteria[ ferID=" + ferID + " ]";
    }
    
}
