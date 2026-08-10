package com.techtez.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class EmployeeErrorHandler {

	private static final Logger logger = LogManager.getLogger(EmployeeErrorHandler.class);

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorDetails> handleIAE(IllegalArgumentException iae, HttpServletRequest req) {

		logger.error("IllegalArgumentException occurred at {} : {}", req.getRequestURI(), iae.getMessage(), iae);

		ErrorDetails details = new ErrorDetails();
		details.setTime(LocalDateTime.now());
		details.setMsg(iae.getMessage());
		details.setStatus(HttpStatus.BAD_REQUEST.value());
		details.setPath(req.getRequestURI());
		return new ResponseEntity<ErrorDetails>(details, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDetails> handleIAE(RuntimeException re, HttpServletRequest req) {

		logger.error("RuntimeEception Occurred at {} : {}", req.getRequestURI(), re.getMessage(), re);

		ErrorDetails details = new ErrorDetails();
		details.setTime(LocalDateTime.now());
		details.setMsg(re.getMessage());
		details.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		details.setPath(req.getRequestURI());
		return new ResponseEntity<ErrorDetails>(details, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> handleValidationErrors(
	        MethodArgumentNotValidException ex,
	        HttpServletRequest request) {
		
		logger.error("Validation failed at {}",request.getRequestURI(),ex);

	    List<String> errors = ex.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .map(error -> error.getField() + " : " + error.getDefaultMessage())
	            .toList();

	    ErrorDetails details = new ErrorDetails();
	           details.setTime(LocalDateTime.now());
	           details.setMsg("Validation Failed");
	           details.setStatus(HttpStatus.BAD_REQUEST.value());
	           details.setPath(request.getRequestURI());
	           details.setErrors(errors);

	    return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorDetails> handleInvalidJson(
	        HttpMessageNotReadableException ex,
	        HttpServletRequest request) {
	        
	    ErrorDetails response = new ErrorDetails();

	    response.setTime(LocalDateTime.now());
	    response.setStatus(HttpStatus.BAD_REQUEST.value());
	    response.setMsg("Invalid JSON request."+ex.getMessage());
	    response.setPath(request.getRequestURI());
	    response.setErrors(List.of("Invalid Json Body, Please Check..","Please provide values for all fields. Empty values are not allowed.","Comma and Braces Correctly"));

	    return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleIAE(Exception e, HttpServletRequest req) {

		logger.error("Global Exception occurred at {} : {}", req.getRequestURI(), e.getMessage(), e);
		ErrorDetails details = new ErrorDetails();
		details.setTime(LocalDateTime.now());
		details.setMsg(e.getMessage());
		details.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		details.setPath(req.getRequestURI());
		return new ResponseEntity<ErrorDetails>(details, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
