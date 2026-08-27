package com.tcs.exception;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	
	 @ExceptionHandler(NoSuchElementException.class)
	    public ResponseEntity<Map<String, String>> handleNotFound(NoSuchElementException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(Map.of("status", "NOT_FOUND", "message", e.getMessage()));
	    }
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handelBadRequest(IllegalArgumentException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Map.of("status","Failed","message",e.getMessage()));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handelGeneric(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("status", "FAILED", "message", "Unexpected error: " + e.getMessage()));
	}

	
}
