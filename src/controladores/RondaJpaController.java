/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Jugador;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Pregunta;
import modelo.Ronda;

/**
 *
 * @author Tamagochita
 */
public class RondaJpaController implements Serializable {

    public RondaJpaController() {
          this.emf = Persistence.createEntityManagerFactory("juegoPreguntasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ronda ronda) throws PreexistingEntityException, Exception {
        if (ronda.getJugadorList() == null) {
            ronda.setJugadorList(new ArrayList<Jugador>());
        }
        if (ronda.getPreguntaList() == null) {
            ronda.setPreguntaList(new ArrayList<Pregunta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Jugador> attachedJugadorList = new ArrayList<Jugador>();
            for (Jugador jugadorListJugadorToAttach : ronda.getJugadorList()) {
                jugadorListJugadorToAttach = em.getReference(jugadorListJugadorToAttach.getClass(), jugadorListJugadorToAttach.getIdJugador());
                attachedJugadorList.add(jugadorListJugadorToAttach);
            }
            ronda.setJugadorList(attachedJugadorList);
            List<Pregunta> attachedPreguntaList = new ArrayList<Pregunta>();
            for (Pregunta preguntaListPreguntaToAttach : ronda.getPreguntaList()) {
                preguntaListPreguntaToAttach = em.getReference(preguntaListPreguntaToAttach.getClass(), preguntaListPreguntaToAttach.getIdPregunta());
                attachedPreguntaList.add(preguntaListPreguntaToAttach);
            }
            ronda.setPreguntaList(attachedPreguntaList);
            em.persist(ronda);
            for (Jugador jugadorListJugador : ronda.getJugadorList()) {
                Ronda oldIdRondaOfJugadorListJugador = jugadorListJugador.getIdRonda();
                jugadorListJugador.setIdRonda(ronda);
                jugadorListJugador = em.merge(jugadorListJugador);
                if (oldIdRondaOfJugadorListJugador != null) {
                    oldIdRondaOfJugadorListJugador.getJugadorList().remove(jugadorListJugador);
                    oldIdRondaOfJugadorListJugador = em.merge(oldIdRondaOfJugadorListJugador);
                }
            }
            for (Pregunta preguntaListPregunta : ronda.getPreguntaList()) {
                Ronda oldIdRondaOfPreguntaListPregunta = preguntaListPregunta.getIdRonda();
                preguntaListPregunta.setIdRonda(ronda);
                preguntaListPregunta = em.merge(preguntaListPregunta);
                if (oldIdRondaOfPreguntaListPregunta != null) {
                    oldIdRondaOfPreguntaListPregunta.getPreguntaList().remove(preguntaListPregunta);
                    oldIdRondaOfPreguntaListPregunta = em.merge(oldIdRondaOfPreguntaListPregunta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRonda(ronda.getIdRonda()) != null) {
                throw new PreexistingEntityException("Ronda " + ronda + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ronda ronda) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ronda persistentRonda = em.find(Ronda.class, ronda.getIdRonda());
            List<Jugador> jugadorListOld = persistentRonda.getJugadorList();
            List<Jugador> jugadorListNew = ronda.getJugadorList();
            List<Pregunta> preguntaListOld = persistentRonda.getPreguntaList();
            List<Pregunta> preguntaListNew = ronda.getPreguntaList();
            List<String> illegalOrphanMessages = null;
            for (Pregunta preguntaListOldPregunta : preguntaListOld) {
                if (!preguntaListNew.contains(preguntaListOldPregunta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pregunta " + preguntaListOldPregunta + " since its idRonda field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Jugador> attachedJugadorListNew = new ArrayList<Jugador>();
            for (Jugador jugadorListNewJugadorToAttach : jugadorListNew) {
                jugadorListNewJugadorToAttach = em.getReference(jugadorListNewJugadorToAttach.getClass(), jugadorListNewJugadorToAttach.getIdJugador());
                attachedJugadorListNew.add(jugadorListNewJugadorToAttach);
            }
            jugadorListNew = attachedJugadorListNew;
            ronda.setJugadorList(jugadorListNew);
            List<Pregunta> attachedPreguntaListNew = new ArrayList<Pregunta>();
            for (Pregunta preguntaListNewPreguntaToAttach : preguntaListNew) {
                preguntaListNewPreguntaToAttach = em.getReference(preguntaListNewPreguntaToAttach.getClass(), preguntaListNewPreguntaToAttach.getIdPregunta());
                attachedPreguntaListNew.add(preguntaListNewPreguntaToAttach);
            }
            preguntaListNew = attachedPreguntaListNew;
            ronda.setPreguntaList(preguntaListNew);
            ronda = em.merge(ronda);
            for (Jugador jugadorListOldJugador : jugadorListOld) {
                if (!jugadorListNew.contains(jugadorListOldJugador)) {
                    jugadorListOldJugador.setIdRonda(null);
                    jugadorListOldJugador = em.merge(jugadorListOldJugador);
                }
            }
            for (Jugador jugadorListNewJugador : jugadorListNew) {
                if (!jugadorListOld.contains(jugadorListNewJugador)) {
                    Ronda oldIdRondaOfJugadorListNewJugador = jugadorListNewJugador.getIdRonda();
                    jugadorListNewJugador.setIdRonda(ronda);
                    jugadorListNewJugador = em.merge(jugadorListNewJugador);
                    if (oldIdRondaOfJugadorListNewJugador != null && !oldIdRondaOfJugadorListNewJugador.equals(ronda)) {
                        oldIdRondaOfJugadorListNewJugador.getJugadorList().remove(jugadorListNewJugador);
                        oldIdRondaOfJugadorListNewJugador = em.merge(oldIdRondaOfJugadorListNewJugador);
                    }
                }
            }
            for (Pregunta preguntaListNewPregunta : preguntaListNew) {
                if (!preguntaListOld.contains(preguntaListNewPregunta)) {
                    Ronda oldIdRondaOfPreguntaListNewPregunta = preguntaListNewPregunta.getIdRonda();
                    preguntaListNewPregunta.setIdRonda(ronda);
                    preguntaListNewPregunta = em.merge(preguntaListNewPregunta);
                    if (oldIdRondaOfPreguntaListNewPregunta != null && !oldIdRondaOfPreguntaListNewPregunta.equals(ronda)) {
                        oldIdRondaOfPreguntaListNewPregunta.getPreguntaList().remove(preguntaListNewPregunta);
                        oldIdRondaOfPreguntaListNewPregunta = em.merge(oldIdRondaOfPreguntaListNewPregunta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ronda.getIdRonda();
                if (findRonda(id) == null) {
                    throw new NonexistentEntityException("The ronda with id " + id + " no longer exists.");
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
            Ronda ronda;
            try {
                ronda = em.getReference(Ronda.class, id);
                ronda.getIdRonda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ronda with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pregunta> preguntaListOrphanCheck = ronda.getPreguntaList();
            for (Pregunta preguntaListOrphanCheckPregunta : preguntaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ronda (" + ronda + ") cannot be destroyed since the Pregunta " + preguntaListOrphanCheckPregunta + " in its preguntaList field has a non-nullable idRonda field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Jugador> jugadorList = ronda.getJugadorList();
            for (Jugador jugadorListJugador : jugadorList) {
                jugadorListJugador.setIdRonda(null);
                jugadorListJugador = em.merge(jugadorListJugador);
            }
            em.remove(ronda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ronda> findRondaEntities() {
        return findRondaEntities(true, -1, -1);
    }

    public List<Ronda> findRondaEntities(int maxResults, int firstResult) {
        return findRondaEntities(false, maxResults, firstResult);
    }

    private List<Ronda> findRondaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ronda.class));
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

    public Ronda findRonda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ronda.class, id);
        } finally {
            em.close();
        }
    }

    public int getRondaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ronda> rt = cq.from(Ronda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
