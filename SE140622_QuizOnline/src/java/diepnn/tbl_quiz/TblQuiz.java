/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.tbl_quiz;

import diepnn.tbl_account.TblAccount;
import diepnn.tbl_quizdetail.TblQuizDetail;
import diepnn.tbl_subject.TblSubject;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Delwyn
 */
@Entity
@Table(name = "tbl_Quiz", catalog = "QuizOnline", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblQuiz.findAll", query = "SELECT t FROM TblQuiz t")
    , @NamedQuery(name = "TblQuiz.findById", query = "SELECT t FROM TblQuiz t WHERE t.id = :id")
    , @NamedQuery(name = "TblQuiz.findByCreatedDate", query = "SELECT t FROM TblQuiz t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TblQuiz.findBySubmittedDate", query = "SELECT t FROM TblQuiz t WHERE t.submittedDate = :submittedDate")
    , @NamedQuery(name = "TblQuiz.findByCorrectQues", query = "SELECT t FROM TblQuiz t WHERE t.correctQues = :correctQues")
    , @NamedQuery(name = "TblQuiz.findByTotalQues", query = "SELECT t FROM TblQuiz t WHERE t.totalQues = :totalQues")})
public class TblQuiz implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblQuiz")
    private Collection<TblQuizDetail> tblQuizDetailCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "createdDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();
    @Column(name = "submittedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedDate;
    @Column(name = "correctQues")
    private Short correctQues;
    @Column(name = "totalQues")
    private Short totalQues;
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @ManyToOne
    private TblAccount userId;
    @JoinColumn(name = "subjectId", referencedColumnName = "id")
    @ManyToOne
    private TblSubject subjectId;

    public TblQuiz() {
        
    }

    public TblQuiz(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public Short getCorrectQues() {
        return correctQues;
    }

    public void setCorrectQues(Short correctQues) {
        this.correctQues = correctQues;
    }

    public Short getTotalQues() {
        return totalQues;
    }

    public void setTotalQues(Short totalQues) {
        this.totalQues = totalQues;
    }

    public TblAccount getUserId() {
        return userId;
    }

    public void setUserId(TblAccount userId) {
        this.userId = userId;
    }

    public TblSubject getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(TblSubject subjectId) {
        this.subjectId = subjectId;
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
        if (!(object instanceof TblQuiz)) {
            return false;
        }
        TblQuiz other = (TblQuiz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "diepnn.tbl_quiz.TblQuiz[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<TblQuizDetail> getTblQuizDetailCollection() {
        return tblQuizDetailCollection;
    }

    public void setTblQuizDetailCollection(Collection<TblQuizDetail> tblQuizDetailCollection) {
        this.tblQuizDetailCollection = tblQuizDetailCollection;
    }
    
}
