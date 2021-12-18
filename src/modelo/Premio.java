/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author Tamagochita
 */
@Entity
@Table(name = "premio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Premio.findAll", query = "SELECT p FROM Premio p")
    , @NamedQuery(name = "Premio.findByIdPremio", query = "SELECT p FROM Premio p WHERE p.idPremio = :idPremio")
    , @NamedQuery(name = "Premio.findByCantPremio", query = "SELECT p FROM Premio p WHERE p.cantPremio = :cantPremio")})
public class Premio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPremio")
    private Integer idPremio;
    @Basic(optional = false)
    @Column(name = "cantPremio")
    private int cantPremio;
    @JoinColumn(name = "numRonda", referencedColumnName = "idRonda")
    @ManyToOne(optional = false)
    private Ronda numRonda;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPremio")
    private List<Jugador> jugadorList;

    public Premio() {
    }

    public Premio(Integer idPremio) {
        this.idPremio = idPremio;
    }

    public Premio(Integer idPremio, int cantPremio) {
        this.idPremio = idPremio;
        this.cantPremio = cantPremio;
    }

    public Integer getIdPremio() {
        return idPremio;
    }

    public void setIdPremio(Integer idPremio) {
        this.idPremio = idPremio;
    }

    public int getCantPremio() {
        return cantPremio;
    }

    public void setCantPremio(int cantPremio) {
        this.cantPremio = cantPremio;
    }

    public Ronda getNumRonda() {
        return numRonda;
    }

    public void setNumRonda(Ronda numRonda) {
        this.numRonda = numRonda;
    }

    @XmlTransient
    public List<Jugador> getJugadorList() {
        return jugadorList;
    }

    public void setJugadorList(List<Jugador> jugadorList) {
        this.jugadorList = jugadorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPremio != null ? idPremio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Premio)) {
            return false;
        }
        Premio other = (Premio) object;
        if ((this.idPremio == null && other.idPremio != null) || (this.idPremio != null && !this.idPremio.equals(other.idPremio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "juegopreguntas.modelo.Premio[ idPremio=" + idPremio + " ]";
    }
    
}
