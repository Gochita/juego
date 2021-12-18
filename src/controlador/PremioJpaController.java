/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Ronda;
import modelo.Jugador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Premio;

/**
 *
 * @author Tamagochita
 */
public class PremioJpaController implements Serializable {

    public PremioJpaController() {
       this.emf = Persistence.createEntityManagerFactory("juegoPreguntasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Premio premio) {
        if (premio.getJugadorList() == null) {
            premio.setJugadorList(new ArrayList<Jugador>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ronda numRonda = premio.getNumRonda();
            if (numRonda != null) {
                numRonda = em.getReference(numRonda.getClass(), numRonda.getIdRonda());
                premio.setNumRonda(numRonda);
            }
            List<Jugador> attachedJugadorList = new ArrayList<Jugador>();
            for (Jugador jugadorListJugadorToAttach : premio.getJugadorList()) {
                jugadorListJugadorToAttach = em.getReference(jugadorListJugadorToAttach.getClass(), jugadorListJugadorToAttach.getIdJugador());
                attachedJugadorList.add(jugadorListJugadorToAttach);
            }
            premio.setJugadorList(attachedJugadorList);
            em.persist(premio);
            if (numRonda != null) {
                numRonda.getPremioList().add(premio);
                numRonda = em.merge(numRonda);
            }
            for (Jugador jugadorListJugador : premio.getJugadorList()) {
                Premio oldIdPremioOfJugadorListJugador = jugadorListJugador.getIdPremio();
                jugadorListJugador.setIdPremio(premio);
                jugadorListJugador = em.merge(jugadorListJugador);
                if (oldIdPremioOfJugadorListJugador != null) {
                    oldIdPremioOfJugadorListJugador.getJugadorList().remove(jugadorListJugador);
                    oldIdPremioOfJugadorListJugador = em.merge(oldIdPremioOfJugadorListJugador);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Premio premio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Premio persistentPremio = em.find(Premio.class, premio.getIdPremio());
            Ronda numRondaOld = persistentPremio.getNumRonda();
            Ronda numRondaNew = premio.getNumRonda();
            List<Jugador> jugadorListOld = persistentPremio.getJugadorList();
            List<Jugador> jugadorListNew = premio.getJugadorList();
            List<String> illegalOrphanMessages = null;
            for (Jugador jugadorListOldJugador : jugadorListOld) {
                if (!jugadorListNew.contains(jugadorListOldJugador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Jugador " + jugadorListOldJugador + " since its idPremio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (numRondaNew != null) {
                numRondaNew = em.getReference(numRondaNew.getClass(), numRondaNew.getIdRonda());
                premio.setNumRonda(numRondaNew);
            }
            List<Jugador> attachedJugadorListNew = new ArrayList<Jugador>();
            for (Jugador jugadorListNewJugadorToAttach : jugadorListNew) {
                jugadorListNewJugadorToAttach = em.getReference(jugadorListNewJugadorToAttach.getClass(), jugadorListNewJugadorToAttach.getIdJugador());
                attachedJugadorListNew.add(jugadorListNewJugadorToAttach);
            }
            jugadorListNew = attachedJugadorListNew;
            premio.setJugadorList(jugadorListNew);
            premio = em.merge(premio);
            if (numRondaOld != null && !numRondaOld.equals(numRondaNew)) {
                numRondaOld.getPremioList().remove(premio);
                numRondaOld = em.merge(numRondaOld);
            }
            if (numRondaNew != null && !numRondaNew.equals(numRondaOld)) {
                numRondaNew.getPremioList().add(premio);
                numRondaNew = em.merge(numRondaNew);
            }
            for (Jugador jugadorListNewJugador : jugadorListNew) {
                if (!jugadorListOld.contains(jugadorListNewJugador)) {
                    Premio oldIdPremioOfJugadorListNewJugador = jugadorListNewJugador.getIdPremio();
                    jugadorListNewJugador.setIdPremio(premio);
                    jugadorListNewJugador = em.merge(jugadorListNewJugador);
                    if (oldIdPremioOfJugadorListNewJugador != null && !oldIdPremioOfJugadorListNewJugador.equals(premio)) {
                        oldIdPremioOfJugadorListNewJugador.getJugadorList().remove(jugadorListNewJugador);
                        oldIdPremioOfJugadorListNewJugador = em.merge(oldIdPremioOfJugadorListNewJugador);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = premio.getIdPremio();
                if (findPremio(id) == null) {
                    throw new NonexistentEntityException("The premio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Premio premio;
            try {
                premio = em.getReference(Premio.class, id);
                premio.getIdPremio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The premio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Jugador> jugadorListOrphanCheck = premio.getJugadorList();
            for (Jugador jugadorListOrphanCheckJugador : jugadorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Premio (" + premio + ") cannot be destroyed since the Jugador " + jugadorListOrphanCheckJugador + " in its jugadorList field has a non-nullable idPremio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ronda numRonda = premio.getNumRonda();
            if (numRonda != null) {
                numRonda.getPremioList().remove(premio);
                numRonda = em.merge(numRonda);
            }
            em.remove(premio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Premio> findPremioEntities() {
        return findPremioEntities(true, -1, -1);
    }

    public List<Premio> findPremioEntities(int maxResults, int firstResult) {
        return findPremioEntities(false, maxResults, firstResult);
    }

    private List<Premio> findPremioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Premio.class));
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

    public Premio findPremio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Premio.class, id);
        } finally {
            em.close();
        }
    }

    public int getPremioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Premio> rt = cq.from(Premio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
