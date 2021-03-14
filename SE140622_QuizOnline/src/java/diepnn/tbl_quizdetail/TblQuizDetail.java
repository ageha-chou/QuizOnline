/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.tbl_quizdetail;

import diepnn.tbl_question.TblQuestion;
import diepnn.tbl_quiz.TblQuiz;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Delwyn
 */
@Entity
@Table(name = "tbl_QuizDetail", catalog = "QuizOnline", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblQuizDetail.findAll", query = "SELECT t FROM TblQuizDetail t")
    , @NamedQuery(name = "TblQuizDetail.findByQuizId", query = "SELECT t FROM TblQuizDetail t WHERE t.tblQuizDetailPK.quizId = :quizId")
    , @NamedQuery(name = "TblQuizDetail.findByQuestionId", query = "SELECT t FROM TblQuizDetail t WHERE t.tblQuizDetailPK.questionId = :questionId")
    , @NamedQuery(name = "TblQuizDetail.findByIsCorrect", query = "SELECT t FROM TblQuizDetail t WHERE t.isCorrect = :isCorrect")})
public class TblQuizDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TblQuizDetailPK tblQuizDetailPK;
    @Column(name = "isCorrect")
    private Boolean isCorrect;
    @Column(name = "userChoice")
    private Integer userChoice;
    @JoinColumn(name = "questionId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TblQuestion tblQuestion;
    @JoinColumn(name = "quizId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TblQuiz tblQuiz;

    public TblQuizDetail() {
    }

    public TblQuizDetail(TblQuizDetailPK tblQuizDetailPK) {
        this.tblQuizDetailPK = tblQuizDetailPK;
    }

    public TblQuizDetail(int quizId, int questionId) {
        this.tblQuizDetailPK = new TblQuizDetailPK(quizId, questionId);
    }

    public TblQuizDetailPK getTblQuizDetailPK() {
        return tblQuizDetailPK;
    }

    public void setTblQuizDetailPK(TblQuizDetailPK tblQuizDetailPK) {
        this.tblQuizDetailPK = tblQuizDetailPK;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getUserChoice() {
        return userChoice;
    }

    public void setUserChoice(Integer userChoice) {
        this.userChoice = userChoice;
    }
    
    public TblQuestion getTblQuestion() {
        return tblQuestion;
    }

    public void setTblQuestion(TblQuestion tblQuestion) {
        this.tblQuestion = tblQuestion;
    }

    public TblQuiz getTblQuiz() {
        return tblQuiz;
    }

    public void setTblQuiz(TblQuiz tblQuiz) {
        this.tblQuiz = tblQuiz;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tblQuizDetailPK != null ? tblQuizDetailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblQuizDetail)) {
            return false;
        }
        TblQuizDetail other = (TblQuizDetail) object;
        if ((this.tblQuizDetailPK == null && other.tblQuizDetailPK != null) || (this.tblQuizDetailPK != null && !this.tblQuizDetailPK.equals(other.tblQuizDetailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "diepnn.tbl_quizdetail.TblQuizDetail[ tblQuizDetailPK=" + tblQuizDetailPK + " ]";
    }
    
}
