package com.project.tickets.controllers;

import com.project.tickets.domain.dtos.ErrorDto;
import com.project.tickets.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(TicketSoldOutException.class)
	public ResponseEntity<ErrorDto> handleTicketSoldOutException(TicketSoldOutException ex) {
		log.error("Caught TicketSoldOutException", ex);
		ErrorDto error = new ErrorDto();
		error.setError("Tickets are sold out Exception");
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(QrCodeNotFoundException.class)
	public ResponseEntity<ErrorDto> handleQRCodeNotFoundException(QrCodeNotFoundException ex) {
		log.error("Caught QrCodeNotFoundException", ex);
		ErrorDto error = new ErrorDto();
		error.setError(" QR Code Not Found ");
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(QrCodeGenerationException.class)
	public ResponseEntity<ErrorDto> handleQRCodeGenerateException(QrCodeGenerationException ex) {
		log.error("Caught QrCodeGenerationException", ex);
		ErrorDto error = new ErrorDto();
		error.setError("Unable to Generate QR Code");
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(EventUpdateException.class)
	public ResponseEntity<ErrorDto> handleEventUpdateException(EventUpdateException ex) {
		log.error("Caught EventUpdateException", ex);
		ErrorDto error = new ErrorDto();
		error.setError("Unable to update");
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EventNotFoundException.class)
	public ResponseEntity<ErrorDto> handleEventNotFoundException(EventNotFoundException ex) {
		log.error("Caught EventNotFoundException", ex);
		ErrorDto error = new ErrorDto();
		error.setError("Event not found");
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TicketTypeNotFoundException.class)
	public ResponseEntity<ErrorDto> handleTicketTypeNotFoundException(TicketTypeNotFoundException ex) {
		log.error("Caught TicketTypeNotFoundException", ex);
		ErrorDto error = new ErrorDto();
		error.setError("Ticket not found");
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
		log.error("Caught UserNotFoundException", ex);
		ErrorDto error = new ErrorDto();
		error.setError("User not found");
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

		log.error("Caught MethodArgumentNotValidException", ex);
		ErrorDto error = new ErrorDto();

		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		String validationError = fieldErrors.stream()
			.findFirst()
			.map(filedError -> filedError.getDefaultMessage())
			.orElse("Validation error");

		error.setError(validationError);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException ex) {

		log.error("Caught ConstraintViolationException", ex);
		String validationError = ex.getConstraintViolations()
			.stream()
			.findFirst()
			.map(violation -> violation.getPropertyPath() + " : " + violation.getMessage())
			.orElse("Validation error");

		ErrorDto error = new ErrorDto();
		error.setError(validationError);

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDto> handleException(Exception ex) {

		log.error("Caught Exception", ex);
		ErrorDto error = new ErrorDto();
		error.setError("Internal Server Error");
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
