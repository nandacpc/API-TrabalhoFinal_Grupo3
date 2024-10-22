package org.serratec.shablau.config;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }
	 @ExceptionHandler(ConstraintViolationException.class)
	 public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
	        Map<String, Object> erroResposta = new HashMap<>();
	        erroResposta.put("timestamp", LocalDateTime.now());
	        erroResposta.put("status", HttpStatus.BAD_REQUEST.value());
	        erroResposta.put("error", "Validation Error");
	        erroResposta.put("message", ex.getConstraintViolations().iterator().next().getMessage());
	        erroResposta.put("path", request.getRequestURI()); 
	        return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	    }
	 @ExceptionHandler(DuplicateKeyException.class)
	    public ResponseEntity<Map<String, Object>> handleDuplicateKeyException(DuplicateKeyException ex, HttpServletRequest request) {
	        Map<String, Object> erroResposta = new HashMap<>();
	        erroResposta.put("timestamp", LocalDateTime.now());
	        erroResposta.put("status", HttpStatus.BAD_REQUEST.value());
	        erroResposta.put("error", "Duplicate Key Error");
	        erroResposta.put("message", "CPF já cadastrado");
	        erroResposta.put("path", request.getRequestURI());
	        return new ResponseEntity<>(erroResposta, HttpStatus.BAD_REQUEST);
	    }
}
