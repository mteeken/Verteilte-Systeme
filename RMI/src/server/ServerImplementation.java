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
    }
    

    @Override
    public boolean login(String username, String password) throws RemoteException
                    ,PasswordEmptyException,UsernameEmptyException,Exception{
        try{
            this.aktuellerNutzer = this.nutzer.getUser(username, password);
            return true;
        }catch(  PasswordEmptyException | UsernameEmptyException pe){
            throw pe;
        }catch(Exception e){
            System.out.println("Nutzer nicht gefunden");
            throw e;
        }
    }

    @Override
    public boolean logout() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean terminAnlegen(String date_begin, String date_end, String title,  String ort, Terminart art) throws RemoteException
    ,TerminWithNoDateException,ParseException,Exception{
       try{
           this.termine.setTermin(title, date_begin,date_end,ort,art,this.aktuellerNutzer.getName());
           return true;
       }catch(TerminWithNoDateException t){
           throw t;
       }catch(ParseException p){
           throw p;
       }catch(Exception e){
           throw e;
       }
    }

    @Override
    public boolean terminBearbeiten(Integer id, String title, String date_begin, String date_end, 
           String ort, Terminart art) throws RemoteException, TerminIDEmptyException,ParseException {
        try{
            this.termine.modifyTermin(id, title, date_begin, date_end, ort, art);
            return true;
        }catch(  TerminIDEmptyException | ParseException e){
            throw e;
        }catch(Exception ex) {
            System.out.println("Unerwarter Fehler");
            return false;
        }
    }

    @Override
    public List<Termine> termineAnzeigenStart(String username) throws RemoteException {
        try{
            return this.termine.getAllTermine(username);
        }catch(Exception e){
            System.out.println("Keine Termine vorhanden");
            return null;
        }
    }

    @Override
    public boolean terminLoeschen(int id) throws RemoteException, TerminIDEmptyException{
        try{
           this.termine.deleteTermin(id);
           return true;
        }catch(TerminIDEmptyException ie){
            throw ie;
        }catch(Exception e){
            System.out.println("unerwarteter Fehler");
            return false;
        }
    }

    @Override
    public boolean registrieren(String username, String passwort) throws RemoteException,UsernameEmptyException,
            PasswordEmptyException,PasswordToShortException,Exception {
        try{
            this.nutzer.setUser(username, passwort,passwort);
            return true;
        }catch(UsernameEmptyException ue){
            throw ue;
        }catch(PasswordEmptyException pe ){
            throw pe;
        }catch(PasswordToShortException pe){
            throw pe;
        }catch (Exception ex) {
            System.out.println("unerwarteter Fehler");
            throw ex;
        }
    }
    
}
