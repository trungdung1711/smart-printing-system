package com.Anonymous.smart_printing_system.exception;


public class UserAlreadyExistsException extends RuntimeException
{
    public UserAlreadyExistsException(String message)
    {
        super(message);
    }
}
