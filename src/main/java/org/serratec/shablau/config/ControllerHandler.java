package org.serratec.shablau.config;

import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerHandler extends ResponseEntityExceptionHandler{
	
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
	        HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<String> erros= new ArrayList<>();
		for(FieldError error: ex.getBindingResult().getFieldErrors()) {
			erros.add(error.getField() + ": " + error.getDefaultMessage());
		}
		ErroResposta erroResposta = new ErroResposta(status.value(),
				"Existem Campos Inválidos, Confira o preenchimento", LocalDateTime.now(), erros);
		return super.handleMethodArgumentNotValid(ex, null, status, request);
	}
}
