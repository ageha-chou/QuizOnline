/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.servlet;

import diepnn.google.GooglePojo;
import diepnn.tbl_account.TblAccount;
import diepnn.utils.GoogleUtils;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Delwyn
 */
@WebServlet(name = "GoogleLoginServlet", urlPatterns = {"/GoogleLoginServlet"})
public class GoogleLoginServlet extends HttpServlet {
    private final String FAIL_PAGE = "loginPage";
    private final String STUDENT_PAGE = "loadUserPage";
    private final String ADMIN_PAGE = "loadAdminPage";
    private final String REGISTRATION_PAGE = "googleSignupPage";
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
        PrintWriter out = response.getWriter();
        String code = request.getParameter("code");
        String url = FAIL_PAGE;
        try {
            if(code != null && !code.isEmpty()){
                String accessToken = GoogleUtils.getToken(code);
                GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
                String fullname = googlePojo.getName();
                String email = googlePojo.getEmail();
                String sUrl = "http://localhost:8080/SE140622_QuizOnlineWS/webresources/tblAccount";
                Client client = ClientBuilder.newClient();
                WebTarget target = client.target(sUrl).path("checkIfAccountExisted");
                TblAccount acc = target.queryParam("email", email)
                        .request(MediaType.APPLICATION_XML_TYPE).get(TblAccount.class);
                if(acc != null){
                    HttpSession session = request.getSession();
                    session.setAttribute("USER", acc);
                    if(acc.getIsAdmin()){
                        url = ADMIN_PAGE;
                    }else url = STUDENT_PAGE;
                }else{
                    request.setAttribute("email", email);
                    request.setAttribute("fullname", fullname);
                    url = REGISTRATION_PAGE;
                }
            }
        }catch(NotFoundException ex){
            log("GoogleLoginServlet_NotFound: " + ex.getMessage());
        }finally{
            if(url.equals(ADMIN_PAGE) || url.equals(STUDENT_PAGE)){
                response.sendRedirect(url);
            }else{
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
            out.close();
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
