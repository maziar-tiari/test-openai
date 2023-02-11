package com.buddies.services.userprofile.exceptions;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	protected ResponseEntity<Object> handleConfict(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(value = { 
			IllegalArgumentException.class, 
			JsonProcessingException.class, 
			ConstraintViolationException.class,
			NullPointerException.class
	})
	protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { NoSuchElementException.class })
	protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(
          "Subject not found", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(value = { ConfigurationException.class, MappingException.class,
			OptimisticLockingFailureException.class })
	protected ResponseEntity<Object> handleInternalError(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
