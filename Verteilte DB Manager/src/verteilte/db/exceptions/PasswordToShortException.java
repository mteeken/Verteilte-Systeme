/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package verteilte.db.exceptions;

/**
 *
 * @author mteeken
 */
public class PasswordToShortException extends Exception {
    
    public PasswordToShortException()
    {
    }
    
    public PasswordToShortException(String message)
    {
        super(message);
    }
}
