/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSF;

import Model.Comment;
import Model.Posting;
import Model.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import service.PostingService;
import service.UserService;

/**
 *
 * @author Vito
 */
@Named(value = "moderatorBean")
@SessionScoped
public class moderatorBean implements Serializable {

    public moderatorBean() {

    }

    @Inject
    UserService userService;

    @Inject
    PostingService postingService;

    private Posting selectedPosting;
    private Comment selectedComment;
    private List<Comment> commentsFromPost;
    private String emailAddress;
    
    private User user;
    

    public List<Posting> getAllPostings() {
        return postingService.getAllPostings();
    }

    public void setSelectedPost(Posting selectedPosting) {
        this.selectedPosting = selectedPosting;

    }

    public Posting getSelectedPost() {
        return this.selectedPosting;
    }

    public void deletePost() {
        postingService.remove(this.selectedPosting.getId());
    }

    public void setSelectedComment(Comment selectedComment) {
        this.selectedComment = selectedComment;
    }

    public Comment getSelectedComment() {
        return this.selectedComment;
    }

    public void deleteComment() {
        postingService.removeComment(this.selectedComment.getId());
    }

    public List<Comment> getCommentsFromPost() {
        return commentsFromPost;
    }

    public void setCommentsFromPost(List<Comment> commentsFromPost) {
        this.commentsFromPost = commentsFromPost;
    }

    public void getComments() {
        if (selectedPosting != null) {
            this.commentsFromPost = selectedPosting.getComments();
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User findUser() {
        if (!this.emailAddress.isEmpty()) {
            this.user = userService.find(emailAddress);
            if (user != null) {
                return user;
            }
        }
        this.user = new User();
        return this.user;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
