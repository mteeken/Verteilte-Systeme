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

/**
 *
 * @author Philipp Nardmann
 */
public class RMIClient {
    private Registry registry;
    private RemoteInterface remote;
    private EinUndAusgabe io;
    private Boolean angemeldet;
    
    public RMIClient(){
        this.io = new EinUndAusgabe();
        this.angemeldet = false;
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
                    + " (1) Nutzer registrieren");
            eingabe = this.io.leseInteger();
            switch (eingabe) {
                case 0:
                    break;
                case 1:
                    this.nutzerAnlegen();
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
}
