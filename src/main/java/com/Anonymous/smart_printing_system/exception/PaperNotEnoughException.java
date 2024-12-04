package com.Anonymous.smart_printing_system.exception;


public class PaperNotEnoughException extends
        RuntimeException
{
    public PaperNotEnoughException()
    {
        super("Paper not enough");
    }
}
