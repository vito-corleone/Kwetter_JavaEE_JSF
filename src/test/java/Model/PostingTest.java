/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class PostingTest {
      // TEST POST
    Posting post1 = new Posting("User1", "Content1");

    // ONLY IMPORTANT METHODS TESTED    
    /**
     * Test of addComment method, of class Posting.
     */
    @Test
    public void testAddComment() {
        System.out.println("testAddComment");
        // verify post has no comments
        assertEquals(0, post1.getComments().size());

        // add comments to post
        Comment comment1 = post1.addComment("User2", "Comment1");
        Comment comment2 = post1.addComment("User3", "Comment2");
        Comment comment3 = post1.addComment("User4", "Comment3");
        Comment comment4 = post1.addComment("User5", "Comment4");
        Comment comment5 = post1.addComment("User6", "Comment5");
        Comment comment6 = post1.addComment("User7", "Comment6");

        // verify that post has gained comments
        assertEquals(6, post1.getComments().size());
        
        // verify that comments doesn't exist
        assertFalse(post1.removeComment(123L));
        
        // remove comment
        assertTrue(post1.removeComment(3L));
        
        // verify that comment is removed
        assertEquals(5, post1.getComments().size());
    }
    
}
