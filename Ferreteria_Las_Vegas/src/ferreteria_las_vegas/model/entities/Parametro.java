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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Johan
 */
@Entity
@Table(name = "tb_parametros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametro.findAll", query = "SELECT p FROM Parametro p")
    , @NamedQuery(name = "Parametro.findByParFerreteria", query = "SELECT p FROM Parametro p WHERE p.parFerreteria = :parFerreteria")
    , @NamedQuery(name = "Parametro.findByParTipoCambio", query = "SELECT p FROM Parametro p WHERE p.parTipoCambio = :parTipoCambio")})
public class Parametro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Par_Ferreteria")
    private String parFerreteria;
    @Basic(optional = false)
    @Column(name = "Par_TipoCambio")
    private double parTipoCambio;
    @JoinColumn(name = "Par_Ferreteria", referencedColumnName = "Fer_Cedula", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Ferreteria ferreteria;

    public Parametro() {
    }

    public Parametro(String parFerreteria) {
        this.parFerreteria = parFerreteria;
    }

    public Parametro(String parFerreteria, double parTipoCambio) {
        this.parFerreteria = parFerreteria;
        this.parTipoCambio = parTipoCambio;
    }

    public String getParFerreteria() {
        return parFerreteria;
    }

    public void setParFerreteria(String parFerreteria) {
        this.parFerreteria = parFerreteria;
    }

    public double getParTipoCambio() {
        return parTipoCambio;
    }

    public void setParTipoCambio(double parTipoCambio) {
        this.parTipoCambio = parTipoCambio;
    }

    public Ferreteria getFerreteria() {
        return ferreteria;
    }

    public void setFerreteria(Ferreteria ferreteria) {
        this.ferreteria = ferreteria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parFerreteria != null ? parFerreteria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametro)) {
            return false;
        }
        Parametro other = (Parametro) object;
        if ((this.parFerreteria == null && other.parFerreteria != null) || (this.parFerreteria != null && !this.parFerreteria.equals(other.parFerreteria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ferreteria_las_vegas.model.entities.Parametro[ parFerreteria=" + parFerreteria + " ]";
    }
    
}
