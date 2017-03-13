/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exception;

import java.io.Serializable;

/**
 *
 * @author Vito Corleone
 */
public class UserWebServiceException extends Exception implements
        Serializable {

    private static final long serialVersionUID = 1169426381288170661L;

    public UserWebServiceException() {
        super();
    }

    public UserWebServiceException(String msg) {
        super(msg);
    }

    public UserWebServiceException(String msg, Exception e) {
        super(msg, e);
    }

}
