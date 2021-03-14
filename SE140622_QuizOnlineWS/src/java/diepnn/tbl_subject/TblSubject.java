/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.tbl_subject;

import diepnn.tbl_question.TblQuestion;
import diepnn.tbl_quiz.TblQuiz;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "tbl_Subject", catalog = "QuizOnline", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblSubject.findAll", query = "SELECT t FROM TblSubject t")
    , @NamedQuery(name = "TblSubject.findById", query = "SELECT t FROM TblSubject t WHERE t.id = :id")
    , @NamedQuery(name = "TblSubject.findByName", query = "SELECT t FROM TblSubject t WHERE t.name = :name")
    , @NamedQuery(name = "TblSubject.findByStatus", query = "SELECT t FROM TblSubject t WHERE t.status = :status")
    , @NamedQuery(name = "TblSubject.findByQuizTime", query = "SELECT t FROM TblSubject t WHERE t.quizTime = :quizTime")
    , @NamedQuery(name = "TblSubject.findByNoOfQuestion", query = "SELECT t FROM TblSubject t WHERE t.noOfQuestion = :noOfQuestion")})
public class TblSubject implements Serializable {

    @OneToMany(mappedBy = "subjectId")
    private Collection<TblQuiz> tblQuizCollection;

    @OneToMany(mappedBy = "subjectId")
    private Collection<TblQuestion> tblQuestionCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "quizTime")
    private Short quizTime;
    @Column(name = "noOfQuestion")
    private Short noOfQuestion;

    public TblSubject() {
    }

    public TblSubject(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Short getQuizTime() {
        return quizTime;
    }

    public void setQuizTime(Short quizTime) {
        this.quizTime = quizTime;
    }

    public Short getNoOfQuestion() {
        return noOfQuestion;
    }

    public void setNoOfQuestion(Short noOfQuestion) {
        this.noOfQuestion = noOfQuestion;
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
        if (!(object instanceof TblSubject)) {
            return false;
        }
        TblSubject other = (TblSubject) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "diepnn.tbl_subject.TblSubject[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<TblQuestion> getTblQuestionCollection() {
        return tblQuestionCollection;
    }

    public void setTblQuestionCollection(Collection<TblQuestion> tblQuestionCollection) {
        this.tblQuestionCollection = tblQuestionCollection;
    }

    @XmlTransient
    public Collection<TblQuiz> getTblQuizCollection() {
        return tblQuizCollection;
    }

    public void setTblQuizCollection(Collection<TblQuiz> tblQuizCollection) {
        this.tblQuizCollection = tblQuizCollection;
    }
    
}
