/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import modelo.Opcion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Pregunta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Tamagochita
 */
public class OpcionJpaController implements Serializable {

    public OpcionJpaController() {
         this.emf = Persistence.createEntityManagerFactory("juegoPreguntasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Opcion opcion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pregunta idPregunta = opcion.getIdPregunta();
            if (idPregunta != null) {
                idPregunta = em.getReference(idPregunta.getClass(), idPregunta.getIdPregunta());
                opcion.setIdPregunta(idPregunta);
            }
            em.persist(opcion);
            if (idPregunta != null) {
                idPregunta.getOpcionList().add(opcion);
                idPregunta = em.merge(idPregunta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Opcion opcion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Opcion persistentOpcion = em.find(Opcion.class, opcion.getIdOpcion());
            Pregunta idPreguntaOld = persistentOpcion.getIdPregunta();
            Pregunta idPreguntaNew = opcion.getIdPregunta();
            if (idPreguntaNew != null) {
                idPreguntaNew = em.getReference(idPreguntaNew.getClass(), idPreguntaNew.getIdPregunta());
                opcion.setIdPregunta(idPreguntaNew);
            }
            opcion = em.merge(opcion);
            if (idPreguntaOld != null && !idPreguntaOld.equals(idPreguntaNew)) {
                idPreguntaOld.getOpcionList().remove(opcion);
                idPreguntaOld = em.merge(idPreguntaOld);
            }
            if (idPreguntaNew != null && !idPreguntaNew.equals(idPreguntaOld)) {
                idPreguntaNew.getOpcionList().add(opcion);
                idPreguntaNew = em.merge(idPreguntaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = opcion.getIdOpcion();
                if (findOpcion(id) == null) {
                    throw new NonexistentEntityException("The opcion with id " + id + " no longer exists.");
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
            Opcion opcion;
            try {
                opcion = em.getReference(Opcion.class, id);
                opcion.getIdOpcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The opcion with id " + id + " no longer exists.", enfe);
            }
            Pregunta idPregunta = opcion.getIdPregunta();
            if (idPregunta != null) {
                idPregunta.getOpcionList().remove(opcion);
                idPregunta = em.merge(idPregunta);
            }
            em.remove(opcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Opcion> findOpcionEntities() {
        return findOpcionEntities(true, -1, -1);
    }

    public List<Opcion> findOpcionEntities(int maxResults, int firstResult) {
        return findOpcionEntities(false, maxResults, firstResult);
    }

    private List<Opcion> findOpcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Opcion.class));
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

    public Opcion findOpcion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Opcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getOpcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Opcion> rt = cq.from(Opcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
