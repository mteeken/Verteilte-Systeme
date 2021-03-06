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
import controller.NutzerController;
import exceptions.PasswordEmptyException;
import javax.servlet.ServletOutputStream;
import module.Nutzer;
/**
 *
 * @author mteeken
 */
@WebServlet(name = "Login", urlPatterns = {"/login.jsp"})
public class Login extends HttpServlet {
    
    /**
     * Speichert die Nachricht aus einer Exception
     */
    private String errorMessage;
    /**
     * Bestimmt ob nach erfolgreicher anfrage weitergeleitet wird
     */
    private Boolean sendRedirect = false;
    
    /**
     * Verarbeitet die POST Anfrage und überprüft ob es den User gibt und loggt ihn anschließend ein
     * @param HttpServletRequest req
     * @param HttpServletResponse res
     * @throws ServletException
     * @throws IOException 
     */
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        HttpSession session = null;
        if (req.getMethod().equals("POST")) {
            String name = req.getParameterValues("name")[0];
            String password = req.getParameterValues("password")[0];
            NutzerController lc = new NutzerController();
            Nutzer n;
            try {
                n = lc.getUser(name, password);
                session = req.getSession(true);
                session.setAttribute("name", n.getName());
            } catch (PasswordEmptyException e) {
                this.errorMessage = e.getMessage();
            } catch (DigestException e) {
                this.errorMessage = e.getMessage();
            } catch (Exception e) {
                this.errorMessage = e.getMessage();
            }

            if (session != null) {
                this.sendRedirect = true;
                res.sendRedirect("terminlist.jsp");
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
       
       
        writer.println("<div class=\"wrapper\"><div class=\"spacer\">");
        writer.println("<h1>Terminkalender</h1><BR />");
        
        if (this.errorMessage != null) {
            writer.println("<b>" + this.errorMessage + "</b><br />");
            this.errorMessage = null;
        }
        writer.println("<h2>Zum Verwalten der Termine loggen sie sich bitte ein</h2>");
        writer.println("<form action=\"login.jsp\" method=\"POST\">");
        writer.println("<label for=\"name\">Name:</label>");
            writer.println("<input type=\"text\" name=\"name\" id=\"name\" />");
            writer.println("<BR /><BR />");
            writer.println("<label for=\"password\">Passwort:</label>");
            writer.println("<input type=\"password\" name=\"password\" id=\"password\" />");
            writer.println("<BR /><BR />");
            writer.println("<input type=\"submit\" value=\"Eingabe\" />");
            writer.println("<BR /><BR />");
        writer.println("</form>");
        writer.println("<a href=\"register.jsp\">Registrieren</a>");
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
