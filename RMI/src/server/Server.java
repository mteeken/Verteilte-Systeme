/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Interface.Constant;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Klasse, welche den Server startet, die Ports bereitstellt und so eine
 * Verbindung ermöglicht
 * @author Philipp Nardmann
 */
public class Server {

    /**
     * Ausführbare Methode zum Starten des Servers
     * @param args 
     * @throws RemoteException tritt bei Verbindungsfehlern auf
     */
    public static void main(String[] args) throws RemoteException{
        try
        {
            ServerImplementation server = new ServerImplementation();
            Registry registry = LocateRegistry.createRegistry(Constant.RMI_PORT);
            registry.rebind(Constant.RMI_ID,server);
            System.out.println("Registry gestartet und NewsServer gebunden.");
        }
        catch(RemoteException re)
        {   
            System.out.println("Server konnte nicht gestartet werden");
            System.out.println(re);
        }
    }
}
