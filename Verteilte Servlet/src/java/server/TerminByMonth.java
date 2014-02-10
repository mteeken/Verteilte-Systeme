/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import controller.TermineController;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
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
@WebServlet(name = "TerminByMonth", urlPatterns = {"/month.jsp"})
public class TerminByMonth extends HttpServlet {
    
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
            writer.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            writer.println("<title>Terminkalender</title>");
        writer.println("</head>");
        writer.println("<body>");
        Integer month;

        writer.println("<div class=\"wrapper\"><div class=\"spacer\">");
        writer.println("<a href=\"logout.jsp\">Ausloggen</a><br/>");
        writer.println("<h1>Terminkalender</h1><BR />");
        
        if (this.errorMessage != null)
            writer.println("<b>" + this.errorMessage + "</b>");

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = df.getCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (request.getParameterValues("month") != null) {
            month = Integer.parseInt(request.getParameterValues("month")[0]);  
            calendar.set(Calendar.MONTH, month - 1);
        } else {
            calendar.setTimeInMillis(System.currentTimeMillis());
            month = calendar.MONTH;
        }

        TermineController tc = new TermineController();
        writer.println("<h2>Ihre Termine im " + tc.getMonat(month) + ": </h2>");

        try {
            int i = 1;
            int daysOfMonth = calendar.getActualMaximum(calendar.DATE);
            String name = session.getAttribute("name").toString();
            
            List<Termine> termine = tc.getByMonth(name, month);
            Iterator<Termine> tIterator = termine.iterator();
            Termine t = tIterator.next();
            while(i <= daysOfMonth) {
                writer.println("<div>");
                writer.println("<h3>" + i + "</h3>");
                
                if (t != null)
                    calendar.setTime(t.getDate_begin());
                
                while (calendar.get(calendar.DAY_OF_MONTH) == i && t != null) {
                    writer.println("<div>");
                    writer.println(t.getTitle());
                    String date_begin = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(t.getDate_begin());
                    String date_end = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(t.getDate_end());
                    writer.println("Anfang: " + date_begin);
                    writer.println("Ende: " + date_end);
                    writer.println("<a href=\"delete.jsp?id=" + t.getId() + "\">Termin löschen</a>");
                    writer.println("<a href=\"modify.jsp?id=" + t.getId() + "\">Termin bearbeiten</a>");
                    writer.println("</div>");
                    
                    if (tIterator.hasNext()) {
                        t = tIterator.next();
                        calendar.setTime(t.getDate_begin());
                    } else {
                        t = null;
                        break;
                    }
                }

                i++;

                writer.println("</div>");
            }
        } catch (Exception e) {
            writer.println("<h2>" + e.getMessage() + "</h2>");
        }
        writer.println("<br/><br/>");
        writer.println("<a href=\"addtermin.jsp\">Termin anlegen</a><br/>");
        writer.println("<a href=\"terminlist.jsp\">Nächste 10 Termine</a>");
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
