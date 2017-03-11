/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Dao.UserDAO;
import Dao.UserDAO_JPA;
import Model.User;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Vito Corleone
 */
public class UserService {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("KwetterPU_Enterprise");
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO_JPA(emf.createEntityManager());
    }

    public void create(User user) {
        if (user != null) {
            userDAO.create(user);
        }
    }

    public void edit(User user) {
        if (user != null) {
            userDAO.edit(user);
        }
    }

    public User find(Long userId) {
        if (userId > 0) {
            return userDAO.find(userId);
        }
        return null;
    }

    public void remove(Long id) {
        if (id > 0) {
            userDAO.remove(id);
        }
    }
}
