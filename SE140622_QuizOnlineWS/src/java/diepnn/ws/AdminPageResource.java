/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.ws;

import diepnn.tbl_answer.TblAnswer;
import diepnn.tbl_answer.TblAnswerBLO;
import diepnn.tbl_question.TblQuestion;
import diepnn.tbl_question.TblQuestionBLO;
import diepnn.tbl_subject.TblSubject;
import diepnn.tbl_subject.TblSubjectBLO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Delwyn
 */
@Path("admin")
public class AdminPageResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TblSubjectResource
     */
    public AdminPageResource() {
    }

    /**
     * Retrieves representation of an instance of diepnn.ws.AdminPageResource
     * @return an instance of java.lang.String
     */
    
    @Path("/updateQuestionAnswer")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateQuestionAnswer(@FormParam("subject") String subject, 
            @FormParam("question") String question, @FormParam("trueAns") String trueAnswer, 
            @FormParam("answer01") String answer01, @FormParam("answer02") String answer02, 
            @FormParam("answer03") String answer03, @FormParam("answer04") String answer04, 
            @FormParam("updatedUser") String createdUser, @FormParam("status") String status,
            @FormParam("quesId") String questId){
        if(questId != null){
            if(!questId.isEmpty()){
                int quesId = Integer.parseInt(questId);
                int trueAns = Integer.parseInt(trueAnswer);
                int userId = Integer.parseInt(createdUser);
                int subjectId = Integer.parseInt(subject);
                boolean sta = Boolean.parseBoolean(status);
                TblQuestionBLO quesBlo = new TblQuestionBLO();
                //update question
                quesBlo.updateQuestion(quesId, question, userId, subjectId, sta);
                //update answer
                List<TblAnswer> ans = new ArrayList<>();
                ans.add(new TblAnswer(answer01, 1 == trueAns));
                ans.add(new TblAnswer(answer02, 2 == trueAns));
                ans.add(new TblAnswer(answer03, 3 == trueAns));
                ans.add(new TblAnswer(answer04, 4 == trueAns));
                
                TblAnswerBLO ansBlo = new TblAnswerBLO();
                ansBlo.updateAnswers(quesId, ans);
                return "true";
            }
        }
        return "false";
    }
    
    @Path("/getSpecificQuestion")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public TblQuestion getSpecificQues(@QueryParam("quesId") String quesId){
        if(quesId != null){
            if(!quesId.isEmpty()){
                int id = Integer.parseInt(quesId);
                TblQuestionBLO quesBlo = new TblQuestionBLO();
                return quesBlo.getSpecificQuestion(id);
            }
        }
        return null;
    }
    
    @Path("/deleteQuestion")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteQuestion(@QueryParam("quesId") String quesId, 
            @QueryParam("userId") String userId){
        if(quesId != null && userId != null){
            if(!quesId.isEmpty() && !userId.isEmpty()){
                int qId = Integer.parseInt(quesId);
                int uId = Integer.parseInt(userId);
                TblQuestionBLO blo = new TblQuestionBLO();
                if(blo.deleteQuestion(qId, uId)) return "true";
            }
        }
        return "false";
    }
    
    @Path("/getAnswers")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Collection<TblAnswer> getAns(@QueryParam("quesId") String quesId){
        TblAnswerBLO ansBlo = new TblAnswerBLO();
        List<TblAnswer> ans = ansBlo.getAnswers(Integer.parseInt(quesId));
        return ans;
    }
    
    @Path("/getQuestions")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Collection<TblQuestion> getQues(@QueryParam("status") String status,
            @QueryParam("subjectId") String subjectId, @QueryParam("question") String question){
        
        TblQuestionBLO quesBlo = new TblQuestionBLO();
        List<TblQuestion> ques = quesBlo.getQues(status, subjectId, question);
        return ques;
    }
    
    @Path("/createQuestionAnswer")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String createQuestionAnswer(@FormParam("subject") String subject, 
            @FormParam("question") String question, @FormParam("trueAns") String trueAnswer, 
            @FormParam("answer01") String answer01, @FormParam("answer02") String answer02, 
            @FormParam("answer03") String answer03, @FormParam("answer04") String answer04, 
            @FormParam("createdUser") String createdUser){
        //create question
        TblQuestionBLO quesBlo = new TblQuestionBLO();
        int quesId = quesBlo.createQuestion(question, Integer.parseInt(subject), 
                Integer.parseInt(createdUser));
        
        int ansIndex = Integer.parseInt(trueAnswer);

        List<TblAnswer> answers = new ArrayList<>();
        answers.add(new TblAnswer(answer01, ansIndex == 1, new TblQuestion(quesId)));
        answers.add(new TblAnswer(answer02, ansIndex == 2, new TblQuestion(quesId)));
        answers.add(new TblAnswer(answer03, ansIndex == 3, new TblQuestion(quesId)));
        answers.add(new TblAnswer(answer04, ansIndex == 4, new TblQuestion(quesId)));
        
        TblAnswerBLO ansBlo = new TblAnswerBLO();
        if(ansBlo.createAnswers(quesId, answers)) return "true";
        return "false";
    }
    
    @Path("/getSubjects")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Collection<TblSubject> getSubjects(){
        TblSubjectBLO blo = new TblSubjectBLO();
        List<TblSubject> list = blo.getSubjects();
        return list;
    }
    
}
