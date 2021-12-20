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
@Table(name = "opcion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Opcion.findAll", query = "SELECT o FROM Opcion o")
    , @NamedQuery(name = "Opcion.findByIdOpcion", query = "SELECT o FROM Opcion o WHERE o.idOpcion = :idOpcion")
    , @NamedQuery(name = "Opcion.findByEstadoRespuesta", query = "SELECT o FROM Opcion o WHERE o.estadoRespuesta = :estadoRespuesta")
    , @NamedQuery(name = "Opcion.findByContenidoOpcion", query = "SELECT o FROM Opcion o WHERE o.contenidoOpcion = :contenidoOpcion")})
public class Opcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOpcion")
    private Integer idOpcion;
    @Basic(optional = false)
    @Column(name = "estadoRespuesta")
    private int estadoRespuesta;
    @Basic(optional = false)
    @Column(name = "contenidoOpcion")
    private String contenidoOpcion;
    @JoinColumn(name = "idPregunta", referencedColumnName = "idPregunta")
    @ManyToOne(optional = false)
    private Pregunta idPregunta;

    public Opcion() {
    }

    public Opcion(Integer idOpcion) {
        this.idOpcion = idOpcion;
    }

    public Opcion(Integer idOpcion, int estadoRespuesta, String contenidoOpcion) {
        this.idOpcion = idOpcion;
        this.estadoRespuesta = estadoRespuesta;
        this.contenidoOpcion = contenidoOpcion;
    }

    public Integer getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Integer idOpcion) {
        this.idOpcion = idOpcion;
    }

    public int getEstadoRespuesta() {
        return estadoRespuesta;
    }

    public void setEstadoRespuesta(int estadoRespuesta) {
        this.estadoRespuesta = estadoRespuesta;
    }

    public String getContenidoOpcion() {
        return contenidoOpcion;
    }

    public void setContenidoOpcion(String contenidoOpcion) {
        this.contenidoOpcion = contenidoOpcion;
    }

    public Pregunta getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Pregunta idPregunta) {
        this.idPregunta = idPregunta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOpcion != null ? idOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Opcion)) {
            return false;
        }
        Opcion other = (Opcion) object;
        if ((this.idOpcion == null && other.idOpcion != null) || (this.idOpcion != null && !this.idOpcion.equals(other.idOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Opcion[ idOpcion=" + idOpcion + " ]";
    }
    
}
