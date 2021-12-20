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
import javax.persistence.Id;
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
@Table(name = "ronda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ronda.findAll", query = "SELECT r FROM Ronda r")
    , @NamedQuery(name = "Ronda.findByIdRonda", query = "SELECT r FROM Ronda r WHERE r.idRonda = :idRonda")
    , @NamedQuery(name = "Ronda.findByNumRonda", query = "SELECT r FROM Ronda r WHERE r.numRonda = :numRonda")})
public class Ronda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idRonda")
    private Integer idRonda;
    @Basic(optional = false)
    @Column(name = "numRonda")
    private int numRonda;
    @OneToMany(mappedBy = "idRonda")
    private List<Jugador> jugadorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRonda")
    private List<Pregunta> preguntaList;

    public Ronda() {
    }

    public Ronda(Integer idRonda) {
        this.idRonda = idRonda;
    }

    public Ronda(Integer idRonda, int numRonda) {
        this.idRonda = idRonda;
        this.numRonda = numRonda;
    }

    public Integer getIdRonda() {
        return idRonda;
    }

    public void setIdRonda(Integer idRonda) {
        this.idRonda = idRonda;
    }

    public int getNumRonda() {
        return numRonda;
    }

    public void setNumRonda(int numRonda) {
        this.numRonda = numRonda;
    }

    @XmlTransient
    public List<Jugador> getJugadorList() {
        return jugadorList;
    }

    public void setJugadorList(List<Jugador> jugadorList) {
        this.jugadorList = jugadorList;
    }

    @XmlTransient
    public List<Pregunta> getPreguntaList() {
        return preguntaList;
    }

    public void setPreguntaList(List<Pregunta> preguntaList) {
        this.preguntaList = preguntaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRonda != null ? idRonda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ronda)) {
            return false;
        }
        Ronda other = (Ronda) object;
        if ((this.idRonda == null && other.idRonda != null) || (this.idRonda != null && !this.idRonda.equals(other.idRonda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Ronda[ idRonda=" + idRonda + " ]";
    }
    
}
