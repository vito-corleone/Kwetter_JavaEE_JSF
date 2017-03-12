/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.User;

/**
 *
 * @author Vito Corleone
 */
public interface UserDAO {

    void create(User user);

    void edit(User user);

    User find(Long userId);
    
    User find(String emailAddress);

    void remove(Long userId);    
}
