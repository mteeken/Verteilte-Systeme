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
import java.util.ArrayList;
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

    private List<String> monate = new ArrayList<String>();
    
    public TermineController() {
        monate.add("Januar");
        monate.add("Februar");
        monate.add("März");
        monate.add("April");
        monate.add("Mai");
        monate.add("Juni");
        monate.add("Juli");
        monate.add("August");
        monate.add("September");
        monate.add("Oktober");
        monate.add("November");
        monate.add("Dezember");
    }
    
    public Termine getTermin(String username, Integer id) throws Exception {
        
        if (id == null) {
            throw new TerminIDEmptyException();
        }

        try {
            Nutzer n = new NutzerController().getUserByName(username);
            return em.createNamedQuery("termine.find", Termine.class)
                .setParameter("id", id).setParameter("username", n.getName()).getSingleResult();
        } catch (Exception e) {
            throw new Exception("Termin konnte nicht gefunden werden");
        }   
    }
    
    public List<Termine> getNextXTermine(String username, int nummer) throws Exception {

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
            Nutzer n = new NutzerController().getUserByName(username);
            return em.createNamedQuery("termine.findNextX", Termine.class)
                .setParameter("date", date)
                .setParameter("username", n.getName())
                .setMaxResults(nummer)
                .getResultList();
        } catch (Exception e) {
            throw new Exception("Keine Termine vorhanden");
        }
    }
    
    
   public List<Termine> getAllTermine(String username) throws Exception{
        try {
            Nutzer n = new NutzerController().getUserByName(username);
            return this.em.createQuery("Select t FROM Termine t", Termine.class)
                    .setParameter("username", n.getName())
                    .getResultList();
        } catch (Exception e) {
            throw new Exception("Keine Termine vorhanden");
        }
   }
    
    public List<Termine> getByMonth(String username, int month) throws Exception {

        if (month <= 0) {
            throw new InvalidParameterException();
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date_begin = calendar.getTime();
        calendar.set(Calendar.MONTH, month);
        Date date_end = calendar.getTime();
        try {
            Nutzer n = new NutzerController().getUserByName(username);
            return em.createNamedQuery("termine.findByMonth", Termine.class)
                .setParameter("date_begin", date_begin)
                .setParameter("date_end", date_end)
                .setParameter("username", n.getName())
                .getResultList();
        } catch (Exception e) {
            throw new Exception("Keine Termine vorhanden");
        }
    }
    
    public void setTermin(String title, String date_begin, String date_end, 
           String ort, Terminart art, String username) throws Exception {

        if (date_begin == null || date_end == null)
            throw new TerminWithNoDateException("Bitte ein Start und Enddatum angeben");
        
        try {
            Nutzer n = new NutzerController().getUserByName(username);
            Termine t = new Termine(title,date_begin, date_end, ort, art, n);
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        } catch (ParseException e) {
            throw new ParseException("Start oder Enddatum ist fehlerhaft", 0);
        } catch (Exception e) {
            throw new Exception("Termin konnte nicht gefunden werden");
        }   
    }

    public void modifyTermin(Integer id, String title, String date_begin, String date_end, 
           String ort, Terminart art) throws Exception {
        
        if (id == null) {
            throw new TerminIDEmptyException("Es wurde kein Termin angegeben");
        }

        try {
            Termine t = em.createNamedQuery("termine.find", Termine.class)
                .setParameter("id", id).getSingleResult();
            
            t.setTitle(title);
            t.setDate_begin(date_begin);
            t.setDate_end(date_end);
            t.setOrt(ort);
            t.setArt(art);
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
        } catch (ParseException e) {
            throw new Exception("Termin konnte nicht geändert werden. Start oder Enddatum ist falsch.");
        } catch (Exception e) {
            throw new Exception("Termin konnte nicht geändert werden");
        }   
    }
    
    public void deleteTermin(Integer id) throws Exception {
        if (id == null) {
            throw new TerminIDEmptyException("Es wurde kein Termin angegeben");
        }
        try {
             Termine t = em.createNamedQuery("termine.find", Termine.class)
                .setParameter("id", id).getSingleResult();
             
            em.getTransaction().begin();
            em.remove(t);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Termin konnte nicht gelöscht werden");
        } 
    }
    
    public String getMonat(int index) {
        return monate.get(index - 1);
    }
}
