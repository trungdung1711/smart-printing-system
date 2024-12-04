package com.Anonymous.smart_printing_system.exception;


public class PaperNotEnough extends
        RuntimeException
{
    public PaperNotEnough()
    {
        super("Paper not enough");
    }
}
