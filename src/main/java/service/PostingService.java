/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Dao.PostingDAO;
import Dao.PostingDAO_JPA;
import Dao.UserDAO;
import Dao.UserDAO_JPA;
import Model.Posting;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Vito Corleone
 */
public class PostingService {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("KwetterPU_Enterprise");
    private PostingDAO postingDAO;

    public PostingService() {
        postingDAO = new PostingDAO_JPA(emf.createEntityManager());
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

    public void remove(Long postingId) {
        if (postingId > 0) {
            postingDAO.remove(postingId);
        }
    }
}
