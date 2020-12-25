package com.example.app.web.mvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.app.domain.ValidationDto;

@ControllerAdvice
public class RestResponseEntityExceptionHandler 
  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IncorrectObjectVersionException.class)
    protected ResponseEntity<Object> handleIncorrectObjectVersion(
      RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "The object can not be updated with this request";
        return handleExceptionInternal(ex, bodyOfResponse, 
          new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	BindingResult result = ex.getBindingResult();
        
        ValidationDto dto = new ValidationDto();
        dto.setErrors(result.getAllErrors());;
    		return new ResponseEntity<>(dto, headers, status);
    }
}