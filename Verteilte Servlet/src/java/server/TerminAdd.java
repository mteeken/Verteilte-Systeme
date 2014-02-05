/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import controller.TermineController;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import module.Terminart;
import module.Termine;

/**
 *
 * @author mteeken
 */
@WebServlet(name = "TerminAdd", urlPatterns = {"/addtermin.jsp"})
public class TerminAdd extends HttpServlet {
    
     private String errorMessage;
     private Boolean sendRedirect = false;
    
         
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        
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
       
       response.setContentType("text/html;charset=UTF-8");
        // Ausgabe setzen
       ServletOutputStream writer; 
       writer = response.getOutputStream(); 
        
       writer.println("<!DOCTYPE html>");
       writer.println("<html>");
       writer.println("<head>");
            writer.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            writer.println("<title>Terminkalender</title>");
       writer.println("</head>");
       writer.println("<body>");
       
       if (this.errorMessage != null)
            writer.println("<b>" + this.errorMessage + "</b>");
       
       writer.println("<h1>Termin hinzufügen: </h1>");

       try {
            writer.println("<form action=\"addtermin.jsp\" method=\"POST\">");
                writer.println("<label for=\"title\">Title:</label>");
                writer.println("<input type=\"text\" id=\"title\" name=\"title\" />");
                writer.println("<label for=\"datum_begin\">Anfang:</label>");
                writer.println("<input type=\"date\" name=\"date_begin\" />");
                writer.println("<label for=\"datum_begin\">Ende:</label>");
                writer.println("<input type=\"date\" name=\"date_end\" />");
                writer.println("<label for=\"datum_begin\">Ort:</label>");
                writer.println("<input type=\"text\" name=\"ort\" />");
                writer.println("<label for=\"datum_begin\">Art:</label>");
                writer.println("<select name=\"art\">");
                    writer.println("<option value=\""+ Terminart.BIRTHDAY +"\">Geburtstag</option>");    
                    writer.println("<option value=\""+ Terminart.FREETIME +"\">Freie Zeit</option>");    
                    writer.println("<option value=\""+ Terminart.HOLIDAY +"\">Ferien</option>");    
                    writer.println("<option value=\""+ Terminart.WORK +"\">Arbeit</option>");    
                writer.println("</select>");
                writer.println("<BR /><BR />");
                writer.println("<input type=\"submit\" value=\"Eingabe\" />");
            writer.println("</form>");

            writer.println("</form>");
        } catch (Exception e) {
            writer.println("<h2>" + e.getMessage() + "</h2>");
        }

        writer.println("</body>");
        writer.println("</html>");
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
        
        if (this.sendRedirect == false)
            this.doGet(request, response);
    }
}
