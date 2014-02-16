/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import IO.EinUndAusgabe;
import Interface.Constant;
import Interface.RemoteInterface;
import exceptions.PasswordEmptyException;
import exceptions.PasswordToShortException;
import exceptions.TerminIDEmptyException;
import exceptions.TerminWithNoDateException;
import exceptions.UsernameEmptyException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.ParseException;
import java.util.List;
import module.Terminart;
import module.Termine;

/**
 * Klasse zur Initialisierung eines Clients, der per Remote die Serverfunktionen
 * aufruft
 * @author Philipp Nardmann
 * 
 */
public class RMIClient {
    private Registry registry;
    private RemoteInterface remote;
    private final EinUndAusgabe io;
    private String username;
    private List<Termine> termine = null;
    
    /**
     * Konstruktor zum Erzeugen eines Clients
     */
    public RMIClient(){
        this.io = new EinUndAusgabe();
    }
    
    /**
     * Methode zum Ausführen und initialisieren des Clients
     * @param args
     */
    public static void main(String args[]){
      RMIClient rmi =  new RMIClient();
      rmi.verbindungHerstellen();
      rmi.dialog();
    }
    
    /*
    * Methode, um eine Verbindung zum Server herzustellen
    *
    */
    private void verbindungHerstellen(){
        try{
            this.registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
            this.remote = (RemoteInterface) registry.lookup(Constant.RMI_ID);
        }catch(RemoteException | NotBoundException e){
            System.out.println("Verbindung fehlgeschlagen");
        }
    }

    /**
     * 
     * Textbasiertes Auswahlmenü zum Bedienen des Clients
     */
    public void dialog() {
        int eingabe = -1;
        while (eingabe != 0) {
            System.out.println(
                    "Was wollen Sie?\n"
                    + " (0) Programm beenden\n"
                    + " (1) Nutzer registrieren\n"
                    + " (2) Anmelden");
            eingabe = this.io.leseInteger();
            switch (eingabe) {
                case 0:
                    break;
                case 1:
                    this.nutzerAnlegen();
                    break;
                case 2:
                     this.anmelden();
                    break;
            }
        }
    }
    
     /**
     * Methode zum Anlegen eines Nutzers. Es muss per Eingabefenster ein
     * Username und ein Passwort eingegeben werden
     * 
     */
    private void nutzerAnlegen(){
        
        String passwort;
        Boolean angelegt;
        try{
            System.out.println("Nutzernamen eingeben");
            this.username = this.io.leseString();
            System.out.println("Passwort eingeben");
            passwort = this.io.leseString();
            angelegt = this.remote.registrieren(this.username, passwort);
            if(angelegt){
               System.out.println("Nutzer wurde angelegt");
            }else{
                System.out.println("Da ist etwas schief gelaufen");
            }
        }catch(RemoteException re){
            System.out.println("Verbindung nicht möglich");
        }catch (PasswordEmptyException ex) {
            System.out.println("Kein Passwort angegeben");
        }catch (PasswordToShortException ex) {
           System.out.println("Passwort zu kurz");
        }catch (Exception ex) {
           System.out.println("Inerwarteter Fehler");
        }
            
    }
    /*
    * Methode zum Anmelden beim Server
    *
    */
    private void anmelden(){
        String passwort;
        Boolean login;
        try{
            System.out.println("Nutzernamen eingeben");
            this.username = this.io.leseString();
            System.out.println("Passwort eingeben");
            passwort = this.io.leseString();
            login = this.remote.login(this.username, passwort);
            if(login){
               System.out.println("Sie wurden eingeloggt!");
               this.termineAnzeigen();
               this.eingeloggtDialog();
            }else{
                System.out.println("Da ist etwas schief gelaufen");
            }
        }catch(RemoteException re){
            System.out.println("Verbindung nicht möglich");
        }catch (UsernameEmptyException ex) {
            System.out.println("Username nicht angegeben");
        } catch (Exception ex) {
            System.out.println("Unerwarteter Fehler");
        }
    }
    
    /**
     * Methode zum Anzeigen aller Termine des Nutzers
     */
    private void termineAnzeigen(){
        try{
          this.termine = this.remote.termineAnzeigenStart(this.username);
          if(termine==null){
              System.out.println("Keine Termine vorhanden");
          }else{
              for(Termine t : this.termine){
                  System.out.println(t.toString());
              }
          }
        }catch(RemoteException re){
            System.out.println("Verbindung fehlgeschlagen");
        }
    }
    
    /**
     * Methode zum Löschen eines Termins
     */
    private void terminLoeschen(){
        int nummer; 
        boolean geloescht;
        this.termineAnzeigen();
        System.out.println("Bitte Nummer angeben: ");
        nummer = this.io.leseInteger();
        try{
            geloescht = this.remote.terminLoeschen(nummer);
            if(geloescht){
                System.out.println("Termin wurde geloescht");
            }else{
                System.out.println("Termin konnte nicht gelöscht werden");
            }
        }catch(RemoteException re){
            System.out.println("Verbindung fehlgeschlagen");
        }catch(TerminIDEmptyException ie){
            System.out.println("Keine TerminId angegeben");
        }
    }
    
    /*
    * Methode zum Anlegen eines neuen Termins
    *
    */
    private void terminAnlegen(){
        String date_begin;
        String date_end;
        String title;
        String ort;
        Terminart art;
        int auswahl;
        Boolean angelegt;
        try{
            System.out.println("Termine im Format yyyy-MM-dd HH:mm angeben");
            System.out.println("Startdatum angeben");
            date_begin = this.io.leseString();
            System.out.println("Enddatum angeben");
            date_end = this.io.leseString();
            System.out.println("Termintitel angeben");
            title = this.io.leseString();
            System.out.println("Ort angeben");
            ort = this.io.leseString();
            System.out.println("Terminart wahlen\n"
                                +"(0) Geburtstag\n"
                                +"(1) Arbeit\n"
                                +"(2) Freizeit\n"
                                +"(3) Feiertag");
            auswahl = this.io.leseInteger();
            art = this.getTerminart(auswahl);
            angelegt = this.remote.terminAnlegen(date_begin, date_end, title, ort, art);
            if(angelegt){
                System.out.println("Termin wurde angelegt");
            }else{
                System.out.println("Etwas ist schief gelaufen");
            }
        }catch(RemoteException re){
            System.out.println("Verbindung fehlgeschlagen");
        }catch(TerminWithNoDateException te){
            System.out.println("Keine Termin Id angegeben");
        }catch(ParseException pe){
            System.out.println("Terminformat nicht eingehalten");
        }catch(Exception e){
            System.out.println("Unerwarteter Fehler");
        }
    }
    
    /*
    * Methode zum Bearbeiten eines vorhandenen Termins
    *
    */
    private void terminBearbeiten(){
        int nummer; 
        boolean geaendert;
        String date_begin;
        String date_end;
        String title;
        String ort;
        int auswahl;
        Terminart art;
        this.termineAnzeigen();
        System.out.println("Bitte Nummer angeben: ");
        nummer = this.io.leseInteger();
        try{
            System.out.println("Termine im Format yyyy-MM-dd HH:mm angeben");
            System.out.println("Startdatum angeben");
            date_begin = this.io.leseString();
            System.out.println("Enddatum angeben");
            date_end = this.io.leseString();
            System.out.println("Termintitel angeben");
            title = this.io.leseString();
            System.out.println("Ort angeben");
            ort = this.io.leseString();
            System.out.println("Terminart wahlen\n"
                                +"(0) Geburtstag\n"
                                +"(1) Arbeit\n"
                                +"(2) Freizeit\n"
                                +"(3) Feiertag");
            auswahl = this.io.leseInteger();
            art = this.getTerminart(auswahl);
            geaendert = this.remote.terminBearbeiten(nummer,title,date_begin,date_end,ort,art);
            if(geaendert){
                System.out.println("Termin wurde geändert");
            }else{
                System.out.println("Termin wurde nicht geändert");
            }
        }catch(RemoteException re){
            System.out.println("Verbindung fehlgeschlagen");
        }catch(TerminIDEmptyException te){
            System.out.println("Keine TerminId angegeben");
        }catch(ParseException e){
            System.out.println("Termin nicht im richtigen Format angegeben");
        }catch (Exception ex) {
           System.out.println("Unerwarteter Fehler");
        }
    }
    
     /**
     * Methode, welche den richtigen Enumtypen zurückgibt
     * @param auswahl Integerparameter der aus dem Eingabemenü übergeben wird
     */
    private Terminart getTerminart(int auswahl){
        Terminart rueckgabe = null;
        switch(auswahl){
            case 0:
                rueckgabe = Terminart.BIRTHDAY;
                break;
            case 1:
                rueckgabe = Terminart.WORK;
                break;
            case 2:
                rueckgabe = Terminart.FREETIME;
                break;
            case 3:
                rueckgabe = Terminart.HOLIDAY;
                break;
        }
        return rueckgabe;
    }
    
    /**
     * Methode zur Nutzerführung. Bietet die Möglichkeit die implementierten
     * Methoden zu benutzen
     */
    private void eingeloggtDialog(){
        int eingabe = -1;
        while (eingabe != 0) {
            System.out.println(
                    "Was wollen Sie?\n"
                    + " (0) abmelden\n"
                    + " (1) Termin anlegen\n"
                    + " (2) Termin bearbeiten\n"
                    + " (3) Termin loeschen\n"
                    + " (4) alle Termine anzeigen");
            eingabe = this.io.leseInteger();
            switch (eingabe) {
                case 0:
                    break;
                case 1:
                    this.terminAnlegen();
                    break;
                case 2:
                    this.terminBearbeiten();
                    break;
                case 3:
                    this.terminLoeschen();
                    break;
                case 4:
                    this.termineAnzeigen();
                    break;
            }
        }
    };
}
