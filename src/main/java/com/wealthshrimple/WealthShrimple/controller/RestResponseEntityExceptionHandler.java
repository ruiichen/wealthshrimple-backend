package com.wealthshrimple.WealthShrimple.controller;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.wealthshrimple.WealthShrimple.dto.ResponseBase;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {
	
	@ExceptionHandler(value ={ ConstraintViolationException.class})
	public ResponseEntity<ResponseBase> handleAccessDeniedException (Exception ex, WebRequest request) {
		ResponseBase resp = new ResponseBase();
		resp.setCode("400");
		resp.setMessage("rough time buddy, " + ex.getMessage() + " just screwed you over");
		return new ResponseEntity<ResponseBase> (resp, HttpStatus.OK);
	}
	

	@ExceptionHandler(value ={ HttpMessageNotReadableException.class})
	public ResponseEntity<ResponseBase> handleNotReadableException (Exception ex, WebRequest request) {
		ResponseBase resp = new ResponseBase();
		resp.setCode("400");
		resp.setMessage("rough time buddy, " + ex.getMessage() + " just screwed you over");
		return new ResponseEntity<ResponseBase> (resp, HttpStatus.OK);
	}

}
