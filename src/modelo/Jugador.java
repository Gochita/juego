/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tamagochita
 */
@Entity
@Table(name = "jugador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jugador.findAll", query = "SELECT j FROM Jugador j")
    , @NamedQuery(name = "Jugador.findByIdJugador", query = "SELECT j FROM Jugador j WHERE j.idJugador = :idJugador")
    , @NamedQuery(name = "Jugador.findByNombreJugador", query = "SELECT j FROM Jugador j WHERE j.nombreJugador = :nombreJugador")
    , @NamedQuery(name = "Jugador.findByEstadoJugador", query = "SELECT j FROM Jugador j WHERE j.estadoJugador = :estadoJugador")
    , @NamedQuery(name = "Jugador.findByAcumuladoPremio", query = "SELECT j FROM Jugador j WHERE j.acumuladoPremio = :acumuladoPremio")})
public class Jugador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idJugador")
    private Integer idJugador;
    @Column(name = "nombreJugador")
    private String nombreJugador;
    @Column(name = "estadoJugador")
    private Integer estadoJugador;
    @Column(name = "acumuladoPremio")
    private Integer acumuladoPremio;
    @JoinColumn(name = "idRonda", referencedColumnName = "idRonda")
    @ManyToOne
    private Ronda idRonda;

    public Jugador() {
    }

    public Jugador(Integer idJugador) {
        this.idJugador = idJugador;
    }

    public Integer getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(Integer idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public Integer getEstadoJugador() {
        return estadoJugador;
    }

    public void setEstadoJugador(Integer estadoJugador) {
        this.estadoJugador = estadoJugador;
    }

    public Integer getAcumuladoPremio() {
        return acumuladoPremio;
    }

    public void setAcumuladoPremio(Integer acumuladoPremio) {
        this.acumuladoPremio = acumuladoPremio;
    }

    public Ronda getIdRonda() {
        return idRonda;
    }

    public void setIdRonda(Ronda idRonda) {
        this.idRonda = idRonda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJugador != null ? idJugador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jugador)) {
            return false;
        }
        Jugador other = (Jugador) object;
        if ((this.idJugador == null && other.idJugador != null) || (this.idJugador != null && !this.idJugador.equals(other.idJugador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Jugador[ idJugador=" + idJugador + " ]";
    }
    
}
