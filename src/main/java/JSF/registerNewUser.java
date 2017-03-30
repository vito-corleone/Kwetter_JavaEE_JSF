/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSF;

import Model.User;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;
import service.UserService;

/**
 *
 * @author Vito
 */
@Named(value = "registerNewUser")
@RequestScoped
public class registerNewUser implements Serializable{

    
    @Inject
    UserService userService;
    
    private String emailAddress;    
    private String password;    
    private String username;
    private String result = "Create account";
    
    
    
    /**
     * Creates a new instance of registerNewUser
     */
    public registerNewUser() {
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setemailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    } 
    
    
    public void createUser(){
        User user = new User(this.username, this.emailAddress, this.password);
        userService.create(user);
        this.username = "";
        this.emailAddress = "";
        this.password = "";
        this.result = "Account is made, go to the startpage and login";
    }
    
}
