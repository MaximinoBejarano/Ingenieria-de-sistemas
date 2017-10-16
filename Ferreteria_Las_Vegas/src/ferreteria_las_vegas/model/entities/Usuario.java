/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "tb_usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByUsuPersona", query = "SELECT u FROM Usuario u WHERE u.usuPersona = :usuPersona")
    , @NamedQuery(name = "Usuario.findByUsuNombre", query = "SELECT u FROM Usuario u WHERE u.usuNombre = :usuNombre")
    , @NamedQuery(name = "Usuario.findByUsuContrase\u00f1a", query = "SELECT u FROM Usuario u WHERE u.usuContrase\u00f1a = :usuContrase\u00f1a")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Usu_Persona")
    private String usuPersona;
    @Basic(optional = false)
    @Column(name = "Usu_Nombre")
    private String usuNombre;
    @Basic(optional = false)
    @Column(name = "Usu_Contrase\u00f1a")
    private String usuContraseña;
    @JoinColumn(name = "Usu_Persona", referencedColumnName = "Per_Cedula", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;
    @JoinColumn(name = "Usu_Rol", referencedColumnName = "Rol_ID")
    @ManyToOne(optional = false)
    private Rol usuRol;

    public Usuario() {
    }

    public Usuario(String usuPersona) {
        this.usuPersona = usuPersona;
    }

    public Usuario(String usuPersona, String usuNombre, String usuContraseña) {
        this.usuPersona = usuPersona;
        this.usuNombre = usuNombre;
        this.usuContraseña = usuContraseña;
    }

    public String getUsuPersona() {
        return usuPersona;
    }

    public void setUsuPersona(String usuPersona) {
        this.usuPersona = usuPersona;
    }

    public String getUsuNombre() {
        return usuNombre;
    }

    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }

    public String getUsuContraseña() {
        return usuContraseña;
    }

    public void setUsuContraseña(String usuContraseña) {
        this.usuContraseña = usuContraseña;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Rol getUsuRol() {
        return usuRol;
    }

    public void setUsuRol(Rol usuRol) {
        this.usuRol = usuRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuPersona != null ? usuPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuPersona == null && other.usuPersona != null) || (this.usuPersona != null && !this.usuPersona.equals(other.usuPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Usuario[ usuPersona=" + usuPersona + " ]";
    }
    
}
