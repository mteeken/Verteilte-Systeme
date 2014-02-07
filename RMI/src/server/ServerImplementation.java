/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Interface.RemoteInterface;
import controller.NutzerController;
import controller.TermineController;
import java.rmi.RemoteException;
import exceptions.PasswordEmptyException;
import exceptions.PasswordToShortException;
import exceptions.TerminIDEmptyException;
import exceptions.TerminWithNoDateException;
import exceptions.UsernameEmptyException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nugget
 */
public class ServerImplementation extends UnicastRemoteObject implements RemoteInterface{
    private final TermineController termine = new TermineController();
    private final NutzerController nutzer = new NutzerController();
    
    public ServerImplementation() throws RemoteException{
        super();
    }
    

    @Override
    public boolean login(String username, String password) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean logout() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean terminAnlegen() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean terminBearbeiten() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean termineAnzeigen() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean terminLoeschen() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean registrieren(String username, String passwort) throws RemoteException {
        try{
            this.nutzer.setUser(username, passwort);
            return true;
        }catch(UsernameEmptyException ue){
                System.out.println("Username leer");
            return false;
        }catch(PasswordEmptyException pe ){
            System.out.println("Passwort leer");
            return false;
        }catch(PasswordToShortException pe){
            System.out.println("Passwort zu kurz");
            return false;
        } catch (Exception ex) {
            System.out.println("unerwarteter Fehler");
            return false;
        }
    }
    
}
