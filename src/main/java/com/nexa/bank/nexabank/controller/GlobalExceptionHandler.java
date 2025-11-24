package com.nexa.bank.nexabank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    public String handleAll(Exception ex, Model model) {
        log.error("Unhandled exception while processing request", ex);
        model.addAttribute("errorMessage", ex.toString());
        String stack = Arrays.stream(ex.getStackTrace())
                .map(StackTraceElement::toString)
                .limit(500)
                .collect(Collectors.joining("<br/>"));
        model.addAttribute("errorStack", stack);
        return "error/internal-error";
    }
}
