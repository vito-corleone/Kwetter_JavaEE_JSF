/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import Model.Posting;

/**
 *
 * @author Vito Corleone
 */
public class PostingDAO_JPA implements PostingDAO {

    private final EntityManager em;

    public PostingDAO_JPA(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Posting posting) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        try {
            em.persist(posting);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void edit(Posting posting) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        try {
            em.merge(posting);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public Posting find(Long postingId) {
        Query q = em.createNamedQuery("Posting.findBypostingId", Posting.class);
        q.setMaxResults(1);
        q.setParameter("postingId", postingId);
        Posting foundPost = (Posting) q.getSingleResult();
        if (foundPost == null) {
            return null;
        }
        return foundPost;
    }

    @Override
    public void remove(Long postingId) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        try {
            Posting u = em.find(Posting.class, postingId);
            em.remove(u);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }
}
