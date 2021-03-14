/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.servlet;

import com.google.gson.Gson;
import diepnn.tbl_answer.TblAnswer;
import diepnn.tbl_question.QuestionAndAnswer;
import diepnn.tbl_question.TblQuestion;
import diepnn.tbl_quiz.TblQuiz;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Delwyn
 */
@WebServlet(name = "SubmitQuizServlet", urlPatterns = {"/SubmitQuizServlet"})
public class SubmitQuizServlet extends HttpServlet {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String quizId = "0";
        try {
            HttpSession session = request.getSession();
            Map<Integer, Integer> ansList = (Map<Integer, Integer>) session.getAttribute("ANS_LIST");
            TblQuiz quiz = (TblQuiz) session.getAttribute("QUIZ");
            List<QuestionAndAnswer> qna = (List<QuestionAndAnswer>) session.getAttribute("QUEST_LIST");
            quiz.setSubmittedDate(new Date());
            short countTrue = 0;
            List<Boolean> results = new ArrayList<>();
            List<Integer> quesIdList = new ArrayList<>();
            for (QuestionAndAnswer quesAns : qna) {
                quesIdList.add(quesAns.getQuestion().getId());
                boolean isTrue = false;
                TblQuestion ques = quesAns.getQuestion();
                Integer userAns = ansList.get(ques.getId());
                Collection<TblAnswer> ans = quesAns.getAnswers();
                if(userAns != null){
                    for (TblAnswer an : ans) {
                        if(an.getIsRight() && Objects.equals(userAns, an.getId())){
                            countTrue++;
                            isTrue = true;
                            break;
                        }
                    }   
                }
                results.add(isTrue);
            }
            quiz.setCorrectQues(countTrue);
            
            //write quiz to db
            String sUrl = "http://localhost:8080/SE140622_QuizOnlineWS/webresources/student";
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(sUrl).path("createQuiz");
            String quizJson = new Gson().toJson(quiz);
            Form form = new Form();
            form.param("quiz", quizJson);
            quizId = target.request(MediaType.TEXT_PLAIN_TYPE)
                    .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
            
            //wriete quiz detail to db
            target = client.target(sUrl).path("createQuizDetail");
            String questListJson = new Gson().toJson(quesIdList, List.class);
            String ansListJson = new Gson().toJson(ansList);
            String resultsJson = new Gson().toJson(results);
            form = new Form();
            form.param("quizId", quizId);
            form.param("questListJson", questListJson);
            form.param("ansListJson", ansListJson);
            form.param("resultsJson", resultsJson);
            String res = target.request(MediaType.TEXT_PLAIN_TYPE)
                    .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
            if(Boolean.parseBoolean(res)){
                session.removeAttribute("QUIZ");
                session.removeAttribute("QUEST_LIST");
                session.removeAttribute("TIME_LIMIT");
                session.removeAttribute("TIME_PASS");
                session.removeAttribute("ANS_LIST");
            }
        }catch(NotFoundException ex){
            log("SubmitQuizServlet_NotFound: " + ex.getMessage());
        }finally{
            String urlRewriting = "loadResultPage?qId="+quizId;
            response.sendRedirect(urlRewriting);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
