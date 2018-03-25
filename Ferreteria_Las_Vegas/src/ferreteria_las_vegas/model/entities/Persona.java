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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Johan
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
    , @NamedQuery(name = "Persona.findByPerSApellido", query = "SELECT p FROM Persona p WHERE p.perSApellido = :perSApellido")
    , @NamedQuery(name = "Persona.findByPerEstado", query = "SELECT p FROM Persona p WHERE p.perEstado = :perEstado")})
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
    @Basic(optional = false)
    @Column(name = "Per_Estado")
    private String perEstado;
    @JoinTable(name = "tb_personas_direcciones", joinColumns = {
        @JoinColumn(name = "RPD_Persona", referencedColumnName = "Per_Cedula")}, inverseJoinColumns = {
        @JoinColumn(name = "RPD_Direccion", referencedColumnName = "Dir_Codigo")})
    @ManyToMany
    private List<Direccion> direccionList;        
    @JoinTable(name = "tb_personas_contactos", joinColumns = {
        @JoinColumn(name = "RPC_Persona", referencedColumnName = "Per_Cedula")}, inverseJoinColumns = {
        @JoinColumn(name = "RPC_Contacto", referencedColumnName = "Con_Codigo")})
    @ManyToMany
    private List<Contacto> contactoList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Usuario usuario;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona")
    private Cliente cliente;
    @JoinColumn(name = "Per_Ferreteria", referencedColumnName = "Fer_Cedula")
    @ManyToOne(optional = false)
    private Ferreteria PerFerreteria;

    public Persona() {
    }

    public Persona(String perCedula) {
        this.perCedula = perCedula;
    }

    public Persona(String perCedula, String perNombre, String perPApellido, String perEstado) {
        this.perCedula = perCedula;
        this.perNombre = perNombre;
        this.perPApellido = perPApellido;
        this.perEstado = perEstado;        
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

    public String getPerEstado() {
        return perEstado;
    }

    public void setPerEstado(String perEstado) {
        this.perEstado = perEstado;
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

    public Ferreteria getPerFerreteria() {
        return PerFerreteria;
    }

    public void setPerFerreteria(Ferreteria PerFerreteria) {
        this.PerFerreteria = PerFerreteria;
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
