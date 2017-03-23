///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Dao;
//
//import Model.User;
//import java.sql.SQLException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author Vito Corleone
// */
//public class UserDAO_JPATest {
//
//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("KwetterPU_Local_Resource");
//    EntityManager em;
//    UserDAO_JPA userDAO;
//    DatabaseCleaner databaseCleaner;
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//        em = emf.createEntityManager();
//        userDAO = new UserDAO_JPA(em);
//    }
//
//    @After
//    public void tearDown() {
//        try {
//            databaseCleaner = new DatabaseCleaner(em);
//            databaseCleaner.clean();
//        } catch (SQLException ex) {
//            Logger.getLogger(UserDAO_JPATest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    // ONLY IMPORTANT METHODS TESTED       
//    /**
//     * Test of create method, of class UserDAO1234.
//     */
//    @Test
//    public void testCreate() {
//        // create user
//        User vito = new User("Vito", "vito@kwetter.com", "PassWord");
//        vito.setId(12L);
//        userDAO.create(vito);
//
//        // find user
//        User findUserVito = userDAO.find(12L);
//        assertNotNull(findUserVito);
//    }
//
//    /**
//     * Test of edit method, of class UserDAO1234.
//     */
//    @Test
//    public void testEdit() {
//        // create user
//        User vito = new User("Vito", "vito@kwetter.com", "PassWord");
//        vito.setId(12L);
//        userDAO.create(vito);
//
//        // Verify that location property is not yet set of user object
//        assertNull(vito.getLocation());
//
//        // edit user
//        vito.setLocation("Roermond");
//        userDAO.edit(vito);
//
//        //Find User through entity        
//        EntityManager em2 = emf.createEntityManager();
//        em2.getTransaction().begin();
//        User findUserVito = em.find(User.class, vito.getId());
//        assertNotNull(findUserVito);
//
//        // verify eddited value of the persisted object
//        assertEquals("Roermond", findUserVito.getLocation());
//    }
//
//    /**
//     * Test of find method, of class UserDAO1234.
//     */
//    @Test
//    public void testFind() {
//        // create user
//        User vito = new User("Vito", "vito@kwetter.com", "PassWord");
//        vito.setId(12L);
//        userDAO.create(vito);
//
//        // find existing user
//        User foundUser = userDAO.find(12L);
//    }
//
//    /**
//     * Test of remove method, of class UserDAO1234.
//     */
//    @Test
//    public void testRemove() {
//        // create user
//        User vito = new User("Vito", "vito@kwetter.com", "PassWord");
//        vito.setId(12L);
//        userDAO.create(vito);
//
//        //Find User    
//        EntityManager em2 = emf.createEntityManager();
//        em2.getTransaction().begin();
//        User findUserVito = em.find(User.class, vito.getId());
//        assertNotNull(findUserVito);
//
//        // remove user
//        userDAO.remove(12L);
//
//        // try to find deleted user 
//        em2 = emf.createEntityManager();
//        em2.getTransaction().begin();
//        User deletedUser = em.find(User.class, 12L);
//        assertNull(deletedUser);
//    }
//}
