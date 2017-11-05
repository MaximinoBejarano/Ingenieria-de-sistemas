/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferreteria_las_vegas.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "tb_clientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
    , @NamedQuery(name = "Cliente.findByCliPersona", query = "SELECT c FROM Cliente c WHERE c.cliPersona = :cliPersona")
    , @NamedQuery(name = "Cliente.findByCliFechaIngreso", query = "SELECT c FROM Cliente c WHERE c.cliFechaIngreso = :cliFechaIngreso")
    , @NamedQuery(name = "Cliente.findByCliEstado", query = "SELECT c FROM Cliente c WHERE c.cliEstado = :cliEstado")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Cli_Persona")
    private String cliPersona;
    @Basic(optional = false)
    @Column(name = "Cli_FechaIngreso")
    @Temporal(TemporalType.DATE)
    private Date cliFechaIngreso;
    @Basic(optional = false)
    @Column(name = "Cli_Estado")
    private String cliEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cueCliente")
    private List<CuentaXCobrar> cuentaXCobrarList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facCliente")
    private List<Factura> facturaList;
    @JoinColumn(name = "Cli_Persona", referencedColumnName = "Per_Cedula", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Persona persona;

    public Cliente() {
    }

    public Cliente(String cliPersona) {
        this.cliPersona = cliPersona;
    }

    public Cliente(String cliPersona, Date cliFechaIngreso, String cliEstado) {
        this.cliPersona = cliPersona;
        this.cliFechaIngreso = cliFechaIngreso;
        this.cliEstado = cliEstado;
    }

    public String getCliPersona() {
        return cliPersona;
    }

    public void setCliPersona(String cliPersona) {
        this.cliPersona = cliPersona;
    }

    public Date getCliFechaIngreso() {
        return cliFechaIngreso;
    }

    public void setCliFechaIngreso(Date cliFechaIngreso) {
        this.cliFechaIngreso = cliFechaIngreso;
    }

    public String getCliEstado() {
        return cliEstado;
    }

    public void setCliEstado(String cliEstado) {
        this.cliEstado = cliEstado;
    }

    @XmlTransient
    public List<CuentaXCobrar> getCuentaXCobrarList() {
        return cuentaXCobrarList;
    }

    public void setCuentaXCobrarList(List<CuentaXCobrar> cuentaXCobrarList) {
        this.cuentaXCobrarList = cuentaXCobrarList;
    }

    @XmlTransient
    public List<Factura> getFacturaList() {
        return facturaList;
    }

    public void setFacturaList(List<Factura> facturaList) {
        this.facturaList = facturaList;
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
        hash += (cliPersona != null ? cliPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.cliPersona == null && other.cliPersona != null) || (this.cliPersona != null && !this.cliPersona.equals(other.cliPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Cliente[ cliPersona=" + cliPersona + " ]";
    }
    
}
