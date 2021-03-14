/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.servlet;

import diepnn.tbl_account.TblAccount;
import diepnn.tbl_answer.TblAnswer;
import diepnn.tbl_question.QuestionAndAnswer;
import diepnn.tbl_question.TblQuestion;
import diepnn.tbl_quiz.TblQuiz;
import diepnn.tbl_subject.TblSubject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Delwyn
 */
@WebServlet(name = "LoadQuizServlet", urlPatterns = {"/LoadQuizServlet"})
public class LoadQuizServlet extends HttpServlet {
    private String DIRECT_PAGE = "quizPage";
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
        try {
            HttpSession session = request.getSession();
            TblAccount user = (TblAccount) session.getAttribute("USER");
            if(user != null){
                if(session.getAttribute("QUIZ") == null){
                    String subjectId = request.getParameter("subjectId");
                    String limit = request.getParameter("numOfquest");
                    //create quiz
                    String sUrl = "http://localhost:8080/SE140622_QuizOnlineWS/webresources/student";
                    Client client = ClientBuilder.newClient();
                    WebTarget target = client.target(sUrl).path("getRandomQues");
                    GenericType<Collection<TblQuestion>> gt = 
                            new GenericType<Collection<TblQuestion>>(){}; 
                    
                    //get questions
                    Collection<TblQuestion> ques = target.queryParam("subjectId", subjectId)
                            .queryParam("limit", limit).request(MediaType.APPLICATION_XML_TYPE).get(gt);
                    
                    //get answers
                    List<QuestionAndAnswer> qna = new ArrayList();
                    sUrl = "http://localhost:8080/SE140622_QuizOnlineWS/webresources/admin";
                    for (TblQuestion tblQuestion : ques) {
                        target = client.target(sUrl).path("getAnswers");
                        GenericType<Collection<TblAnswer>> gtAns = 
                                new GenericType<Collection<TblAnswer>>(){};
                        Collection<TblAnswer> ans = target.queryParam("quesId", tblQuestion.getId())
                                .request(MediaType.APPLICATION_XML_TYPE).get(gtAns);
                        QuestionAndAnswer obj = new QuestionAndAnswer();
                        obj.setQuestion(tblQuestion);
                        obj.setAnswers(ans);
                        qna.add(obj);
                    }
                    TblQuiz quiz = new TblQuiz();
                    quiz.setSubjectId(new TblSubject(Integer.parseInt(subjectId)));
                    quiz.setTotalQues(Short.parseShort(limit));
                    quiz.setUserId(user);
                    session.setAttribute("QUIZ", quiz);
                    session.setAttribute("QUEST_LIST", qna);
                    session.setAttribute("TIME_LIMIT", request.getParameter("timeLimit"));
                    session.setAttribute("TIME_PASS", 0);
                }
            }
        }catch(NotFoundException ex){
            log("LoadQuizServlet_NotFound: " + ex.getMessage());
        }finally{
            response.sendRedirect(DIRECT_PAGE);
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
