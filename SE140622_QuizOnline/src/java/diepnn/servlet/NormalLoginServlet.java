/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diepnn.servlet;

import diepnn.tbl_account.TblAccount;
import diepnn.utils.SHAGeneration;
import java.io.IOException;
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
@WebServlet(name = "NormalLoginServlet", urlPatterns = {"/NormalLoginServlet"})
public class NormalLoginServlet extends HttpServlet {
    private final String ERROR_PAGE = "loginPage";
    private final String STUDENT_PAGE = "loadUserPage";
    private final String ADMIN_PAGE = "loadAdminPage";
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
        String url = ERROR_PAGE;
        try {
            String email = request.getParameter("email");
            String password = SHAGeneration.generateSHA(request.getParameter("password"));
            String sUrl = "http://localhost:8080/SE140622_QuizOnlineWS/webresources/tblAccount";
            
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(sUrl);
            TblAccount user = target.path("checkLogin")
                    .queryParam("email", email)
                    .queryParam("password", password)
                    .request(MediaType.APPLICATION_XML_TYPE)
                    .get(TblAccount.class);
            if(user != null){
                HttpSession session = request.getSession();
                session.setAttribute("USER", user);
                if(user.getIsAdmin()) url = ADMIN_PAGE;
                else url = STUDENT_PAGE;
            }else{
                request.setAttribute("ERROR", "This account does not exist");
            }
        }catch(NotFoundException ex){
            log("NormalLoginServlet_NotFound: " + ex.getMessage());
        }finally{
            if(url.equals(ERROR_PAGE)){
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }else{
                response.sendRedirect(url);
            }
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
