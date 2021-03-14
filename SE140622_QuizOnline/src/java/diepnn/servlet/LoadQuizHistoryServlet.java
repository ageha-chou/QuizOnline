/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.servlet;

import diepnn.tbl_account.TblAccount;
import diepnn.tbl_quiz.TblQuiz;
import diepnn.tbl_subject.TblSubject;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "LoadQuizHistoryServlet", urlPatterns = {"/LoadQuizHistoryServlet"})
public class LoadQuizHistoryServlet extends HttpServlet {
    private final String DIRECT_PAGE = "quizHistoryPage";
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
            HttpSession session = request.getSession();
            TblAccount user = (TblAccount) session.getAttribute("USER");
            if(user != null){
                //load all subjects
                String sUrl = "http://localhost:8080/SE140622_QuizOnlineWS/webresources/admin";
                Client client = ClientBuilder.newClient();
                WebTarget target = client.target(sUrl).path("getSubjects");
                GenericType<Collection<TblSubject>> gt = 
                            new GenericType<Collection<TblSubject>>(){};
                Collection<TblSubject> list = target.request(MediaType.APPLICATION_XML).get(gt);
                request.setAttribute("SUBJECT_LIST", list);
                
                //load quiz
                String subId = request.getParameter("subId");
                sUrl = "http://localhost:8080/SE140622_QuizOnlineWS/webresources/student";
                target = client.target(sUrl).path("getQuiz");
                GenericType<Collection<TblQuiz>> quizGt = 
                            new GenericType<Collection<TblQuiz>>(){};
                Collection<TblQuiz> quiz = target.queryParam("subId", subId)
                        .queryParam("userId", user.getId())
                        .request(MediaType.APPLICATION_XML_TYPE).get(quizGt);
                request.setAttribute("QUIZ_LSIT", quiz);
            }
        }catch(NotFoundException ex){
            log("LoadQuizHistoryServlet_NotFound: " + ex.getMessage());
        }finally{
            RequestDispatcher rd = request.getRequestDispatcher(DIRECT_PAGE);
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
