package com.autentia.tutorial.idempotency.common.api;

import com.autentia.tutorial.idempotency.common.exception.InvalidDataException;
import com.autentia.tutorial.idempotency.common.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author marlandy
 */
@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(value = InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidData(InvalidDataException ide) {
        final ErrorResponse error = new ErrorResponse(ide.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Void> handleInvalidData(NotFoundException ide) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleInvalidData(HttpMediaTypeNotSupportedException ide) {
        final ErrorResponse error = new ErrorResponse("Unsupported media type. Please verify your json or xml input data format and your Content-Type and Accept headers");
        return new ResponseEntity<>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

}
