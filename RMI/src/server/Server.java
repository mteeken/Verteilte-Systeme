/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Interface.Constant;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Nugget
 */
public class Server {
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
