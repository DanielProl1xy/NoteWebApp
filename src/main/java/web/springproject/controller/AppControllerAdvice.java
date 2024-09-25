package web.springproject.controller;


import javax.security.auth.login.LoginException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import web.springproject.model.exceptions.InvalidNoteException;

@ControllerAdvice
public class AppControllerAdvice {
    
    @ExceptionHandler(LoginException.class)
    public String OnAccessDenied() {
        return "redirect: /login";
    }

    @ExceptionHandler(InvalidNoteException.class)
    public String OnInvalidNote() {
        return "redirect: /notes";
    }
}
