/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSF;

import Model.User;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import service.PostingService;
import service.UserService;

/**
 *
 * @author Vito
 */
@Named(value = "userBean")
@RequestScoped
public class UserBean implements Serializable {

    @Inject
    private UserService userService;

    private String emailAddress = "vito@kwetter.com";
    //private String emailAddress;

    private User user;
    
    private HttpServletRequest request=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();


// METHOD BENEATH IS FOR AUTHENTICATED SESSIONS
//    public User getUser() {
//        if (user == null) {
//            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
//            if (principal != null) {
//                user = userService.find(principal.getName()); // Find User by j_username.
//            }
//        }
//        return user;
//    }
    // USER INFO
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public User getUser() {
        this.emailAddress = request.getParameter("emailAddress");
        if (user == null && !emailAddress.isEmpty()) {
            return userService.find(emailAddress);
        }
        return null;
    }
    
    // PROFILE DETAILS
    public String getName() {
        return getUser().getName();
    }

    public String setName(String name) {
        if (user == null) {
            User user = getUser();
            user.setName(name);
            userService.edit(user);
            return getUser().getName();
        }
        return "false";
    }

    public String getPhotoPath() {
        return getUser().getPhotoPath();
    }

    public User getUser(String emailAddress) {
        return userService.find(emailAddress);
    }

    // USER DETAILS
    public String getUserBio() {
        return getUser().getBio();
    }

    public String getUserLocation() {
        return getUser().getLocation();
    }

    public String getUserWebsite() {
        return getUser().getWebsite();
    }

    // Followers
    public List<User> getPeopleIFollow() {
        return getUser().getThePeopleThatIFollow();
    }

    public List<User> getPeopleThatFollowMe() {
        return getUser().getThePeopleThatFollowMe();
    }
}
