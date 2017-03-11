/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import Model.User;

/**
 *
 * @author Vito Corleone
 */
public class UserDAO_JPA implements UserDAO {

    private final EntityManager em;

    public UserDAO_JPA(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(User user) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        try {
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public void edit(User user) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        try {
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    @Override
    public User find(Long userId) {
        Query q = em.createNamedQuery("User.findByID", User.class);
        q.setMaxResults(1);
        q.setParameter("userId", userId);
        User foundUser = (User) q.getSingleResult();
        if (foundUser == null) {
            return null;
        }
        return foundUser;
    }

    @Override
    public void remove(Long id) {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
        try {
            User u = em.find(User.class, id);
            em.remove(u);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }
}
