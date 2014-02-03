/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.security.DigestException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import verteilte.db.controller.NutzerController;
import verteilte.db.module.Nutzer;
/**
 *
 * @author mteeken
 */
@WebServlet(name = "Login", urlPatterns = {"/Login.jsp"})
public class Login extends HttpServlet {
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        //res.setContentType("text/html;charset=UTF-8");
        HttpSession session = null;
        if (req.getMethod().equals("GET")) {
            String name = req.getParameterValues("name")[0];
            String password = req.getParameterValues("password")[0];
            NutzerController lc = new NutzerController();
            try {
                lc.getUser(name, password);
            } catch (DigestException e) {
                
            }
    
            session = req.getSession(true);
            session.setAttribute("name", name);
            if (session != null) {
                res.sendRedirect("News.jsp");
            } 
        }
     }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
}
