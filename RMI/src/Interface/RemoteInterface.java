/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import exceptions.PasswordEmptyException;
import exceptions.PasswordToShortException;
import exceptions.TerminIDEmptyException;
import exceptions.TerminWithNoDateException;
import exceptions.UsernameEmptyException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.List;
import module.Terminart;
import module.Termine;

/**
 * Klassen zur Schnittstellendeklaration
 * @author Philipp Nardmann
 */
public interface RemoteInterface extends Remote {


    /**
     * Methode, die ermittelt ob ein Nutzer in der Datenbank vorhanden ist, um
     * Zugriff auf die weiteren Servermethoden zu gewähren
     * @param username Nutzername zur Identifizierung des Nutzers
     * @param password Passwort zur Authentifizierung des Nutzers
     * @return
     * @throws RemoteException  wird bei Fehlverbindung geworfen
     * @throws PasswordEmptyException wird geworfen, wenn kein Passwort übergeben
     * wurde
     * @throws UsernameEmptyException wird geworden, wenn kein Nutzername 
     * übergeben wurde
     * @throws Exception wird geworfen, falls ein nicht-identifizierter Fehler
     * auftritt
     */
    public boolean login(String username,String password)throws RemoteException
                    ,PasswordEmptyException,UsernameEmptyException,Exception;
        /**
     * Methode um einen neuen Termin in die Datenbank zu schreiben.
     * @param date_begin Anfangsdatum des Termins
     * @param date_end Enddatum des Termins
     * @param title Titel des Termins
     * @param ort Ort des Termins
     * @param art Art des Termins (als Auswahl einer Enumeration)
     * @return Rückmeldung, ob der Termin angelegt wurde.
     * @throws RemoteException Tritt bei Verbindungsfehler auf
     * @throws TerminWithNoDateException Tritt auf wenn kein Datum eingegeben
     * wurde
     * @throws ParseException tritt auf falls das Datum falsch angegeben 
     * @throws Exception tritt bei einem unerwarteten Fehler auf
     */
    public boolean terminAnlegen(String title, String date_begin, String date_end, 
           String ort, Terminart art) throws RemoteException,TerminWithNoDateException,ParseException,Exception;
       /**
     * Methode die einen bereits vorhandenen Termin des Nutzers anpasst
     * @param id Parameter zur Identifikation des Termins der geändert werden
     * soll
     * @param title neuer Titel des Termins
     * @param date_begin neues Anfangsdatum des Termins
     * @param date_end neues Enddatum des Termins
     * @param ort neuer Ort des Termins
     * @param art neuer Typ des Termins
     * @return Rückmeldung, ob der Termin geändert wurde.
     * @throws RemoteException tritt bei Verbindungsfehler auf
     * @throws ParseException  tritt auf falls das Datum falsch angegeben 
     * @throws TerminIDEmptyException tritt auf falls keine ID des Termins
     * angegeben wurde
     */
    public boolean terminBearbeiten(Integer id, String title, String date_begin, String date_end, 
           String ort, Terminart art) throws RemoteException,Exception;
       /**
     * Gibt bei der Anmeldung eines Nutzers alle Termine zurück
     * @param username Nutzername zur Identifikation der Termine
     * @return Liste aller Termine des Nutzers
     * @throws RemoteException tritt bei Verbindungsfehlern auf
     */
    public List<Termine> termineAnzeigenStart(String username) throws RemoteException;
        /**
     * Methode zum Löschen eines Termins 
     * @param id Identifikationsnummer des zu löschenden Termins
     * @return Rückmeldung, ob der Termin geändert wurde.
     * @throws RemoteException tritt bei Verbindungsfehler auf
     * @throws TerminIDEmptyException tritt auf falls keine ID des Termins
     * angegeben wurde
     */
    public boolean terminLoeschen(int id) throws RemoteException,TerminIDEmptyException;
     /**
     * Methode, die ermittelt ob ein Nutzer in der Datenbank vorhanden ist, um
     * Zugriff auf die weiteren Servermethoden zu gewähren
     * @param username Nutzername zur Identifizierung des Nutzers
     * @param passwort Passwort zur Authentifizierung des Nutzers
     * @return
     * @throws RemoteException  wird bei Fehlverbindung geworfen
     * @throws PasswordEmptyException wird geworfen, wenn kein Passwort übergeben
     * wurde
     * @throws UsernameEmptyException wird geworden, wenn kein Nutzername 
     * übergeben wurde
     * @throws Exception wird geworfen, falls ein nicht-identifizierter Fehler
     * auftritt
     */
    public boolean registrieren(String username,String passwort)throws RemoteException,UsernameEmptyException,
            PasswordEmptyException,PasswordToShortException,Exception;
    
}
