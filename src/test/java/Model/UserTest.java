/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
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
public class UserTest {
    
   // TEST DATA
    User user1 = new User("User1", "user1@kwetter.com", "Pass1");
    User user2 = new User("User2", "user2@kwetter.com", "Pass2");
    User user3 = new User("User3", "user3@kwetter.com", "Pass3");
    User user4 = new User("User4", "user4@kwetter.com", "Pass4");
    User user5 = new User("User5", "user5@kwetter.com", "Pass5");
    User user6 = new User("User6", "user6@kwetter.com", "Pass6");
    User user7 = new User("User7", "user7@kwetter.com", "Pass7");
    User user8 = new User("User8", "user8@kwetter.com", "Pass8");
    User user9 = new User("User9", "user9@kwetter.com", "Pass9");
    User user10 = new User("User10", "user1@kwetter.com", "Pass10");

    // ONLY IMPORTANT METHODS TESTED    
    /**
     * Test of getThePeopleThatIFollow method, of class User.
     */
    @Test
    public void testGetFollowing() {
        System.out.println("getFollowing");
        // not following anybody
        assertEquals(0, user1.getThePeopleThatIFollow().size());

        // adding one to follow
        assertTrue(user1.addUserThatIWantToFollow(user2));
        assertEquals(1, user1.getThePeopleThatIFollow().size());

        // addings the same user again -> should not be possible
        assertFalse(user1.addUserThatIWantToFollow(user2)); // user already added to follow

        // add new users
        assertTrue(user1.addUserThatIWantToFollow(user3));
        assertTrue(user1.addUserThatIWantToFollow(user4));
        assertTrue(user1.addUserThatIWantToFollow(user5));
        assertTrue(user1.addUserThatIWantToFollow(user6));
        assertTrue(user1.addUserThatIWantToFollow(user7));
        assertTrue(user1.addUserThatIWantToFollow(user8));
        assertTrue(user1.addUserThatIWantToFollow(user9));

        // added total of eight friends
        assertEquals(8, user1.getThePeopleThatIFollow().size());

        // verify that user10 is not added and has no people that follow him
        assertEquals(0, user10.getThePeopleThatFollowMe().size());

        // add user10 to user1 list of people that he follows
        assertTrue(user1.addUserThatIWantToFollow(user10));

        // verify that user10 has indeed gained a follower
        assertEquals(1, user10.getThePeopleThatFollowMe().size());
    }

    /**
     * Test of getThePeopleThatFollowMe method, of class User.
     */
    @Test
    public void testGetFollowers() {
        // not following anybody
        assertEquals(0, user1.getThePeopleThatFollowMe().size());

        // adding one to follow
        assertTrue(user1.addUserThatFollowsMe(user2));
        assertEquals(1, user1.getThePeopleThatFollowMe().size());

        // addings the same user again -> should not be possible
        assertFalse(user1.addUserThatFollowsMe(user2)); // user already added to follow

        // add new users
        assertTrue(user1.addUserThatFollowsMe(user3));
        assertTrue(user1.addUserThatFollowsMe(user4));
        assertTrue(user1.addUserThatFollowsMe(user5));
        assertTrue(user1.addUserThatFollowsMe(user6));
        assertTrue(user1.addUserThatFollowsMe(user7));
        assertTrue(user1.addUserThatFollowsMe(user8));
        assertTrue(user1.addUserThatFollowsMe(user9));

        // added total of eight friends
        assertEquals(8, user1.getThePeopleThatFollowMe().size());

        // verify that user10 has no people that follow him
        assertEquals(0, user10.getThePeopleThatFollowMe().size());

        // add user10 to user1 list of people that he follows
        assertTrue(user1.addUserThatIWantToFollow(user10));

        // verify that user10 has indeed gained a follower
        assertEquals(1, user10.getThePeopleThatFollowMe().size());
    }

    /**
     * Test of deleteUserThatIFollow method, of class User.
     */
    @Test
    public void deleteUserThatIFollow() {
        // not following anybody
        assertEquals(0, user1.getThePeopleThatIFollow().size());

        // adding five to follow and verify
        assertTrue(user1.addUserThatIWantToFollow(user2));
        assertEquals(1, user1.getThePeopleThatIFollow().size());
        assertTrue(user1.addUserThatIWantToFollow(user3));
        assertEquals(2, user1.getThePeopleThatIFollow().size());
        assertTrue(user1.addUserThatIWantToFollow(user4));
        assertEquals(3, user1.getThePeopleThatIFollow().size());
        assertTrue(user1.addUserThatIWantToFollow(user5));
        assertEquals(4, user1.getThePeopleThatIFollow().size());
        assertTrue(user1.addUserThatIWantToFollow(user6));
        assertEquals(5, user1.getThePeopleThatIFollow().size());

        //verify that user3 has indeed one follower
        assertEquals(1, user3.getThePeopleThatFollowMe().size());

        // remove user3
        assertTrue(user1.deleteUserThatIFollow(user3));
        assertEquals(4, user1.getThePeopleThatIFollow().size());

        //verify that user3 has indeed no followers
        assertEquals(0, user3.getThePeopleThatFollowMe().size());
    }

    /**
     * Test of deleteUserThatFollowsMe method, of class User.
     */
    @Test
    public void deleteUserThatFollowsMe() {
        // not follower
        assertEquals(0, user1.getThePeopleThatFollowMe().size());

        // adding five to follow and verify
        assertTrue(user1.addUserThatFollowsMe(user2));
        assertEquals(1, user1.getThePeopleThatFollowMe().size());
        assertTrue(user1.addUserThatFollowsMe(user3));
        assertEquals(2, user1.getThePeopleThatFollowMe().size());
        assertTrue(user1.addUserThatFollowsMe(user4));
        assertEquals(3, user1.getThePeopleThatFollowMe().size());
        assertTrue(user1.addUserThatFollowsMe(user5));
        assertEquals(4, user1.getThePeopleThatFollowMe().size());
        assertTrue(user1.addUserThatFollowsMe(user6));
        assertEquals(5, user1.getThePeopleThatFollowMe().size());

        // remove user3
        assertTrue(user1.deleteUserThatFollowsMe(user3));
        assertEquals(4, user1.getThePeopleThatFollowMe().size());
    }
}
