/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import IO.EinUndAusgabe;
import Interface.Constant;
import Interface.RemoteInterface;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import module.Terminart;
import module.Termine;

/**
 *
 * @author Philipp Nardmann
 */
public class RMIClient {
    private Registry registry;
    private RemoteInterface remote;
    private final EinUndAusgabe io;
    private String username;
    private List<Termine> termine = null;
    
    public RMIClient(){
        this.io = new EinUndAusgabe();
    }
    
    public static void main(String args[]){
      RMIClient rmi =  new RMIClient();
      rmi.verbindungHerstellen();
      rmi.dialog();
    }
    
    private void verbindungHerstellen(){
        try{
            this.registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
            this.remote = (RemoteInterface) registry.lookup(Constant.RMI_ID);
        }catch(RemoteException | NotBoundException e){
            System.out.println("Verbindung fehlgeschlagen");
        }
    }
    
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
        
    private void nutzerAnlegen(){
        String username;
        String passwort;
        Boolean angelegt;
        try{
            System.out.println("Nutzernamen eingeben");
            username = this.io.leseString();
            System.out.println("Passwort eingeben");
            passwort = this.io.leseString();
            angelegt = this.remote.registrieren(username, passwort);
            if(angelegt){
               System.out.println("Nutzer wurde angelegt");
            }else{
                System.out.println("Da ist etwas schief gelaufen");
            }
        }catch(RemoteException re){
            System.out.println("Verbindung nicht möglich");
        }
            
    }
    
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
        }
    }
    
    public void termineAnzeigen(){
        try{
          this.termine = this.remote.termineAnzeigenStart(this.username);
          if(termine==null){
              System.out.println("Keine Termine vorhanden");
          }else{
              for(Termine t : this.termine){
                  t.toString();
              }
          }
        }catch(RemoteException re){
            
        }
    }
    
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
        }
    }
    
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
    
    private void eingeloggtDialog(){
        int eingabe = -1;
        while (eingabe != 0) {
            System.out.println(
                    "Was wollen Sie?\n"
                    + " (0) abmelden\n"
                    + " (1) Termin anlegen\n"
                    + " (2) Termin bearbeiten\n"
                    + " (3) Termin loeschen");
            eingabe = this.io.leseInteger();
            switch (eingabe) {
                case 0:
                    break;
                case 1:
                    this.terminAnlegen();
                    break;
                case 2:
                    break;
            }
        }
    };
}
