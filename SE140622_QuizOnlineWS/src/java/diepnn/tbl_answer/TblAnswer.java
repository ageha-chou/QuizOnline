/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.tbl_answer;

import diepnn.quizdetail.TblQuizDetail;
import diepnn.tbl_question.TblQuestion;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Delwyn
 */
@Entity
@Table(name = "tbl_Answer", catalog = "QuizOnline", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblAnswer.findAll", query = "SELECT t FROM TblAnswer t")
    , @NamedQuery(name = "TblAnswer.findById", query = "SELECT t FROM TblAnswer t WHERE t.id = :id")
    , @NamedQuery(name = "TblAnswer.findByContent", query = "SELECT t FROM TblAnswer t WHERE t.content = :content")
    , @NamedQuery(name = "TblAnswer.findByIsRight", query = "SELECT t FROM TblAnswer t WHERE t.isRight = :isRight")})
public class TblAnswer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "content", nullable = false, length = 300)
    private String content;
    @Column(name = "isRight")
    private Boolean isRight;
    @JoinColumn(name = "questionId", referencedColumnName = "id")
    @ManyToOne
    private TblQuestion questionId;

    public TblAnswer() {
    }

    public TblAnswer(Integer id) {
        this.id = id;
    }

    public TblAnswer(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    public TblAnswer(String content, Boolean isRight, TblQuestion questionId) {
        this.content = content;
        this.isRight = isRight;
        this.questionId = questionId;
    }

    public TblAnswer(String content, Boolean isRight) {
        this.content = content;
        this.isRight = isRight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsRight() {
        return isRight;
    }

    public void setIsRight(Boolean isRight) {
        this.isRight = isRight;
    }

    public TblQuestion getQuestionId() {
        return questionId;
    }

    public void setQuestionId(TblQuestion questionId) {
        this.questionId = questionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblAnswer)) {
            return false;
        }
        TblAnswer other = (TblAnswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "diepnn.tbl_answer.TblAnswer[ id=" + id + " ]";
    }
}
