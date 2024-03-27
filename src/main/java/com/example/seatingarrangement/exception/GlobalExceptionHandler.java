package com.example.seatingarrangement.exception;

import com.example.seatingarrangement.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlreadyLogOutException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ResponseDto> handleAlreadyLogOutException(AlreadyLogOutException exception) {
        ResponseDto response = new ResponseDto();
        response.setHttpStatus(HttpStatus.CONFLICT);
        response.setMessage(exception.getMessage());
        response.setData(null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ResponseDto response = new ResponseDto();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setMessage(exception.getMessage());
        response.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseDto> handleUserNotFoundException(UserNotFoundException exception) {
        ResponseDto response = new ResponseDto();
        response.setHttpStatus(HttpStatus.NOT_FOUND);
        response.setMessage(exception.getMessage());
        response.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ResponseDto> handleIncorrectPasswordException(IncorrectPasswordException exception) {
        ResponseDto response = new ResponseDto();
        response.setHttpStatus(HttpStatus.UNAUTHORIZED);
        response.setMessage(exception.getMessage());
        response.setData(null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }


    @ExceptionHandler(DuplicateRegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDto> handleDuplicateRegistrationException(DuplicateRegistrationException exception) {
        ResponseDto response = new ResponseDto();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setMessage(exception.getMessage());
        response.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ResponseDto response = new ResponseDto();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        });
        response.setMessage(errorMessage.toString());
        response.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
