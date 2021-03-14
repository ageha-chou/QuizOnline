/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.servlet;

import diepnn.tbl_answer.TblAnswer;
import diepnn.tbl_question.QuestionAndAnswer;
import diepnn.tbl_question.TblQuestion;
import diepnn.tbl_subject.TblSubject;
import java.io.IOException;
import java.util.Collection;
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
@WebServlet(name = "LoadSpecificQuestionServlet", urlPatterns = {"/LoadSpecificQuestionServlet"})
public class LoadSpecificQuestionServlet extends HttpServlet {
    private final String SUCCESS_PAGE = "updatePage";
    private final String FAIL_PAGE = "loadAdminPage";
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
        String url = SUCCESS_PAGE;
        try {
            String sUrl = "http://localhost:8080/SE140622_QuizOnlineWS/webresources/admin";
            //load subjects
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(sUrl).path("getSubjects");
            GenericType<Collection<TblSubject>> gt = 
                        new GenericType<Collection<TblSubject>>(){};
            Collection<TblSubject> list = target.request(MediaType.APPLICATION_XML).get(gt);
            request.setAttribute("SUBJECT_LIST", list);
            
            //----------------------------------------------------------------------------
            String quest = request.getParameter("questId");
            if(quest != null){
                //load question
                target = client.target(sUrl).path("getSpecificQuestion");
                TblQuestion ques = target.queryParam("quesId", quest)
                        .request(MediaType.APPLICATION_XML_TYPE).get(TblQuestion.class);
                //load answers
                target = client.target(sUrl).path("getAnswers");
                GenericType<Collection<TblAnswer>> gtAns = 
                        new GenericType<Collection<TblAnswer>>(){};
                Collection<TblAnswer> ans = target.queryParam("quesId", quest)
                        .request(MediaType.APPLICATION_XML_TYPE).get(gtAns);
                QuestionAndAnswer qna = new QuestionAndAnswer();
                qna.setQuestion(ques);
                qna.setAnswers(ans);
                
                request.setAttribute("QNA", qna);
            }else url = FAIL_PAGE;
            
        }catch(NotFoundException ex){
            log("LoadSpecificQuestionSerlet_NotFound: " + ex.getMessage());
        }finally{
            RequestDispatcher rd = request.getRequestDispatcher(url);
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
