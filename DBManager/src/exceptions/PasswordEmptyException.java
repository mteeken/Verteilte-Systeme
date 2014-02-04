/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author mteeken
 */
public class PasswordEmptyException extends Exception {
    
    public PasswordEmptyException()
    {
    }
    
    public PasswordEmptyException(String message)
    {
        super(message);
    }
}
