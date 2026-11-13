package com.ecom_application.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecom_application.dto.ResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(exception = UserNotFoundException.class)
	public ResponseEntity<ResponseDTO> userNotFountException(Exception ex) {
		ResponseDTO responseDTO = new ResponseDTO(ex.getMessage(), new Date(), false);
		return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(exception = ResourceNotFoundException.class)
	public ResponseEntity<ResponseDTO> resourceNotFoundException(Exception ex) {
		ResponseDTO responseDTO = new ResponseDTO(ex.getMessage(), new Date(), false);
		return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);

	}

}
