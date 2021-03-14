/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.tbl_quiz;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Delwyn
 */
public class TblQuizBLO implements Serializable{

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
    
    public int createQuiz(TblQuiz quiz){
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(quiz);
        em.getTransaction().commit();
        return quiz.getId();
    }
    
    public TblQuiz getQuizById(int qId){
        EntityManager em = emf.createEntityManager();
        String jpql = "TblQuiz.findById";
        Query query = em.createNamedQuery(jpql);
        query.setParameter("id", qId);
        try{
            TblQuiz q = (TblQuiz) query.getSingleResult();
            return q;
        }catch(NoResultException ex){
            return null;
        }
    }
    
    public List getQuizzes(String subjectId, String userId){
        EntityManager em = emf.createEntityManager();
        String subSql = "";
        int subId = 0;
        if(subjectId != null){
            if(!subjectId.isEmpty() && !subjectId.equalsIgnoreCase("all")){
                subSql = " AND t.subjectId.id = :subId";
                subId = Integer.parseInt(subjectId);
            }
        }
        String jpql = "SELECT t FROM TblQuiz t WHERE t.userId.id = :userId" + subSql;
        Query query = em.createQuery(jpql);
        query.setParameter("userId", Integer.parseInt(userId));
        if(!subSql.isEmpty()){
            query.setParameter("subId", subId);
        }
        List<TblQuiz> list = query.getResultList();
        return list;
    }
}
