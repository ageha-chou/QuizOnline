/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.servlet;

import diepnn.tbl_answer.TblAnswer;
import diepnn.tbl_question.QuestionAndAnswer;
import diepnn.tbl_question.TblQuestion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@WebServlet(name = "AdminPageSearchServlet", urlPatterns = {"/AdminPageSearchServlet"})
public class AdminPageSearchServlet extends HttpServlet {
    private final String SUCCESS_PAGE = "adminPage";
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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            String status = request.getParameter("status");
            String subjectId = request.getParameter("subject");
            String question = request.getParameter("question");
            
            String sUrl = "http://localhost:8080/SE140622_QuizOnlineWS/webresources/admin";
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(sUrl).path("getQuestions");
            GenericType<Collection<TblQuestion>> gt = 
                        new GenericType<Collection<TblQuestion>>(){};
            Collection<TblQuestion> result = target.queryParam("status", status)
                    .queryParam("subjectId", subjectId).queryParam("question", question)
                    .request(MediaType.APPLICATION_XML_TYPE).get(gt);
            List<QuestionAndAnswer> qna = new ArrayList();
            for (TblQuestion tblQuestion : result) {
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
            request.setAttribute("TOTAL_QUES", result.size());
            request.setAttribute("QUESTION_LIST", qna);
        }catch(NotFoundException ex){
            log("AdminPageSearchServlet_NotFound: " + ex.getMessage());
        }finally{
            RequestDispatcher rd = request.getRequestDispatcher(SUCCESS_PAGE);
            rd.forward(request, response);
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
