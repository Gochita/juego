/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Premio;
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
        if (ronda.getPremioList() == null) {
            ronda.setPremioList(new ArrayList<Premio>());
        }
        if (ronda.getPreguntaList() == null) {
            ronda.setPreguntaList(new ArrayList<Pregunta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Premio> attachedPremioList = new ArrayList<Premio>();
            for (Premio premioListPremioToAttach : ronda.getPremioList()) {
                premioListPremioToAttach = em.getReference(premioListPremioToAttach.getClass(), premioListPremioToAttach.getIdPremio());
                attachedPremioList.add(premioListPremioToAttach);
            }
            ronda.setPremioList(attachedPremioList);
            List<Pregunta> attachedPreguntaList = new ArrayList<Pregunta>();
            for (Pregunta preguntaListPreguntaToAttach : ronda.getPreguntaList()) {
                preguntaListPreguntaToAttach = em.getReference(preguntaListPreguntaToAttach.getClass(), preguntaListPreguntaToAttach.getIdPregunta());
                attachedPreguntaList.add(preguntaListPreguntaToAttach);
            }
            ronda.setPreguntaList(attachedPreguntaList);
            em.persist(ronda);
            for (Premio premioListPremio : ronda.getPremioList()) {
                Ronda oldNumRondaOfPremioListPremio = premioListPremio.getNumRonda();
                premioListPremio.setNumRonda(ronda);
                premioListPremio = em.merge(premioListPremio);
                if (oldNumRondaOfPremioListPremio != null) {
                    oldNumRondaOfPremioListPremio.getPremioList().remove(premioListPremio);
                    oldNumRondaOfPremioListPremio = em.merge(oldNumRondaOfPremioListPremio);
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
            List<Premio> premioListOld = persistentRonda.getPremioList();
            List<Premio> premioListNew = ronda.getPremioList();
            List<Pregunta> preguntaListOld = persistentRonda.getPreguntaList();
            List<Pregunta> preguntaListNew = ronda.getPreguntaList();
            List<String> illegalOrphanMessages = null;
            for (Premio premioListOldPremio : premioListOld) {
                if (!premioListNew.contains(premioListOldPremio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Premio " + premioListOldPremio + " since its numRonda field is not nullable.");
                }
            }
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
            List<Premio> attachedPremioListNew = new ArrayList<Premio>();
            for (Premio premioListNewPremioToAttach : premioListNew) {
                premioListNewPremioToAttach = em.getReference(premioListNewPremioToAttach.getClass(), premioListNewPremioToAttach.getIdPremio());
                attachedPremioListNew.add(premioListNewPremioToAttach);
            }
            premioListNew = attachedPremioListNew;
            ronda.setPremioList(premioListNew);
            List<Pregunta> attachedPreguntaListNew = new ArrayList<Pregunta>();
            for (Pregunta preguntaListNewPreguntaToAttach : preguntaListNew) {
                preguntaListNewPreguntaToAttach = em.getReference(preguntaListNewPreguntaToAttach.getClass(), preguntaListNewPreguntaToAttach.getIdPregunta());
                attachedPreguntaListNew.add(preguntaListNewPreguntaToAttach);
            }
            preguntaListNew = attachedPreguntaListNew;
            ronda.setPreguntaList(preguntaListNew);
            ronda = em.merge(ronda);
            for (Premio premioListNewPremio : premioListNew) {
                if (!premioListOld.contains(premioListNewPremio)) {
                    Ronda oldNumRondaOfPremioListNewPremio = premioListNewPremio.getNumRonda();
                    premioListNewPremio.setNumRonda(ronda);
                    premioListNewPremio = em.merge(premioListNewPremio);
                    if (oldNumRondaOfPremioListNewPremio != null && !oldNumRondaOfPremioListNewPremio.equals(ronda)) {
                        oldNumRondaOfPremioListNewPremio.getPremioList().remove(premioListNewPremio);
                        oldNumRondaOfPremioListNewPremio = em.merge(oldNumRondaOfPremioListNewPremio);
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
            List<Premio> premioListOrphanCheck = ronda.getPremioList();
            for (Premio premioListOrphanCheckPremio : premioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ronda (" + ronda + ") cannot be destroyed since the Premio " + premioListOrphanCheckPremio + " in its premioList field has a non-nullable numRonda field.");
            }
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
