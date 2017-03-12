/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Posting;
import java.util.List;


/**
 *
 * @author Vito Corleone
 */
public interface PostingDAO {

    void create(Posting posting);

    void edit(Posting posting);

    Posting find(Long postingId);
    
    List<Posting> findPostings(String author);

    void remove(Long postingId);
}
