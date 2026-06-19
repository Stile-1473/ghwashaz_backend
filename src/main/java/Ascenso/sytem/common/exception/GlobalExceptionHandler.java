package Ascenso.sytem.common.exception;


import Ascenso.sytem.common.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(

            ResourceNotFoundException ex,

            HttpServletRequest request

    ) {

        return buildError(

                HttpStatus.NOT_FOUND,

                ex.getMessage(),

                request,

                List.of()

        );

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(

            BadRequestException ex,

            HttpServletRequest request

    ) {

        return buildError(

                HttpStatus.BAD_REQUEST,

                ex.getMessage(),

                request,

                List.of()

        );

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validation(

            MethodArgumentNotValidException ex,

            HttpServletRequest request

    ) {

        List<String> errors = ex.getBindingResult()

                .getFieldErrors()

                .stream()

                .map(FieldError::getDefaultMessage)

                .toList();

        return buildError(

                HttpStatus.BAD_REQUEST,

                "Validation failed",

                request,

                errors

        );

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolation(

            ConstraintViolationException ex,

            HttpServletRequest request

    ) {

        List<String> errors = ex.getConstraintViolations()

                .stream()

                .map(v -> v.getMessage())

                .toList();

        return buildError(

                HttpStatus.BAD_REQUEST,

                "Validation failed",

                request,

                errors

        );

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(

            Exception ex,

            HttpServletRequest request

    ) {

        return buildError(

                HttpStatus.INTERNAL_SERVER_ERROR,

                ex.getMessage(),

                request,

                List.of()

        );

    }

    private ResponseEntity<ErrorResponse> buildError(

            HttpStatus status,

            String message,

            HttpServletRequest request,

            List<String> errors

    ) {

        return ResponseEntity.status(status)

                .body(

                        ErrorResponse.builder()

                                .success(false)

                                .status(status.value())

                                .error(status.getReasonPhrase())

                                .message(message)

                                .path(request.getRequestURI())

                                .errors(errors)

                                .build()

                );

    }

}