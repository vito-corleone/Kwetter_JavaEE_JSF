/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Posting;


/**
 *
 * @author Vito Corleone
 */
public interface PostingDAO {

    void create(Posting posting);

    void edit(Posting posting);

    Posting find(Long postingId);

    void remove(Long postingId);
}
