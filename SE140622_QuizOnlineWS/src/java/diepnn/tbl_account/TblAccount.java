/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.tbl_account;

import diepnn.tbl_question.TblQuestion;
import diepnn.tbl_quiz.TblQuiz;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "tbl_Account", catalog = "QuizOnline", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblAccount.findAll", query = "SELECT t FROM TblAccount t")
    , @NamedQuery(name = "TblAccount.findById", query = "SELECT t FROM TblAccount t WHERE t.id = :id")
    , @NamedQuery(name = "TblAccount.findByEmail", query = "SELECT t FROM TblAccount t WHERE t.email = :email")
    , @NamedQuery(name = "TblAccount.findByName", query = "SELECT t FROM TblAccount t WHERE t.name = :name")
    , @NamedQuery(name = "TblAccount.findByPassword", query = "SELECT t FROM TblAccount t WHERE t.password = :password")
    , @NamedQuery(name = "TblAccount.findByIsAdmin", query = "SELECT t FROM TblAccount t WHERE t.isAdmin = :isAdmin")
    , @NamedQuery(name = "TblAccount.findByIsBanned", query = "SELECT t FROM TblAccount t WHERE t.isBanned = :isBanned")})
public class TblAccount implements Serializable {

    @OneToMany(mappedBy = "userId")
    private Collection<TblQuiz> tblQuizCollection;

    @OneToMany(mappedBy = "createdUser")
    private Collection<TblQuestion> tblQuestionCollection;
    @OneToMany(mappedBy = "lastModifiedUser")
    private Collection<TblQuestion> tblQuestionCollection1;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Integer id;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    @Column(name = "isAdmin")
    private Boolean isAdmin = false;
    @Column(name = "isBanned")
    private Boolean isBanned = false;

    public TblAccount() {
    }

    public TblAccount(Integer id) {
        this.id = id;
    }

    public TblAccount(Integer id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(Boolean isBanned) {
        this.isBanned = isBanned;
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
        if (!(object instanceof TblAccount)) {
            return false;
        }
        TblAccount other = (TblAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "diepnn.ws.TblAccount[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<TblQuestion> getTblQuestionCollection() {
        return tblQuestionCollection;
    }

    public void setTblQuestionCollection(Collection<TblQuestion> tblQuestionCollection) {
        this.tblQuestionCollection = tblQuestionCollection;
    }

    @XmlTransient
    public Collection<TblQuestion> getTblQuestionCollection1() {
        return tblQuestionCollection1;
    }

    public void setTblQuestionCollection1(Collection<TblQuestion> tblQuestionCollection1) {
        this.tblQuestionCollection1 = tblQuestionCollection1;
    }

    @XmlTransient
    public Collection<TblQuiz> getTblQuizCollection() {
        return tblQuizCollection;
    }

    public void setTblQuizCollection(Collection<TblQuiz> tblQuizCollection) {
        this.tblQuizCollection = tblQuizCollection;
    }
    
}
