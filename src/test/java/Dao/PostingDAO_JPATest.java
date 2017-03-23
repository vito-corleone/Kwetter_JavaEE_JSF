///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Dao;
//
//import Model.Posting;
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
//public class PostingDAO_JPATest {
//
//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("KwetterPU_Local_Resource");
//    EntityManager em;
//    PostingDAO_JPA postingDAO;
//    DatabaseCleaner databaseCleaner;
//   
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
//        postingDAO = new PostingDAO_JPA(em);
//    }
//
//    @After
//    public void tearDown() {
//        try {
//            databaseCleaner = new DatabaseCleaner(em);
//            databaseCleaner.clean();
//        } catch (SQLException ex) {
//            Logger.getLogger(PostingDAO_JPATest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    // ONLY IMPORTANT METHODS TESTED
//    /**
//     * Test of create method, of class PostingDAO_JPA234.
//     */
//    @Test
//    public void testCreate() {
//        // create posting
//        Posting post = new Posting("Vito", "Content");
//        post.setId(12L);
//        postingDAO.create(post);
//
//        // find posting
//        Posting foundPosting = postingDAO.find(12L);
//        assertNotNull(foundPosting);
//        assertEquals("Vito", foundPosting.getAuthor());
//    }
//
//    /**
//     * Test of edit method, of class PostingDAO_JPA234.
//     */
//    @Test
//    public void testEdit() {
//        // create posting
//        Posting post = new Posting("Vito", "Content");
//        post.setId(12L);
//        postingDAO.create(post);
//
//        // edit content
//        post.setContent("Content is editted");
//        postingDAO.edit(post);
//
//        // find posting
//        Posting foundPosting = postingDAO.find(12L);
//        assertNotNull(foundPosting);
//        assertEquals("Content is editted", foundPosting.getContent());
//    }
//
//    /**
//     * Test of find method, of class PostingDAO_JPA234.
//     */
//    @Test
//    public void testFind() {
//        // create posting
//        Posting post = new Posting("Vito", "Content");
//        post.setId(12L);
//        postingDAO.create(post);
//
//        // find posting
//        Posting foundPosting = postingDAO.find(12L);
//        assertNotNull(foundPosting);
//        assertEquals("Vito", foundPosting.getAuthor());
//    }
//
//    /**
//     * Test of remove method, of class PostingDAO_JPA234.
//     */
//    @Test
//    public void testRemove() {
//        // create user
//        Posting post = new Posting("Vito", "Content");
//        post.setId(12L);
//        postingDAO.create(post);
//
//        //Find User    
//        EntityManager em2 = emf.createEntityManager();
//        em2.getTransaction().begin();
//        Posting foundPosting = em.find(Posting.class, post.getId());
//        assertNotNull(foundPosting);
//
//        // remove user
//        postingDAO.remove(12L);
//
//        // try to find deleted user 
//        em2 = emf.createEntityManager();
//        em2.getTransaction().begin();
//        Posting deletedPost = em.find(Posting.class, post.getId());
//        assertNull(deletedPost);
//    }
//
//}
