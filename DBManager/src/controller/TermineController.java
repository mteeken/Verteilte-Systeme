/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import exceptions.TerminIDEmptyException;
import exceptions.TerminWithNoDateException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import module.Nutzer;
import module.Terminart;
import module.Termine;

/**
 *
 * @author mteeken
 */
public class TermineController {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Verteilte_DB_ManagerPU");
    private EntityManager em = emf.createEntityManager();

    public Termine getTermin(Integer id) throws Exception {
        
        if (id == null) {
            throw new TerminIDEmptyException();
        }

        try {
            return em.createNamedQuery("termine.find", Termine.class)
                .setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new Exception("Termin konnte nicht gefunden werden");
        }   
    }
    
    public List<Termine> getNextXTermine(int nummer) throws Exception {

        if (nummer <= 0) {
            throw new InvalidParameterException();
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        try {
            return em.createNamedQuery("termine.findNextX", Termine.class)
                .setParameter("date", date.getTime())
                .setMaxResults(nummer).getResultList();
        } catch (Exception e) {
            throw new Exception("Keine Termine vorhanden");
        }
    }
    
    public void setTermin(String date_begin, String date_end, String title,
           String ort, Terminart art, String username) throws Exception {

        if (date_begin == null || date_end == null)
            throw new TerminWithNoDateException("Bitte ein Start und Enddatum angeben");
        
        try {
            Nutzer n = new NutzerController().getUserByName(username);
            Termine t = new Termine(date_begin, date_end, title, ort, art, n);
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        } catch (ParseException e) {
            throw new ParseException("Start oder Enddatum ist fehlerhaft", 0);
        } catch (Exception e) {
            throw new Exception("Termin konnte nicht gefunden werden");
        }   
    }
}
