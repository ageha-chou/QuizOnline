/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.ws;

import com.google.gson.Gson;
import diepnn.quizdetail.TblQuizDetailBLO;
import diepnn.tbl_question.TblQuestion;
import diepnn.tbl_question.TblQuestionBLO;
import diepnn.tbl_quiz.TblQuiz;
import diepnn.tbl_quiz.TblQuizBLO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
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
@Path("student")
public class StudentResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of StudentResource
     */
    public StudentResource() {
    }

    /**
     * Retrieves representation of an instance of diepnn.ws.StudentResource
     * @return an instance of java.lang.String
     */
    @Path("/getRandomQues")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<TblQuestion> getRandomQues(@QueryParam("subjectId") String subjectId,
            @QueryParam("limit") String limit){
        if(subjectId != null && limit != null){
            TblQuestionBLO quesBlo = new TblQuestionBLO();
            List<TblQuestion> ques = quesBlo.getRandomQues(subjectId, limit);
            return ques;
        }
        return null;
    }
    
    @Path("/createQuiz")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String createQuiz(@FormParam("quiz") String quizJson){
        TblQuiz quiz = new Gson().fromJson(quizJson, TblQuiz.class);
        TblQuizBLO blo = new TblQuizBLO();
        int quesId = blo.createQuiz(quiz);
        return String.valueOf(quesId);
    }
    
    @Path("/createQuizDetail")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String createQuizDetail(@FormParam("quizId") String quizId,
            @FormParam("questListJson") String questListJson,
            @FormParam("ansListJson") String ansListJson,
            @FormParam("resultsJson") String resultsJson){
        int id = Integer.parseInt(quizId);
        List<Double> quesIdList = new Gson().fromJson(questListJson, List.class);
        Map<String, Double> ansList = new Gson().fromJson(ansListJson, HashMap.class);
        List<Boolean> results = new Gson().fromJson(resultsJson, List.class);
        TblQuizDetailBLO detailBlo = new TblQuizDetailBLO();
        detailBlo.createQuizDetail(id, quesIdList, ansList, results);
        return "true";
    }
    
    @Path("/loadQuiz")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public TblQuiz loadQuiz(@QueryParam("qId") String qId){
        if(qId != null){
            TblQuizBLO blo = new TblQuizBLO();
            return blo.getQuizById(Integer.parseInt(qId));
        }
        return null;
    }
    
    @Path("/getQuiz")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<TblQuiz> getQuiz(@QueryParam("userId") String userId,
            @QueryParam("subId") String subId){
        TblQuizBLO blo = new TblQuizBLO();
        List<TblQuiz> list = blo.getQuizzes(subId, userId);
        return list;
    }
}
