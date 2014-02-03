/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Philipp Nardmann
 */
public interface RemoteInterface extends Remote {
    public boolean login(String username,String password) throws RemoteException;
    public boolean logout() throws RemoteException;
    public boolean terminAnlegen() throws RemoteException;
    public boolean terminBearbeiten() throws RemoteException;
    public boolean termineAnzeigen() throws RemoteException;
    public boolean terminLoeschen() throws RemoteException;
    
}
