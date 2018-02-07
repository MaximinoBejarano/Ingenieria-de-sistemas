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
@Table(name = "tb_bodegas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bodega.findAll", query = "SELECT b FROM Bodega b")
    , @NamedQuery(name = "Bodega.findByBodCodigo", query = "SELECT b FROM Bodega b WHERE b.bodCodigo = :bodCodigo")
    , @NamedQuery(name = "Bodega.findByBodNombre", query = "SELECT b FROM Bodega b WHERE b.bodNombre = :bodNombre")
    , @NamedQuery(name = "Bodega.findByBodDescripcion", query = "SELECT b FROM Bodega b WHERE b.bodDescripcion = :bodDescripcion")
    , @NamedQuery(name = "Bodega.findByBodEstado", query = "SELECT b FROM Bodega b WHERE b.bodEstado = :bodEstado")})
public class Bodega implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Bod_Codigo")
    private Integer bodCodigo;
    @Basic(optional = false)
    @Column(name = "Bod_Nombre")
    private String bodNombre;
    @Column(name = "Bod_Descripcion")
    private String bodDescripcion;
    @Basic(optional = false)
    @Column(name = "Bod_Estado")
    private String bodEstado;
    @OneToMany(mappedBy = "invBodega")
    private List<Inventario> inventarioList;
    @JoinColumn(name = "Bob_Direccion", referencedColumnName = "Dir_Codigo")
    @ManyToOne(optional = false)
    private Direccion bobDireccion;
    @JoinColumn(name = "Bod_Ferreteria", referencedColumnName = "Fer_Cedula")
    @ManyToOne(optional = false)
    private Ferreteria bodFerreteria;

    public Bodega() {
    }

    public Bodega(Integer bodCodigo) {
        this.bodCodigo = bodCodigo;
    }

    public Bodega(Integer bodCodigo, String bodNombre, String bodEstado) {
        this.bodCodigo = bodCodigo;
        this.bodNombre = bodNombre;
        this.bodEstado = bodEstado;
    }

    public Integer getBodCodigo() {
        return bodCodigo;
    }

    public void setBodCodigo(Integer bodCodigo) {
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

    public String getBodEstado() {
        return bodEstado;
    }

    public void setBodEstado(String bodEstado) {
        this.bodEstado = bodEstado;
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

    public Ferreteria getBodFerreteria() {
        return bodFerreteria;
    }

    public void setBodFerreteria(Ferreteria bodFerreteria) {
        this.bodFerreteria = bodFerreteria;
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
