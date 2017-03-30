/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSF;

import Model.Comment;
import Model.Posting;
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
public class moderatorBean implements Serializable{
    
    public moderatorBean(){
        
    }

    @Inject
    UserService userService;

    @Inject
    PostingService postingService;

    private Posting selectedPosting;
    private Comment selectedComment;

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
    
    public List<Comment> getCommentsFromPost(){
        if(selectedPosting != null && selectedPosting.getComments().size() > 0){
            return selectedPosting.getComments();
        }
        return new ArrayList<>();
    }
    
    public void setSelectedComment(Comment selectedComment) {
        this.selectedComment = selectedComment;
    }

    public Comment getSelectedComment() {
        return this.selectedComment;
    }

    

}
