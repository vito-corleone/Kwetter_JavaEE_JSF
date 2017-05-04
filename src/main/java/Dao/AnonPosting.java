/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Anon_Posting;
import java.util.List;

/**
 *
 * @author Vito
 */
public interface AnonPosting {

    void createPosting(Anon_Posting anonPosting);

    List<Anon_Posting> getAllAnonPostings();
}
