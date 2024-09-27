package web.springproject.controller.api;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import web.springproject.model.exceptions.InvalidNoteException;
import web.springproject.model.exceptions.UserLoginException;

@RestControllerAdvice
public class APIControllerAdvice {

    @ExceptionHandler(UserLoginException.class)
    public String OnAccessDenied() {
        return "{ \"result\" : \"invalid token\"}";
    }

    @ExceptionHandler(InvalidNoteException.class)
    public String OnInvalidNote() {
        return "{ \"result\" : \"invalidn note\"}";
    }
}
