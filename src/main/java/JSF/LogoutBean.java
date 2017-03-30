/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSF;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Vito
 */
@Named(value = "logoutBean")
@SessionScoped
public class LogoutBean implements Serializable {

    private static Logger log = Logger.getLogger(LogoutBean.class.getName());

    public void logout() {
        String result = "http://localhost:8080/Kwetter/faces/index.xhtml";
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            request.logout();
            externalContext.invalidateSession();
            externalContext.redirect(result);
        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to logout user!", e);
            result = "http://localhost:8080/Kwetter/faces/Error/loginerror.xhtml";
            try {
                externalContext.redirect(result);
            } catch (IOException ex) {
                Logger.getLogger(LogoutBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(LogoutBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean loginStatus() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = ctx.getExternalContext();
        if (ectx.getUserPrincipal() != null) {
            return true;
        }
        return false;
    }
}
