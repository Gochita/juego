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
    @Basic(optional = false)
    @Column(name = "nombreJugador")
    private String nombreJugador;
    @Basic(optional = false)
    @Column(name = "estadoJugador")
    private int estadoJugador;
    @Basic(optional = false)
    @Column(name = "acumuladoPremio")
    private int acumuladoPremio;
    @JoinColumn(name = "idPremio", referencedColumnName = "idPremio")
    @ManyToOne(optional = false)
    private Premio idPremio;

    public Jugador() {
    }

    public Jugador(Integer idJugador) {
        this.idJugador = idJugador;
    }

    public Jugador(Integer idJugador, String nombreJugador, int estadoJugador, int acumuladoPremio) {
        this.idJugador = idJugador;
        this.nombreJugador = nombreJugador;
        this.estadoJugador = estadoJugador;
        this.acumuladoPremio = acumuladoPremio;
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

    public int getEstadoJugador() {
        return estadoJugador;
    }

    public void setEstadoJugador(int estadoJugador) {
        this.estadoJugador = estadoJugador;
    }

    public int getAcumuladoPremio() {
        return acumuladoPremio;
    }

    public void setAcumuladoPremio(int acumuladoPremio) {
        this.acumuladoPremio = acumuladoPremio;
    }

    public Premio getIdPremio() {
        return idPremio;
    }

    public void setIdPremio(Premio idPremio) {
        this.idPremio = idPremio;
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
        return "juegopreguntas.modelo.Jugador[ idJugador=" + idJugador + " ]";
    }
    
}
