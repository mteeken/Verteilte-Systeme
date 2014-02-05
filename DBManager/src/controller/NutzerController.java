/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import module.Nutzer;
import exceptions.PasswordEmptyException;
import exceptions.UsernameEmptyException;
import exceptions.PasswordToShortException;
/**
 *
 * @author mteeken
 */
public class NutzerController {
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Verteilte_DB_ManagerPU");
    private EntityManager em = emf.createEntityManager();

    public Nutzer getUser(String name, String password) throws Exception {
        
        if (password == null || password.equals(""))
            throw new PasswordEmptyException();

        if (name == null || name.equals(""))
            throw new UsernameEmptyException();

        try {
            return em.createNamedQuery("nutzer.find", Nutzer.class)
                .setParameter("name", name)
                .setParameter("password", this.hash(password)).getSingleResult();
         } catch (Exception e) {
            throw new Exception("Nutzer konnte nicht gefunden werden");
        }   
    }
    
    public Nutzer getUserByName(String name) throws Exception {
        
        if (name == null || name.equals(""))
            throw new UsernameEmptyException();

        try {
            return em.createNamedQuery("nutzer.findByName", Nutzer.class)
                .setParameter("name", name).getSingleResult();
         } catch (Exception e) {
            throw new Exception("Nutzer konnte nicht gefunden werden");
        }   
    }
    
    public Nutzer setUser(String name, String password) throws Exception {
        
        if (password == null || password.equals(""))
            throw new PasswordEmptyException("Bitte ein Passwort eingeben");

        if (password.length() < 5)
            throw new PasswordToShortException("Minimum password length is five characters");
        
        if (name == null || name.equals(""))
            throw new UsernameEmptyException("Bitte einen Namen eingeben");

        Nutzer n = new Nutzer(name, this.hash(password));

        try {
            em.getTransaction().begin();
            em.persist(n);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception("Nutzer konnte nicht angelegt werden");
        }
        
        return n;
    }
        
    private String hash(String password) throws DigestException {
        String result = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            byte[] md = new byte[ 8192 ];
            md = password.getBytes();

            for (int i=0; i < md.length; i++) {
              result +=
                    Integer.toString( ( md[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
        } catch (NoSuchAlgorithmException cnse) {
            throw new DigestException("couldn't make digest of partial content");
        }
        
        return result;
    }
}
