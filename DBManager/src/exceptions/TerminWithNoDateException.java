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
public class TerminWithNoDateException extends Exception {

    public TerminWithNoDateException()
    {
    }
    
    public TerminWithNoDateException(String message)
    {
        super(message);
    }
}
