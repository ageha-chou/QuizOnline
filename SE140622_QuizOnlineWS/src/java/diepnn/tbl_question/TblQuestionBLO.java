/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.tbl_question;

import diepnn.tbl_account.TblAccount;
import diepnn.tbl_subject.TblSubject;
import java.io.Serializable;
import java.util.Date;
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
public class TblQuestionBLO implements Serializable{

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
    
    public List getRandomQues(String quesId, String limit){
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT a.id, a.content, a.subjectId FROM tbl_Question a where a.id IN "
                + "(SELECT TOP "+ limit +" id from tbl_Question WHERE subjectId = ? AND status = ? order by newid())";
        Query query = em.createNativeQuery(jpql, "QuestionMapping");
        query.setParameter(1, quesId);
        query.setParameter(2, true);
        List<TblQuestion> list = query.getResultList();
        return list;
    }
    
    public int createQuestion(String question, int subjectId, int createdUser){
        EntityManager em = emf.createEntityManager();
        
        TblQuestion q = new TblQuestion();
        q.setContent(question);
        q.setCreatedUser(new TblAccount(createdUser));
        q.setLastModifiedUser(new TblAccount(createdUser));
        q.setSubjectId(new TblSubject(subjectId));
        
        em.getTransaction().begin();
        em.persist(q);
        em.getTransaction().commit();
        return q.getId();
    }
    
    public List getQues(String status, String subjectId, String question){
        EntityManager em = emf.createEntityManager();
        
        String statusQuery = "";
        String subjectQuery = "";
        String quesQuery = "";
        
        int count = 0;
        if(status != null){
            if(!status.isEmpty()) {
                statusQuery = " WHERE t.status = :status ";
                count++;
            }
            
        }
        
        if(subjectId != null){
            if(!subjectId.isEmpty()){
                if(count == 0) subjectQuery = " WHERE t.subjectId.id = :subjectId ";
                else subjectQuery = " AND t.subjectId.id = :subjectId ";
                count++;
            }
        }
        
        if(question != null){
            question = question.replaceAll("%20", " ");
            if(!question.isEmpty()){
                if(count == 0) quesQuery = " WHERE t.content LIKE :content";
                else quesQuery = " AND t.content LIKE :content ";
            }
        }
        
        String jpql = "SELECT t FROM TblQuestion t " + statusQuery + subjectQuery + quesQuery
                + " ORDER BY t.content DESC";

        Query query = em.createQuery(jpql);
        if(!statusQuery.isEmpty())
            query.setParameter("status", Boolean.parseBoolean(status));
        
        if(!subjectQuery.isEmpty()) 
            query.setParameter("subjectId", Integer.parseInt(subjectId));
        
        if(!quesQuery.isEmpty()) 
            query.setParameter("content", "%" + question + "%");
     
        List<TblQuestion> qna = query.getResultList();
        return qna;
    }
    
    public boolean deleteQuestion(int quesId, int userId){
        EntityManager em = emf.createEntityManager();
        TblQuestion ques = em.find(TblQuestion.class, quesId);
        em.getTransaction().begin();
        ques.setStatus(false);
        ques.setLastModifiedUser(new TblAccount(userId));
        em.getTransaction().commit();
        return true;
    }
    
    public TblQuestion getSpecificQuestion(int questId){
        EntityManager em = emf.createEntityManager();
        TblQuestion ques = em.find(TblQuestion.class, questId);
        return ques;
    }
    
    public void updateQuestion(int quesId, String quesContent, int userId, int subjectId, boolean status){
        EntityManager em = emf.createEntityManager();
        TblQuestion ques = em.find(TblQuestion.class, quesId);
        em.getTransaction().begin();
        ques.setContent(quesContent);
        ques.setSubjectId(new TblSubject(subjectId));
        ques.setLastModifiedDate(new Date());
        ques.setStatus(status);
        ques.setLastModifiedUser(new TblAccount(userId));
        em.merge(ques);
        em.getTransaction().commit();
    }
}

