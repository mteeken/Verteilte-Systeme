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
 *
 * @author Philipp Nardmann
 */
public interface RemoteInterface extends Remote {
    public boolean login(String username,String password)throws RemoteException
                    ,PasswordEmptyException,UsernameEmptyException,Exception;
    public boolean logout() throws RemoteException;
    public boolean terminAnlegen(String title, String date_begin, String date_end, 
           String ort, Terminart art) throws RemoteException,TerminWithNoDateException,ParseException,Exception;
    public boolean terminBearbeiten(Integer id, String title, String date_begin, String date_end, 
           String ort, Terminart art) throws RemoteException,Exception;
    public List<Termine> termineAnzeigenStart(String username) throws RemoteException;
    public boolean terminLoeschen(int id) throws RemoteException,TerminIDEmptyException;
    public boolean registrieren(String username,String passwort)throws RemoteException,UsernameEmptyException,
            PasswordEmptyException,PasswordToShortException,Exception;
    
}
