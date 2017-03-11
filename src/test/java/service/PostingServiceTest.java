/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Model.Posting;
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
public class PostingServiceTest {
    
    PostingService postingService;
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        postingService = new PostingService();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class PostingService.
     */
    @Test
    public void testCreate() {
        // create posting
        Posting post = new Posting("Vito", "Content");
        post.setId(12L);
        postingService.create(post);

        // find posting
        Posting foundPosting = postingService.find(12L);
        assertNotNull(foundPosting);
        assertEquals("Vito", foundPosting.getAuthor());
    }

    /**
     * Test of edit method, of class PostingService.
     */
    @Test
    public void testEdit() {
        // create posting
        Posting post = new Posting("Vito", "Content");
        post.setId(12L);
        postingService.create(post);

        // edit content
        post.setContent("Content is editted");
        postingService.edit(post);

        // find posting
        Posting foundPosting = postingService.find(12L);
        assertNotNull(foundPosting);
        assertEquals("Content is editted", foundPosting.getContent());
    }

    /**
     * Test of find method, of class PostingService.
     */
    @Test
    public void testFind() {
        // create posting
        Posting post = new Posting("Vito", "Content");
        post.setId(12L);
        postingService.create(post);

        // find posting
        Posting foundPosting = postingService.find(12L);
        assertNotNull(foundPosting);
        assertEquals("Vito", foundPosting.getAuthor());
    }

    /**
     * Test of remove method, of class PostingService.
     */
    @Test
    public void testRemove() {
        // create posting
        Posting post = new Posting("Vito", "Content");
        post.setId(12L);
        postingService.create(post);

        // find posting
        Posting foundPosting = postingService.find(12L);
        assertNotNull(foundPosting);
        assertEquals("Vito", foundPosting.getAuthor());

        // remove user
        postingService.remove(12L);
    }    
}
