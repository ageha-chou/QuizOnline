/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.tbl_question;

import diepnn.quizdetail.TblQuizDetail;
import diepnn.tbl_account.TblAccount;
import diepnn.tbl_answer.TblAnswer;
import diepnn.tbl_subject.TblSubject;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Delwyn
 */
@Entity
@SqlResultSetMapping(
    name = "QuestionMapping",
    entities = @EntityResult(
        entityClass = TblQuestion.class,
        fields = {
            @FieldResult(name = "id", column = "id"),
            @FieldResult(name = "content", column = "content"),
            @FieldResult(name = "subjectId", column = "subjectId")
        })
)
@Table(name = "tbl_Question", catalog = "QuizOnline", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblQuestion.findAll", query = "SELECT t FROM TblQuestion t")
    , @NamedQuery(name = "TblQuestion.findById", query = "SELECT t FROM TblQuestion t WHERE t.id = :id")
    , @NamedQuery(name = "TblQuestion.findByContent", query = "SELECT t FROM TblQuestion t WHERE t.content = :content")
    , @NamedQuery(name = "TblQuestion.findByCreatedDate", query = "SELECT t FROM TblQuestion t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TblQuestion.findByLastModifiedDate", query = "SELECT t FROM TblQuestion t WHERE t.lastModifiedDate = :lastModifiedDate")
    , @NamedQuery(name = "TblQuestion.findByStatus", query = "SELECT t FROM TblQuestion t WHERE t.status = :status")})
public class TblQuestion implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tblQuestion")
    private Collection<TblQuizDetail> tblQuizDetailCollection;

    @OneToMany(mappedBy = "questionId")
    private Collection<TblAnswer> tblAnswerCollection;

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
    @Column(name = "createdDate")
    @Temporal(TemporalType.DATE)
    private Date createdDate = new Date();
    @Column(name = "lastModifiedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate = new Date();
    @Column(name = "status")
    private Boolean status = true;
    @JoinColumn(name = "createdUser", referencedColumnName = "id")
    @ManyToOne
    private TblAccount createdUser;
    @JoinColumn(name = "lastModifiedUser", referencedColumnName = "id")
    @ManyToOne
    private TblAccount lastModifiedUser;
    @JoinColumn(name = "subjectId", referencedColumnName = "id")
    @ManyToOne
    private TblSubject subjectId;

    public TblQuestion() {
    }

    public TblQuestion(Integer id) {
        this.id = id;
    }

    public TblQuestion(Integer id, String content) {
        this.id = id;
        this.content = content;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public TblAccount getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(TblAccount createdUser) {
        this.createdUser = createdUser;
    }

    public TblAccount getLastModifiedUser() {
        return lastModifiedUser;
    }

    public void setLastModifiedUser(TblAccount lastModifiedUser) {
        this.lastModifiedUser = lastModifiedUser;
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
        if (!(object instanceof TblQuestion)) {
            return false;
        }
        TblQuestion other = (TblQuestion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "diepnn.tbl_question.TblQuestion[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<TblAnswer> getTblAnswerCollection() {
        return tblAnswerCollection;
    }

    public void setTblAnswerCollection(Collection<TblAnswer> tblAnswerCollection) {
        this.tblAnswerCollection = tblAnswerCollection;
    }

    @XmlTransient
    public Collection<TblQuizDetail> getTblQuizDetailCollection() {
        return tblQuizDetailCollection;
    }

    public void setTblQuizDetailCollection(Collection<TblQuizDetail> tblQuizDetailCollection) {
        this.tblQuizDetailCollection = tblQuizDetailCollection;
    }
    
}
