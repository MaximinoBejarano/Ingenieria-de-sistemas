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
import javax.persistence.JoinColumn;
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
@Table(name = "tb_bodegas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bodega.findAll", query = "SELECT b FROM Bodega b")
    , @NamedQuery(name = "Bodega.findByBodCodigo", query = "SELECT b FROM Bodega b WHERE b.bodCodigo = :bodCodigo")
    , @NamedQuery(name = "Bodega.findByBodNombre", query = "SELECT b FROM Bodega b WHERE b.bodNombre = :bodNombre")
    , @NamedQuery(name = "Bodega.findByBodDescripcion", query = "SELECT b FROM Bodega b WHERE b.bodDescripcion = :bodDescripcion")})
public class Bodega implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Bod_Codigo")
    private String bodCodigo;
    @Basic(optional = false)
    @Column(name = "Bod_Nombre")
    private String bodNombre;
    @Column(name = "Bod_Descripcion")
    private String bodDescripcion;
    @OneToMany(mappedBy = "invBodega")
    private List<Inventario> inventarioList;
    @JoinColumn(name = "Bob_Direccion", referencedColumnName = "Dir_ID")
    @ManyToOne(optional = false)
    private Direccion bobDireccion;
    @JoinColumn(name = "Bob_Ferreteria", referencedColumnName = "Fer_ID")
    @ManyToOne(optional = false)
    private Ferreteria bobFerreteria;

    public Bodega() {
    }

    public Bodega(String bodCodigo) {
        this.bodCodigo = bodCodigo;
    }

    public Bodega(String bodCodigo, String bodNombre) {
        this.bodCodigo = bodCodigo;
        this.bodNombre = bodNombre;
    }

    public String getBodCodigo() {
        return bodCodigo;
    }

    public void setBodCodigo(String bodCodigo) {
        this.bodCodigo = bodCodigo;
    }

    public String getBodNombre() {
        return bodNombre;
    }

    public void setBodNombre(String bodNombre) {
        this.bodNombre = bodNombre;
    }

    public String getBodDescripcion() {
        return bodDescripcion;
    }

    public void setBodDescripcion(String bodDescripcion) {
        this.bodDescripcion = bodDescripcion;
    }

    @XmlTransient
    public List<Inventario> getInventarioList() {
        return inventarioList;
    }

    public void setInventarioList(List<Inventario> inventarioList) {
        this.inventarioList = inventarioList;
    }

    public Direccion getBobDireccion() {
        return bobDireccion;
    }

    public void setBobDireccion(Direccion bobDireccion) {
        this.bobDireccion = bobDireccion;
    }

    public Ferreteria getBobFerreteria() {
        return bobFerreteria;
    }

    public void setBobFerreteria(Ferreteria bobFerreteria) {
        this.bobFerreteria = bobFerreteria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bodCodigo != null ? bodCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bodega)) {
            return false;
        }
        Bodega other = (Bodega) object;
        if ((this.bodCodigo == null && other.bodCodigo != null) || (this.bodCodigo != null && !this.bodCodigo.equals(other.bodCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Bodega[ bodCodigo=" + bodCodigo + " ]";
    }
    
}
