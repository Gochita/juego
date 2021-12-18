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
import modelo.Categoria;
import modelo.Ronda;
import modelo.Opcion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Pregunta;

/**
 *
 * @author Tamagochita
 */
public class PreguntaJpaController implements Serializable {

    public PreguntaJpaController() {
       this.emf = Persistence.createEntityManagerFactory("juegoPreguntasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pregunta pregunta) {
        if (pregunta.getOpcionList() == null) {
            pregunta.setOpcionList(new ArrayList<Opcion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria idCategoria = pregunta.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getIdCategoria());
                pregunta.setIdCategoria(idCategoria);
            }
            Ronda idRonda = pregunta.getIdRonda();
            if (idRonda != null) {
                idRonda = em.getReference(idRonda.getClass(), idRonda.getIdRonda());
                pregunta.setIdRonda(idRonda);
            }
            List<Opcion> attachedOpcionList = new ArrayList<Opcion>();
            for (Opcion opcionListOpcionToAttach : pregunta.getOpcionList()) {
                opcionListOpcionToAttach = em.getReference(opcionListOpcionToAttach.getClass(), opcionListOpcionToAttach.getIdOpcion());
                attachedOpcionList.add(opcionListOpcionToAttach);
            }
            pregunta.setOpcionList(attachedOpcionList);
            em.persist(pregunta);
            if (idCategoria != null) {
                idCategoria.getPreguntaList().add(pregunta);
                idCategoria = em.merge(idCategoria);
            }
            if (idRonda != null) {
                idRonda.getPreguntaList().add(pregunta);
                idRonda = em.merge(idRonda);
            }
            for (Opcion opcionListOpcion : pregunta.getOpcionList()) {
                Pregunta oldIdPreguntaOfOpcionListOpcion = opcionListOpcion.getIdPregunta();
                opcionListOpcion.setIdPregunta(pregunta);
                opcionListOpcion = em.merge(opcionListOpcion);
                if (oldIdPreguntaOfOpcionListOpcion != null) {
                    oldIdPreguntaOfOpcionListOpcion.getOpcionList().remove(opcionListOpcion);
                    oldIdPreguntaOfOpcionListOpcion = em.merge(oldIdPreguntaOfOpcionListOpcion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pregunta pregunta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pregunta persistentPregunta = em.find(Pregunta.class, pregunta.getIdPregunta());
            Categoria idCategoriaOld = persistentPregunta.getIdCategoria();
            Categoria idCategoriaNew = pregunta.getIdCategoria();
            Ronda idRondaOld = persistentPregunta.getIdRonda();
            Ronda idRondaNew = pregunta.getIdRonda();
            List<Opcion> opcionListOld = persistentPregunta.getOpcionList();
            List<Opcion> opcionListNew = pregunta.getOpcionList();
            List<String> illegalOrphanMessages = null;
            for (Opcion opcionListOldOpcion : opcionListOld) {
                if (!opcionListNew.contains(opcionListOldOpcion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Opcion " + opcionListOldOpcion + " since its idPregunta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCategoriaNew != null) {
                idCategoriaNew = em.getReference(idCategoriaNew.getClass(), idCategoriaNew.getIdCategoria());
                pregunta.setIdCategoria(idCategoriaNew);
            }
            if (idRondaNew != null) {
                idRondaNew = em.getReference(idRondaNew.getClass(), idRondaNew.getIdRonda());
                pregunta.setIdRonda(idRondaNew);
            }
            List<Opcion> attachedOpcionListNew = new ArrayList<Opcion>();
            for (Opcion opcionListNewOpcionToAttach : opcionListNew) {
                opcionListNewOpcionToAttach = em.getReference(opcionListNewOpcionToAttach.getClass(), opcionListNewOpcionToAttach.getIdOpcion());
                attachedOpcionListNew.add(opcionListNewOpcionToAttach);
            }
            opcionListNew = attachedOpcionListNew;
            pregunta.setOpcionList(opcionListNew);
            pregunta = em.merge(pregunta);
            if (idCategoriaOld != null && !idCategoriaOld.equals(idCategoriaNew)) {
                idCategoriaOld.getPreguntaList().remove(pregunta);
                idCategoriaOld = em.merge(idCategoriaOld);
            }
            if (idCategoriaNew != null && !idCategoriaNew.equals(idCategoriaOld)) {
                idCategoriaNew.getPreguntaList().add(pregunta);
                idCategoriaNew = em.merge(idCategoriaNew);
            }
            if (idRondaOld != null && !idRondaOld.equals(idRondaNew)) {
                idRondaOld.getPreguntaList().remove(pregunta);
                idRondaOld = em.merge(idRondaOld);
            }
            if (idRondaNew != null && !idRondaNew.equals(idRondaOld)) {
                idRondaNew.getPreguntaList().add(pregunta);
                idRondaNew = em.merge(idRondaNew);
            }
            for (Opcion opcionListNewOpcion : opcionListNew) {
                if (!opcionListOld.contains(opcionListNewOpcion)) {
                    Pregunta oldIdPreguntaOfOpcionListNewOpcion = opcionListNewOpcion.getIdPregunta();
                    opcionListNewOpcion.setIdPregunta(pregunta);
                    opcionListNewOpcion = em.merge(opcionListNewOpcion);
                    if (oldIdPreguntaOfOpcionListNewOpcion != null && !oldIdPreguntaOfOpcionListNewOpcion.equals(pregunta)) {
                        oldIdPreguntaOfOpcionListNewOpcion.getOpcionList().remove(opcionListNewOpcion);
                        oldIdPreguntaOfOpcionListNewOpcion = em.merge(oldIdPreguntaOfOpcionListNewOpcion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pregunta.getIdPregunta();
                if (findPregunta(id) == null) {
                    throw new NonexistentEntityException("The pregunta with id " + id + " no longer exists.");
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
            Pregunta pregunta;
            try {
                pregunta = em.getReference(Pregunta.class, id);
                pregunta.getIdPregunta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pregunta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Opcion> opcionListOrphanCheck = pregunta.getOpcionList();
            for (Opcion opcionListOrphanCheckOpcion : opcionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pregunta (" + pregunta + ") cannot be destroyed since the Opcion " + opcionListOrphanCheckOpcion + " in its opcionList field has a non-nullable idPregunta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categoria idCategoria = pregunta.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getPreguntaList().remove(pregunta);
                idCategoria = em.merge(idCategoria);
            }
            Ronda idRonda = pregunta.getIdRonda();
            if (idRonda != null) {
                idRonda.getPreguntaList().remove(pregunta);
                idRonda = em.merge(idRonda);
            }
            em.remove(pregunta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pregunta> findPreguntaEntities() {
        return findPreguntaEntities(true, -1, -1);
    }

    public List<Pregunta> findPreguntaEntities(int maxResults, int firstResult) {
        return findPreguntaEntities(false, maxResults, firstResult);
    }

    private List<Pregunta> findPreguntaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pregunta.class));
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

    public Pregunta findPregunta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pregunta.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreguntaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pregunta> rt = cq.from(Pregunta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
