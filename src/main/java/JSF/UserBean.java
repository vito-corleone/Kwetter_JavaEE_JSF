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
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
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
@ManagedBean
@SessionScoped
public class UserBean implements Serializable {
    
    @Inject
    private UserService userService;

    private User user;

    public User getUser() {
        if (user == null) {
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            if (principal != null) {
                user = userService.find(principal.getName()); // Find User by j_username.
            }
        }
        return user;
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

}
