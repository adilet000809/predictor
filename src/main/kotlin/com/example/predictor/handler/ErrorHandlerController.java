package com.example.predictor.handler;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleException (MissingServletRequestParameterException e) {
        return "400";
    }

}
