package web.springproject.controller;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import web.springproject.model.exceptions.InvalidNoteException;
import web.springproject.model.exceptions.UserLoginException;

@ControllerAdvice
public class AppControllerAdvice {
    
    @ExceptionHandler(UserLoginException.class)
    public String OnAccessDenied() {
        return "redirect: /login";
    }

    @ExceptionHandler(InvalidNoteException.class)
    public String OnInvalidNote() {
        return "redirect: /notes";
    }
}
