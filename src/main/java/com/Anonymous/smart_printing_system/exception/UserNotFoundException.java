package com.Anonymous.smart_printing_system.exception;


public class UserNotFoundException
        extends RuntimeException
{
    public UserNotFoundException(Long id)
    {
        super("User not found with id " + id);
    }
}
