/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Jugador;
import modelo.Ronda;

/**
 *
 * @author Tamagochita
 */
public class JugadorJpaController implements Serializable {

    public JugadorJpaController() {
          this.emf = Persistence.createEntityManagerFactory("juegoPreguntasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jugador jugador) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ronda idRonda = jugador.getIdRonda();
            if (idRonda != null) {
                idRonda = em.getReference(idRonda.getClass(), idRonda.getIdRonda());
                jugador.setIdRonda(idRonda);
            }
            em.persist(jugador);
            if (idRonda != null) {
                idRonda.getJugadorList().add(jugador);
                idRonda = em.merge(idRonda);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jugador jugador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugador persistentJugador = em.find(Jugador.class, jugador.getIdJugador());
            Ronda idRondaOld = persistentJugador.getIdRonda();
            Ronda idRondaNew = jugador.getIdRonda();
            if (idRondaNew != null) {
                idRondaNew = em.getReference(idRondaNew.getClass(), idRondaNew.getIdRonda());
                jugador.setIdRonda(idRondaNew);
            }
            jugador = em.merge(jugador);
            if (idRondaOld != null && !idRondaOld.equals(idRondaNew)) {
                idRondaOld.getJugadorList().remove(jugador);
                idRondaOld = em.merge(idRondaOld);
            }
            if (idRondaNew != null && !idRondaNew.equals(idRondaOld)) {
                idRondaNew.getJugadorList().add(jugador);
                idRondaNew = em.merge(idRondaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = jugador.getIdJugador();
                if (findJugador(id) == null) {
                    throw new NonexistentEntityException("The jugador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugador jugador;
            try {
                jugador = em.getReference(Jugador.class, id);
                jugador.getIdJugador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jugador with id " + id + " no longer exists.", enfe);
            }
            Ronda idRonda = jugador.getIdRonda();
            if (idRonda != null) {
                idRonda.getJugadorList().remove(jugador);
                idRonda = em.merge(idRonda);
            }
            em.remove(jugador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jugador> findJugadorEntities() {
        return findJugadorEntities(true, -1, -1);
    }

    public List<Jugador> findJugadorEntities(int maxResults, int firstResult) {
        return findJugadorEntities(false, maxResults, firstResult);
    }

    private List<Jugador> findJugadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jugador.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Jugador findJugador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jugador.class, id);
        } finally {
            em.close();
        }
    }

    public int getJugadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jugador> rt = cq.from(Jugador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
