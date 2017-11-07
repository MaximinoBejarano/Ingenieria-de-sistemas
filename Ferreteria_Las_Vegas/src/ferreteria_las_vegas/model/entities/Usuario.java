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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 * Entidad Mapeada
 */
@Entity
@Table(name = "tb_usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByUsuPersona", query = "SELECT u FROM Usuario u WHERE u.usuPersona = :usuPersona")
    , @NamedQuery(name = "Usuario.findByUsuLogin", query = "SELECT u FROM Usuario u WHERE u.usuNombre = :usuUsuario AND u.usuContrase\u00f1a = :usuContrase\u00f1a")
    , @NamedQuery(name = "Usuario.findByUsuNombre", query = "SELECT u FROM Usuario u WHERE u.usuNombre = :usuNombre")
    , @NamedQuery(name = "Usuario.findByUsuContrase\u00f1a", query = "SELECT u FROM Usuario u WHERE u.usuContrase\u00f1a = :usuContrase\u00f1a")
    , @NamedQuery(name = "Usuario.findByUsuEstado", query = "SELECT u FROM Usuario u WHERE u.usuEstado = :usuEstado")})
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
    @Basic(optional = false)
    @Column(name = "Usu_Estado")
    private String usuEstado;
    
    @JoinTable(name = "tb_permisos_usuarios", joinColumns = {
        @JoinColumn(name = "RPU_Usuarios", referencedColumnName = "Usu_Persona")}, inverseJoinColumns = {
        @JoinColumn(name = "RPU_Permisos", referencedColumnName = "Per_ID")})
    @ManyToMany
    private List<Permiso> permisoList;
    
    @JoinColumn(name = "Usu_Persona", referencedColumnName = "Per_Cedula", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;

    public Usuario() {
    }

    public Usuario(String usuPersona) {
        this.usuPersona = usuPersona;
    }

    public Usuario(String usuPersona, String usuNombre, String usuContraseña, String usuEstado) {
        this.usuPersona = usuPersona;
        this.usuNombre = usuNombre;
        this.usuContraseña = usuContraseña;
        this.usuEstado = usuEstado;
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

    public String getUsuEstado() {
        return usuEstado;
    }

    public void setUsuEstado(String usuEstado) {
        this.usuEstado = usuEstado;
    }

    @XmlTransient
    public List<Permiso> getPermisoList() {
        return permisoList;
    }

    public void setPermisoList(List<Permiso> permisoList) {
        this.permisoList = permisoList;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
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
