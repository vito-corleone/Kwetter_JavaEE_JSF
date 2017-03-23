/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSF;

import Model.User;
import java.io.Serializable;
import java.security.Principal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import service.PostingService;
import service.UserService;

/**
 *
 * @author Vito
 */
@Named(value = "userBean")
@ManagedBean
public class UserBean implements Serializable {

    private String emailAddress = "vito@kwetter.com";
    private String name = "Vito";
    private User user; // The JPA entity.

    @Inject
    private UserService userService;

    public User getUser(String emailAddresss) {
        return userService.find(emailAddresss);
    }

    public String getName() {
        return this.name;
    }

    public String setName(String name) {
        this.name = name;
        return this.name;
    }

    public User getUser() {
        if (user == null) {
            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            if (principal != null) {
                user = userService.find(principal.getName()); // Find User by j_username.
            }
        }
        return user;
    }
}
