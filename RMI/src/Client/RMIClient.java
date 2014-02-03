/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

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
    
    public RMIClient(){
        
    }
    
    public static void main(String args[]){
      RMIClient rmi =  new RMIClient();
      rmi.verbindungHerstellen();
    }
    
    private void verbindungHerstellen(){
        try{
            this.registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
            this.remote = (RemoteInterface) registry.lookup(Constant.RMI_ID);
        }catch(RemoteException | NotBoundException e){
            System.out.println("Verbindung fehlgeschlagen");
        }
    }
}
