package com.zidio.resume_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() { super(); }
    public InvalidCredentialsException(String message) { super(message); }
    public InvalidCredentialsException(String message, Throwable cause) { super(message, cause); }
}
