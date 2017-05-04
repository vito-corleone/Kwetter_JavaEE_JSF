/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Model.Anon_Posting;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Vito
 */
@Stateless
public class Anon_Posting_JPA implements AnonPosting {

    @PersistenceContext(unitName = "KwetterPU_Enterprise")
    private EntityManager em;

    @Override
    public void createPosting(Anon_Posting anonPosting) {
        em.persist(anonPosting);
    }

    @Override
    public List<Anon_Posting> getAllAnonPostings() {
        Query q = em.createNamedQuery("Anon_Posting.getAll", Anon_Posting.class);
        List<Anon_Posting> foundPostings = (List<Anon_Posting>) q.getResultList();
        return foundPostings;
    }

    
}
