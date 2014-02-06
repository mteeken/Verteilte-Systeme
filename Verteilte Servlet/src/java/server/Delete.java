/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import controller.TermineController;
import exceptions.PasswordEmptyException;
import java.io.IOException;
import java.security.DigestException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mteeken
 */
@WebServlet(name = "Delete", urlPatterns = {"/delete.jsp"})
public class Delete extends HttpServlet {
    
     private String errorMessage;
     private Boolean sendRedirect = false;
    
         
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        if (request.getMethod().equals("GET")) {
            Integer id = Integer.parseInt(request.getParameterValues("id")[0]);
           
            TermineController tc = new TermineController();
            
            try {
                tc.deleteTermin(id);
            } catch (PasswordEmptyException e) {
                this.errorMessage = e.getMessage();
            } catch (DigestException e) {
                this.errorMessage = e.getMessage();
            } catch (Exception e) {
                this.errorMessage = e.getMessage();
            }

            this.sendRedirect = true;
            response.sendRedirect("terminlist.jsp");
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
        HttpSession session = null;
        session = request.getSession();
        if (session == null) {
            response.sendRedirect("index.jsp");
        } else {
            processRequest(request, response);
        
            if (this.sendRedirect == false)
                this.doGet(request, response);
        }
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
        HttpSession session = null;
        session = request.getSession();
        if (session == null) {
            response.sendRedirect("index.jsp");
        } else {
            processRequest(request, response);
        
            if (this.sendRedirect == false)
                this.doGet(request, response);
        }

    }
}
