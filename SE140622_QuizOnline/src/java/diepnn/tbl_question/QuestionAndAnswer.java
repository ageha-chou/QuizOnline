/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.tbl_question;

import diepnn.tbl_answer.TblAnswer;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Delwyn
 */
public class QuestionAndAnswer implements Serializable{
    private TblQuestion question;
    private Collection<TblAnswer> answers;

    public QuestionAndAnswer() {
    }
    
    public TblQuestion getQuestion() {
        return question;
    }

    public void setQuestion(TblQuestion question) {
        this.question = question;
    }

    public Collection<TblAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<TblAnswer> answers) {
        this.answers = answers;
    }
}
