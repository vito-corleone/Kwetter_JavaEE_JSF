/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Dao.UserDAO;
import Model.User;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Vito Corleone
 */
@Stateless
public class UserService implements Serializable{

    public UserService() {
    }

    @Inject
    private UserDAO userDAO;  
    
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
    
        public User find(String emailAddresss) {
        if (!emailAddresss.isEmpty()) {
            return userDAO.find(emailAddresss);
        }
        return null;
    }

    public void remove(Long id) {
        if (id > 0) {
            userDAO.remove(id);
        }
    }
    
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
