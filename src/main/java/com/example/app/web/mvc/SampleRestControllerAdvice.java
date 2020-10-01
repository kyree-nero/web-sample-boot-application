package com.example.app.web.mvc;


import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.app.domain.ValidationDto;

@ControllerAdvice
public class SampleRestControllerAdvice {
	 	
		
		
		@ExceptionHandler(MethodArgumentNotValidException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ResponseBody
	    public ValidationDto processValidationError(MethodArgumentNotValidException ex) {
	        BindingResult result = ex.getBindingResult();
	        
	        ValidationDto dto = new ValidationDto();
	        dto.setErrors(result.getAllErrors());;
	        return dto;
	    }
	 	
	 	@ResponseStatus(HttpStatus.NOT_FOUND)  // 404
	    @ExceptionHandler(Throwable.class)
	    public void handleOthers(Throwable throwable) {
	 		throwable.printStackTrace();
	    }
	 	
	 	
}
