/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.servlet;

import diepnn.tbl_account.TblAccount;
import java.io.IOException;
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
@WebServlet(name = "CreateQuestionServlet", urlPatterns = {"/CreateQuestionServlet"})
public class CreateQuestionServlet extends HttpServlet {
//    private final String SUCCESS_PAGE = "loadCreateQuestionPage";
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
        
        HttpSession session = request.getSession();
        String subject = request.getParameter("subject");
        String question = request.getParameter("question").trim();
        String trueAns = request.getParameter("rdoAnswer");
        String answer01 = request.getParameter("answer01").trim();
        String answer02 = request.getParameter("answer02").trim();
        String answer03 = request.getParameter("answer03").trim();
        String answer04 = request.getParameter("answer04").trim();
        try {
            TblAccount user = (TblAccount) session.getAttribute("USER");
            int createdUser = user.getId();
            
            String sUrl = "http://localhost:8080/SE140622_QuizOnlineWS/webresources/admin";
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(sUrl).path("createQuestionAnswer");
            Form form = new Form();
            form.param("subject", subject);
            form.param("question", question);
            form.param("trueAns", trueAns);
            form.param("answer01", answer01);
            form.param("answer02", answer02);
            form.param("answer03", answer03);
            form.param("answer04", answer04);
            form.param("createdUser", String.valueOf(createdUser));
            
            String res = target.request(MediaType.TEXT_PLAIN_TYPE)
                    .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
            boolean result = Boolean.parseBoolean(res);
            if(result)
                session.setAttribute("MESSAGE", "Created successfully!!!");
        }catch(NotFoundException ex){
            log("CreateQuestionServlet_NotFound: " + ex.getMessage());
        }finally{
            String urlRewriting = "loadCreateQuestionPage?subject=" + subject;
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
