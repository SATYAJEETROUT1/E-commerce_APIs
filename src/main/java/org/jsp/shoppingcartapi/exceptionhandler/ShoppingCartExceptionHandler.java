package org.jsp.shoppingcartapi.exceptionhandler;

import org.jsp.shoppingcartapi.dto.ResponseStructure;

import org.jsp.shoppingcartapi.exception.IdNotFoundException;
import org.jsp.shoppingcartapi.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ShoppingCartExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleIdNotFoundException(IdNotFoundException e) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setData("User Not found");
		structure.setMessage(e.getMessage());
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ResponseStructure<String>> hindleInvalididCredentialsException(
			InvalidCredentialsException e) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setData("Inavalid Email or Password");
		structure.setMessage(e.getMessage());
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
	}

}
