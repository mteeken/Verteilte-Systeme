/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package verteilte.db.controller;

import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author mteeken
 */
public class NutzerController {
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Verteilte_DB_ManagerPU");
    private EntityManager em = emf.createEntityManager();

    public void getUser(String name, String password) throws DigestException {
        
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            byte[] md = new byte[ 8192 ];
            messageDigest.update(md, 0, password);
            messageDigest.
            md = messageDigest.digest();
        } catch (NoSuchAlgorithmException cnse) {
            throw new DigestException("couldn't make digest of partial content");
        }

        
        password = 
    }

}