/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import module.Terminart;
import module.Termine;

/**
 *
 * @author Philipp Nardmann
 */
public interface RemoteInterface extends Remote {
    public boolean login(String username,String password) throws RemoteException;
    public boolean logout() throws RemoteException;
    public boolean terminAnlegen(String title, String date_begin, String date_end, 
           String ort, Terminart art) throws RemoteException;
    public boolean terminBearbeiten() throws RemoteException;
    public List<Termine> termineAnzeigenStart(String username) throws RemoteException;
    public boolean terminLoeschen() throws RemoteException;
    public boolean registrieren(String username,String passwort) throws RemoteException;
    
}
