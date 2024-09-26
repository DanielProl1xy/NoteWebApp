package web.springproject.controller.api;

import javax.security.auth.login.LoginException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import web.springproject.model.exceptions.InvalidNoteException;

@RestControllerAdvice
public class APIControllerAdvice {

    @ExceptionHandler(LoginException.class)
    public String OnAccessDenied() {
        return "{ \"result\" : \"invalid token\"}";
    }

    @ExceptionHandler(InvalidNoteException.class)
    public String OnInvalidNote() {
        return "{ \"result\" : \"invalidn note\"}";
    }
}
