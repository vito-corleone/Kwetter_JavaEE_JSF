/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import Model.User;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Vito Corleone
 */
@Stateless
public class UserDAO_JPA implements UserDAO {

    @PersistenceContext(unitName = "KwetterPU_Enterprise")
    private EntityManager em;

    public UserDAO_JPA() {

    }

    @Override
    public void create(User user) {
        em.persist(user);
    }

    @Override
    public void edit(User user) {
        em.merge(user);
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
        User u = em.find(User.class, id);
        em.remove(u);
    }

    @Override
    public User find(String emailAddress) {
        Query q = em.createNamedQuery("User.findByEmailAddress", User.class);
        q.setMaxResults(1);
        q.setParameter("emailAddress", emailAddress);
        User foundUser = (User) q.getSingleResult();
        if (foundUser == null) {
            return null;
        }
        return foundUser;    }
}
