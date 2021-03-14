/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.servlet;

import diepnn.tbl_account.TblAccount;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Delwyn
 */
@WebServlet(name = "DeleteQuestionServlet", urlPatterns = {"/DeleteQuestionServlet"})
public class DeleteQuestionServlet extends HttpServlet {

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
        String quesId = request.getParameter("questId");
        String subject = request.getParameter("subject");
        String status = request.getParameter("status");
        String question = request.getParameter("question");
        String urlRewriting = "loadAdminPage"
                            + "?subject=" + subject
                            + "&status=" + status
                            + "&question=" + question;
        try {
            HttpSession session = request.getSession();
            TblAccount user = (TblAccount) session.getAttribute("USER");
            String sUrl = "http://localhost:8080/SE140622_QuizOnlineWS/webresources/admin";
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(sUrl).path("deleteQuestion");
            String res = target.queryParam("quesId", quesId)
                    .queryParam("userId", user.getId())
                    .request(MediaType.TEXT_PLAIN_TYPE).get(String.class);
            boolean result = Boolean.parseBoolean(res);
            
            if(result) urlRewriting += "&message=Successfully!";
            else urlRewriting += "&message=Failed!";
        }catch(NotFoundException ex){
            log("DeleteQuestionServlet_NotFound: " + ex.getMessage());
        }finally{
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
