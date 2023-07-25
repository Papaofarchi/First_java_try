package org.example.exceptions;

public class ChatNotFoundException extends RuntimeException{
    public ChatNotFoundException() {
        super("CHAT NOT FOUND");
    }
}
