package com.epam.lab.controller;

import com.epam.lab.exception.RepositoryException;
import com.epam.lab.exception.ServiceException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(ServiceException.class)
    public void serviceError(final HttpServletResponse response, final Exception e) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(RepositoryException.class)
    public String repositoryError() {
        return "Repository error";
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public void dbError(final HttpServletResponse response, final Exception e) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
    }
}
