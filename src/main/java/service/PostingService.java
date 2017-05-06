/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Dao.PostingDAO;
import Model.Posting;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Vito Corleone
 */
@Stateless
public class PostingService implements Serializable {

    @Inject
    private PostingDAO postingDAO;

    public PostingService() {

    }

    public void create(Posting posting) {
        if (posting != null) {
            postingDAO.create(posting);
        }
    }

    public void edit(Posting posting) {
        if (posting != null) {
            postingDAO.edit(posting);
        }
    }

    public Posting find(Long postingId) {
        if (postingId > 0) {
            return postingDAO.find(postingId);
        }
        return null;
    }

    public List<Posting> find(String userEmailaddress) {
        if (!userEmailaddress.isEmpty()) {
            return postingDAO.findPostings(userEmailaddress);
        }
        return null;
    }

    public List<Posting> searchPosting(String keyword) {
        if (!keyword.isEmpty()) {
            return postingDAO.searchPosting(keyword);
        }
        return null;
    }

    public void remove(Long postingId) {
        if (postingId > 0) {
            postingDAO.remove(postingId);
        }
    }

    public void removeComment(Long commentId) {
        if (commentId > 0) {
            postingDAO.removeComment(commentId);
        }
    }

    public List<Posting> getAllPostings() {
        return postingDAO.getAllPostings();
    }
}
