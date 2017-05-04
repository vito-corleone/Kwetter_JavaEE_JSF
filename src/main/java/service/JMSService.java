/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Dao.AnonPosting;
import Model.Anon_Posting;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Vito
 */
@Stateless
public class JMSService implements Serializable{    

    @Inject
    AnonPosting anon_Posting;
    
    public JMSService(){
        
    }
    
    public void createPosting(Anon_Posting anonPosting) {
        if(anonPosting != null){
            anon_Posting.createPosting(anonPosting);
        }        
    }
    
    public List<Anon_Posting> getAllAnonPostings() {
        return anon_Posting.getAllAnonPostings();
    }
    
}
