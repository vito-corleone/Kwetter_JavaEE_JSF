/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSF;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Vito
 */
@Named(value = "logoutBean")
public class LogoutBean implements Serializable{

    private static Logger log = Logger.getLogger(LogoutBean.class.getName());

    public String logout() {
        String result = "/Kwetter/faces/index.xhtml";
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to logout user!", e);
            result = "/Kwetter/faces/Error/loginerror.xhtml";
        }
        return result;
    }

}
