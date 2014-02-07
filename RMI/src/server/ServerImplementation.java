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
import exceptions.TerminWithNoDateException;
import exceptions.UsernameEmptyException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.util.List;
import module.Nutzer;
import module.Terminart;
import module.Termine;

/**
 *
 * @author Nugget
 */
public class ServerImplementation extends UnicastRemoteObject implements RemoteInterface{
    private final TermineController termine = new TermineController();
    private final NutzerController nutzer = new NutzerController();
    private Nutzer aktuellerNutzer;
    
    public ServerImplementation() throws RemoteException{
        super();
    }
    

    @Override
    public boolean login(String username, String password) throws RemoteException {
        try{
            this.aktuellerNutzer = this.nutzer.getUser(username, password);
            return true;
        }catch(PasswordEmptyException pe){
            System.out.println("Passwort leer");
            return false;
        }catch(UsernameEmptyException ue){
            System.out.println("Benutzername leer");
            return false;
        }catch(Exception e){
            System.out.println("Nutzer nicht gefunden");
        return false;
        }
    }

    @Override
    public boolean logout() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean terminAnlegen(String date_begin, String date_end, String title,  String ort, Terminart art) throws RemoteException {
       try{
           this.termine.setTermin(date_begin, date_end, title, ort, art, this.aktuellerNutzer.getName());
           return true;
       }catch(TerminWithNoDateException t){
           System.out.println("Termin hat keine Daten");
           return false;
       }catch(ParseException p){
           System.out.println("Parsing-Fehler");
           return false;
       }catch(Exception e){
           System.out.println("unerwarteter Fehler");
           e.printStackTrace();
           return false;
       }
    }

    @Override
    public boolean terminBearbeiten() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Termine> termineAnzeigen() throws RemoteException {
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
