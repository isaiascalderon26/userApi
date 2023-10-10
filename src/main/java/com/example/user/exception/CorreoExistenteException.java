package com.example.user.exception;

public class CorreoExistenteException extends RuntimeException {
    public CorreoExistenteException(String mensaje) {
        super(mensaje);
    }
}