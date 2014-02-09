/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbmanager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author mteeken
 */
public class DBManager {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Verteilte_DB_ManagerPU");
    private EntityManager em = emf.createEntityManager();
    public void init() {
        em.getTransaction().begin();
        em.getTransaction().commit();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBManager vm = new DBManager();
        vm.init();
    }
}
