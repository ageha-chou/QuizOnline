/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.tbl_answer;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Delwyn
 */
public class TblAnswerBLO implements Serializable{

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SE140622_QuizOnlineWSPU");

    public void persist(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public boolean createAnswers(int questionId, List<TblAnswer> answers){
        EntityManager em = emf.createEntityManager();

        for (TblAnswer ans : answers) {
            em.getTransaction().begin();;
            em.persist(ans);
            em.getTransaction().commit();
        }
        return true;
    }
    
    public List getAnswers(int questionId){
        EntityManager em = emf.createEntityManager();
        
        String jpql = "SELECT t FROM TblAnswer t WHERE t.questionId.id = :quesId";
        Query query = em.createQuery(jpql);
        query.setParameter("quesId", questionId);
        List ans = query.getResultList();
        return ans;
    }
    
    public void updateAnswers(int questionId, List<TblAnswer> newAns){
        EntityManager em = emf.createEntityManager();
        List<TblAnswer> ans = getAnswers(questionId);
        int count = 0;
        //update questList
        for (TblAnswer a : ans) {
            em.getTransaction().begin();
            a.setContent(newAns.get(count).getContent());
            a.setIsRight(newAns.get(count).getIsRight());
            em.merge(a);
            em.getTransaction().commit();
            count++;
        }
    }
}
