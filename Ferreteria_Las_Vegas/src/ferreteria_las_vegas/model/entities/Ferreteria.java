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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Johan
 */
@Entity
@Table(name = "tb_ferreterias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ferreteria.findAll", query = "SELECT f FROM Ferreteria f")
    , @NamedQuery(name = "Ferreteria.findByFerCedula", query = "SELECT f FROM Ferreteria f WHERE f.ferCedula = :ferCedula")
    , @NamedQuery(name = "Ferreteria.findByFerNombre", query = "SELECT f FROM Ferreteria f WHERE f.ferNombre = :ferNombre")
    , @NamedQuery(name = "Ferreteria.findByFerEstado", query = "SELECT f FROM Ferreteria f WHERE f.ferEstado = :ferEstado")})
public class Ferreteria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Fer_Cedula")
    private String ferCedula;
    @Basic(optional = false)
    @Column(name = "Fer_Nombre")
    private String ferNombre;
    @Basic(optional = false)
    @Column(name = "Fer_Estado")
    private String ferEstado;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "ferreteria")
    private Parametro parametro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "PerFerreteria")
    private List<Persona> personaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bodFerreteria")
    private List<Bodega> bodegaList;

    public Ferreteria() {
    }

    public Ferreteria(String ferCedula) {
        this.ferCedula = ferCedula;
    }

    public Ferreteria(String ferCedula, String ferNombre, String ferEstado) {
        this.ferCedula = ferCedula;
        this.ferNombre = ferNombre;
        this.ferEstado = ferEstado;
    }

    public String getFerCedula() {
        return ferCedula;
    }

    public void setFerCedula(String ferCedula) {
        this.ferCedula = ferCedula;
    }

    public String getFerNombre() {
        return ferNombre;
    }

    public void setFerNombre(String ferNombre) {
        this.ferNombre = ferNombre;
    }

    public String getFerEstado() {
        return ferEstado;
    }

    public void setFerEstado(String ferEstado) {
        this.ferEstado = ferEstado;
    }

    public Parametro getParametro() {
        return parametro;
    }

    public void setParametro(Parametro parametro) {
        this.parametro = parametro;
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
        hash += (ferCedula != null ? ferCedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ferreteria)) {
            return false;
        }
        Ferreteria other = (Ferreteria) object;
        if ((this.ferCedula == null && other.ferCedula != null) || (this.ferCedula != null && !this.ferCedula.equals(other.ferCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Ferreteria[ ferCedula=" + ferCedula + " ]";
    }
    
}
