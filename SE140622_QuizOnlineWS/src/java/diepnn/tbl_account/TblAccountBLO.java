/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.tbl_account;

import java.io.Serializable;
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
public class TblAccountBLO implements Serializable{
    TblAccount user = null;

    public TblAccount getUser() {
        return user;
    }
    
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
    
    public boolean checkIfEmailExisted(String email){
        EntityManager em = emf.createEntityManager();
        String jpql = "TblAccount.findByEmail";
        Query query = em.createNamedQuery(jpql);
        query.setParameter("email", email);
        
        try{
            query.getSingleResult();
            return true;
        }catch(NoResultException ex){
            return false;
        }
    }
    
     public TblAccount checkIfMailExisted(String email){
        EntityManager em = emf.createEntityManager();
        String jpql = "TblAccount.findByEmail";
        Query query = em.createNamedQuery(jpql);
        query.setParameter("email", email);
        
        try{
            TblAccount acccount = (TblAccount) query.getSingleResult();
            return acccount;
        }catch(NoResultException ex){
            return null;
        }
    }
    
    public boolean signUp(String email, String password, String fullname){
        EntityManager em = emf.createEntityManager();
        
        TblAccount account = new TblAccount();
        account.setEmail(email);
        account.setPassword(password);
        account.setName(fullname);
        
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();
        return true;
    }
    
    public boolean checkLogin(String email, String password){
        EntityManager em = emf.createEntityManager();
        
        String jpql = "SELECT t FROM TblAccount t "
                + "WHERE t.email = :email AND t.password = :password";
            Query query = em.createQuery(jpql);
            query.setParameter("email", email);
            query.setParameter("password", password);
            try{
                user = (TblAccount) query.getSingleResult();
                user.setPassword("");
                return true;
            }catch(NoResultException ex){
                return false;
            }
    }
}
