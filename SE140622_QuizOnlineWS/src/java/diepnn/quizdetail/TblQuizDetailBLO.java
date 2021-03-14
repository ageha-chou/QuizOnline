/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.quizdetail;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Delwyn
 */
public class TblQuizDetailBLO implements Serializable{

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
    
    public void createQuizDetail(int quizId, List<Double> questions,
            Map<String, Double> ansList, List<Boolean> results){
        EntityManager em = emf.createEntityManager();
        int count = 0;
        Integer userAns;
        for (Double quesId : questions) {
            em.getTransaction().begin();
            TblQuizDetail detail = new TblQuizDetail();
            detail.setTblQuizDetailPK(new TblQuizDetailPK(quizId, quesId.intValue()));
            
            try{
                userAns = ansList.get(String.valueOf(quesId.intValue())).intValue();
            }catch(NullPointerException ex){
                userAns = null;
            }
            detail.setUserChoice(userAns);
            detail.setIsCorrect(results.get(count++));
            em.persist(detail);
            em.getTransaction().commit();
        }
    }
}
