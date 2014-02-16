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
 * Klasse, welche die Servermethoden des Terminkalenders implementiert
 * @author Philipp Nardmann
 */
public class ServerImplementation extends UnicastRemoteObject implements RemoteInterface{
    private TermineController termine = new TermineController();
    private NutzerController nutzer = new NutzerController();
    private Nutzer aktuellerNutzer;
    
    /**
     * Konstruktor um ein Serverobjekt zu erzeugen
     * @throws RemoteException tritt bei Verbindungsfehlern auf
     */
    public ServerImplementation() throws RemoteException{
    }

    /**
     * Methode, die ermittelt ob ein Nutzer in der Datenbank vorhanden ist, um
     * Zugriff auf die weiteren Servermethoden zu gewähren
     * @param username String zur Identifizierung des Nutzers
     * @param password String zur Authentifizierung des Nutzers
     * @return
     * @throws RemoteException  wird bei Fehlverbindung geworfen
     * @throws PasswordEmptyException wird geworfen, wenn kein Passwort übergeben
     * wurde
     * @throws UsernameEmptyException wird geworden, wenn kein Nutzername 
     * übergeben wurde
     * @throws Exception wird geworfen, falls ein nicht-identifizierter Fehler
     * auftritt
     */
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

    /**
     * Methode um einen neuen Termin in die Datenbank zu schreiben.
     * @param date_begin String als Anfangsdatum des Termins
     * @param date_end String als Enddatum des Termins
     * @param title String alsTitel des Termins
     * @param ort  String als Ort des Termins
     * @param art Art des Termins (als Auswahl einer Enumeration)
     * @return Rückmeldung, ob der Termin angelegt wurde.
     * @throws RemoteException Tritt bei Verbindungsfehler auf
     * @throws TerminWithNoDateException Tritt auf wenn kein Datum eingegeben
     * wurde
     * @throws ParseException tritt auf falls das Datum falsch angegeben 
     * @throws Exception tritt bei einem unerwarteten Fehler auf
     */
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

    /**
     * Methode die einen bereits vorhandenen Termin des Nutzers anpasst
     * @param id Integer : Parameter zur Identifikation des Termins der geändert werden
     * soll
     * @param title String : neuer Titel des Termins
     * @param date_begin String : neues Anfangsdatum des Termins
     * @param date_end String : neues Enddatum des Termins
     * @param ort String : neuer Ort des Termins
     * @param art Terminart : neuer Typ des Termins
     * @return Rückmeldung, ob der Termin geändert wurde.
     * @throws RemoteException tritt bei Verbindungsfehler auf
     * @throws ParseException  tritt auf falls das Datum falsch angegeben 
     * @throws TerminIDEmptyException tritt auf falls keine ID des Termins
     * angegeben wurde
     */
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

    /**
     * Gibt bei der Anmeldung eines Nutzers alle Termine zurück
     * @param username String : Nutzername zur Identifikation der Termine
     * @return Liste aller Termine des Nutzers
     * @throws RemoteException tritt bei Verbindungsfehlern auf
     */
    @Override
    public List<Termine> termineAnzeigenStart(String username) throws RemoteException {
        try{
            return this.termine.getAllTermine(username);
        }catch(Exception e){
            System.out.println("Keine Termine vorhanden");
            return null;
        }
    }

    /**
     * Methode zum Löschen eines Termins 
     * @param id Integer : Identifikationsnummer des zu löschenden Termins
     * @return Rückmeldung, ob der Termin geändert wurde.
     * @throws RemoteException tritt bei Verbindungsfehler auf
     * @throws TerminIDEmptyException tritt auf falls keine ID des Termins
     * angegeben wurde
     */
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

     /**
     * Methode, die ermittelt ob ein Nutzer in der Datenbank vorhanden ist, um
     * Zugriff auf die weiteren Servermethoden zu gewähren
     * @param username String : Nutzername zur Identifizierung des Nutzers
     * @param passwort String : Passwort zur Authentifizierung des Nutzers
     * @return
     * @throws RemoteException  wird bei Fehlverbindung geworfen
     * @throws PasswordEmptyException wird geworfen, wenn kein Passwort übergeben
     * wurde
     * @throws UsernameEmptyException wird geworden, wenn kein Nutzername 
     * übergeben wurde
     * @throws Exception wird geworfen, falls ein nicht-identifizierter Fehler
     * auftritt
     */
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

    @Override
    public List<Termine> nextXTermine(String username) throws RemoteException {
         try{
            return this.termine.getNextXTermine(username, 10);
        }catch(Exception e){
            System.out.println("Keine Termine vorhanden");
            return null;
        }
    }
    
}
