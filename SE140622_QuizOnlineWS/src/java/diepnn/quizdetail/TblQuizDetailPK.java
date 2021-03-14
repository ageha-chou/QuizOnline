/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.quizdetail;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Delwyn
 */
@Embeddable
public class TblQuizDetailPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "quizId", nullable = false)
    private int quizId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "questionId", nullable = false)
    private int questionId;

    public TblQuizDetailPK() {
    }

    public TblQuizDetailPK(int quizId, int questionId) {
        this.quizId = quizId;
        this.questionId = questionId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) quizId;
        hash += (int) questionId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblQuizDetailPK)) {
            return false;
        }
        TblQuizDetailPK other = (TblQuizDetailPK) object;
        if (this.quizId != other.quizId) {
            return false;
        }
        if (this.questionId != other.questionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "diepnn.quizdetail.TblQuizDetailPK[ quizId=" + quizId + ", questionId=" + questionId + " ]";
    }
    
}
