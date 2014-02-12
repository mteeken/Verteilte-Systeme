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
import javax.servlet.http.HttpSession;
import module.Termine;

/**
 *
 * @author mteeken
 */
@WebServlet(name = "TerminList", urlPatterns = {"/terminlist.jsp"})
public class TerminList extends HttpServlet {
    
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
        HttpSession session = request.getSession();
        if (session == null) {
            response.sendRedirect("index.jsp");
        }

        response.setContentType("text/html;charset=UTF-8");
        // Ausgabe setzen
        ServletOutputStream writer; 
        writer = response.getOutputStream(); 
        
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
            writer.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"resources/custom.css\">");
            writer.println("<meta http-equiv=\"refresh\" content=\"5; URL=terminlist.jsp\">");
            writer.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            writer.println("<title>Terminkalender</title>");
        writer.println("</head>");
        writer.println("<body>");

        writer.println("<div class=\"wrapper\"><div class=\"spacer\">");
        writer.println("<a href=\"logout.jsp\">Ausloggen</a><br/>");
        writer.println("<h1>Terminkalender</h1><BR />");
        
        if (this.errorMessage != null)
            writer.println("<b>" + this.errorMessage + "</b>");
        
        writer.println("<h2>Ihre nächsten Termine: </h2>");

        TermineController tc = new TermineController();
        try {
            String name = session.getAttribute("name").toString();
            
            List<Termine> termine = tc.getNextXTermine(name, 10);

            for (Termine t : termine) {
                writer.println("<div>");
                writer.println(t.getTitle());
                String date_begin = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(t.getDate_begin());
                String date_end = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(t.getDate_end());
                writer.println("<b>Anfang:</b> " + date_begin);
                writer.println("<b>Ende:</b> " + date_end);
                writer.println("<b>Ort:</b> " + t.getOrt());
                writer.println("<b>Terinart:</b> " + t.getArt().toString());
                writer.println("<a href=\"delete.jsp?id=" + t.getId() + "\">Termin löschen</a>");
                writer.println("<a href=\"modify.jsp?id=" + t.getId() + "\">Termin bearbeiten</a>");
                writer.println("</div>");
            }
        } catch (Exception e) {
            writer.println("<h2>" + e.getMessage() + "</h2>");
        }
        writer.println("<br/><br/>");
        writer.println("<a href=\"addtermin.jsp\">Termin anlegen</a><br/>");
        writer.println("<a href=\"month.jsp\">Termin des Monats</a>");
        writer.println("</div></div>");
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
