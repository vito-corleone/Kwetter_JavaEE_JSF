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
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import service.PostingService;
import service.UserService;

/**
 *
 * @author Vito
 */
@Named(value = "userPublicBean")
@SessionScoped
public class userPublicBean implements Serializable {

    public userPublicBean() {

    }

    @Inject
    private UserService userService;

    @Inject
    private PostingService postingService;

    private String emailAddress; // user object

    private User user;

    //private User selectedUser;
    // user request to read the provided url-parameters
    private HttpServletRequest request;

    // Implement proper validation for xss and sqli
    public User getUser() {
        if (user == null) {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            this.emailAddress = request.getParameter("emailAddress");
            if (!emailAddress.isEmpty()) {
                user = userService.find(emailAddress);
                if (user != null) {
                    return user;
                }
            }
        } else {
            return user;
        }
        return null;
    }
    // USER INFO

    public void setEmailAddress(String emailAddress) {
        if (!emailAddress.isEmpty()) {
            this.emailAddress = emailAddress;
        }
    }

    public String getEmailAddress() {
        if (getUser() != null) {
            return user.getEmailAddress();
        }
        return "No email found";
    }

    // PROFILE DETAILS
    public String getName() {
        if (getUser() != null) {
            return user.getName();
        }
        return "No name found";
    }

    public String setName(String name) {
        if (getUser() != null) {
            user.setName(name);
            userService.edit(user);
            return user.getName();
        }
        return "false";
    }

    public String getPhotoPath() {
        if (getUser() != null) {
            return user.getPhotoPath();
        }
        return "http://www.denieuwereporter.nl/wp-content/uploads/2015/06/twitter.jpg";
    }

    // USER DETAILS
    public String getUserBio() {
        if (getUser() != null) {
            return user.getBio();
        }
        return "No bio found";
    }

    public String getUserLocation() {
        if (getUser() != null) {
            return user.getLocation();
        }
        return "No location found";
    }

    public String getUserWebsite() {
        if (getUser() != null) {
            return user.getWebsite();
        }
        return "No website found";
    }

    // Followers
    public List<User> getPeopleIFollow() {
        if (getUser() != null) {
            return user.getThePeopleThatIFollow();
        }
        return new ArrayList<>();
    }

    public List<User> getPeopleThatFollowMe() {
        if (getUser() != null) {
            return user.getThePeopleThatFollowMe();
        }
        return new ArrayList<>();
    }

//    public void setSelectedUser(User user) {
//        this.selectedUser = user;
//    }
//
//    public User getSelectedUser() {
//        return this.selectedUser;
//    }
    public List<Posting> getMyPostings() {
        if (getUser() != null) {
            return postingService.find(this.user.getEmailAddress());
        }
        return new ArrayList<>();
    }

    public List<String> getTrends() {
        List<String> trends = new ArrayList<>();
        trends.add("#YoLo");
        trends.add("#Blind");
        trends.add("#NoWAaaay");
        trends.add("#Awesome man");
        trends.add("#Hello Kitty");
        trends.add("#neverever_again");
        trends.add("#earthHour");
        trends.add("#JavaSF");
        trends.add("#StarWars");
        return trends;
    }
}
