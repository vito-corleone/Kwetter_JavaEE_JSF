/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSF;

import Model.Posting;
import Model.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import service.PostingService;
import service.UserService;

/**
 *
 * @author Vito
 */
@Named(value = "postingBean")
@SessionScoped
public class postingBean implements Serializable{

    public postingBean(){
        
    }
    
    @Inject
    PostingService postingService;

    @Inject
    UserService userService;

    private User user;

    //hardcoded for now
    private String emailAddress;

    private User getUser() {
        if (!this.emailAddress.isEmpty()) {
            this.user = userService.find(emailAddress);
            return user;
        }
        return null;
    }

    public List<Posting> getUserPostings(String emailAddress) {
        if (!emailAddress.isEmpty()) {
            return postingService.find(emailAddress);
        }
        return new ArrayList<Posting>();
    }

    public List<Posting> getFollowerPostings() {
        List<Posting> postings = null;
        if (getUser() != null) {
            postings = new ArrayList();
            for (User user : this.user.getThePeopleThatIFollow()) {
                for (Posting post : postingService.find(user.getEmailAddress())) {
                    postings.add(post);
                }
            }
        }
        return postings;
    }

    public String getAuthorPhotoPath(String emailAddress) {
        return userService.find(emailAddress).getPhotoPath();
    }
}
