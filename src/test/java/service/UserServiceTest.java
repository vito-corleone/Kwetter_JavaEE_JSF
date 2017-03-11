/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Model.User;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vito Corleone
 */
public class UserServiceTest {

    UserService userService;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        userService = new UserService();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class UserService.
     */
    @Test
    public void testCreate() {
        // create user
        User user1 = new User("User1", "user1@kwetter.com", "Pass1");
        user1.setId(12L);
        userService.create(user1);

        // get user and verify that user is created
        User foundUser = userService.find(12L);
        assertNotNull(foundUser);
        assertEquals("Pass1", foundUser.getPassword());
    }

    /**
     * Test of edit method, of class UserService.
     */
    @Test
    public void testEdit() {
        // create user
        User vito = new User("Vito", "vito@kwetter.com", "PassWord");
        vito.setId(12L);
        userService.create(vito);

        // Verify that location property is not yet set of user object
        assertNull(vito.getLocation());

        // edit user
        vito.setLocation("Roermond");
        userService.edit(vito);

        //Find User through service        
        User foundUser = userService.find(12L);
        assertNotNull(foundUser);

        // verify eddited value of the persisted object
        assertEquals("Roermond", foundUser.getLocation());
    }

    /**
     * Test of find method, of class UserService.
     */
    @Test
    public void testFind() {
        // create user
        User vito = new User("Vito", "vito@kwetter.com", "PassWord");
        vito.setId(12L);
        userService.create(vito);

        // find existing user
        User foundUser = userService.find(12L);
        assertNotNull(foundUser);
        assertEquals("Vito", foundUser.getName());
    }

    /**
     * Test of remove method, of class UserService.
     */
    @Test
    public void testRemove() {
        // create user
        User vito = new User("Vito", "vito@kwetter.com", "PassWord");
        vito.setId(12L);
        userService.create(vito);

        //Find User    
        User foundUser = userService.find(12L);
        assertNotNull(foundUser);
        assertEquals("Vito", foundUser.getName());

        // remove user
        userService.remove(12L);
    }
}
