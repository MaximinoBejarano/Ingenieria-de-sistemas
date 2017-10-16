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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *Entidad DB
 * @author Usuario
 */
@Entity
@Table(name = "tb_personas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p")
    , @NamedQuery(name = "Persona.findByPerCedula", query = "SELECT p FROM Persona p WHERE p.perCedula = :perCedula")
    , @NamedQuery(name = "Persona.findByPerNombre", query = "SELECT p FROM Persona p WHERE p.perNombre = :perNombre")
    , @NamedQuery(name = "Persona.findByPerSNombre", query = "SELECT p FROM Persona p WHERE p.perSNombre = :perSNombre")
    , @NamedQuery(name = "Persona.findByPerPApellido", query = "SELECT p FROM Persona p WHERE p.perPApellido = :perPApellido")
    , @NamedQuery(name = "Persona.findByPerSApellido", query = "SELECT p FROM Persona p WHERE p.perSApellido = :perSApellido")})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Per_Cedula")
    private String perCedula;
    @Basic(optional = false)
    @Column(name = "Per_Nombre")
    private String perNombre;
    @Column(name = "Per_SNombre")
    private String perSNombre;
    @Basic(optional = false)
    @Column(name = "Per_PApellido")
    private String perPApellido;
    @Column(name = "Per_SApellido")
    private String perSApellido;
    @JoinTable(name = "tb_ferreterias_personas", joinColumns = {
        @JoinColumn(name = "RFP_Persona", referencedColumnName = "Per_Cedula")}, inverseJoinColumns = {
        @JoinColumn(name = "RFP_Ferreteria", referencedColumnName = "Fer_ID")})
    @ManyToMany
    private List<Ferreteria> ferreteriaList;
    @ManyToMany(mappedBy = "personaList")
    private List<Direccion> direccionList;
    @ManyToMany(mappedBy = "personaList")
    private List<Contacto> contactoList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Usuario usuario;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Cliente cliente;

    public Persona() {
    }

    public Persona(String perCedula) {
        this.perCedula = perCedula;
    }

    public Persona(String perCedula, String perNombre, String perPApellido) {
        this.perCedula = perCedula;
        this.perNombre = perNombre;
        this.perPApellido = perPApellido;
    }

    public String getPerCedula() {
        return perCedula;
    }

    public void setPerCedula(String perCedula) {
        this.perCedula = perCedula;
    }

    public String getPerNombre() {
        return perNombre;
    }

    public void setPerNombre(String perNombre) {
        this.perNombre = perNombre;
    }

    public String getPerSNombre() {
        return perSNombre;
    }

    public void setPerSNombre(String perSNombre) {
        this.perSNombre = perSNombre;
    }

    public String getPerPApellido() {
        return perPApellido;
    }

    public void setPerPApellido(String perPApellido) {
        this.perPApellido = perPApellido;
    }

    public String getPerSApellido() {
        return perSApellido;
    }

    public void setPerSApellido(String perSApellido) {
        this.perSApellido = perSApellido;
    }

    @XmlTransient
    public List<Ferreteria> getFerreteriaList() {
        return ferreteriaList;
    }

    public void setFerreteriaList(List<Ferreteria> ferreteriaList) {
        this.ferreteriaList = ferreteriaList;
    }

    @XmlTransient
    public List<Direccion> getDireccionList() {
        return direccionList;
    }

    public void setDireccionList(List<Direccion> direccionList) {
        this.direccionList = direccionList;
    }

    @XmlTransient
    public List<Contacto> getContactoList() {
        return contactoList;
    }

    public void setContactoList(List<Contacto> contactoList) {
        this.contactoList = contactoList;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perCedula != null ? perCedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.perCedula == null && other.perCedula != null) || (this.perCedula != null && !this.perCedula.equals(other.perCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Persona[ perCedula=" + perCedula + " ]";
    }
    
}
