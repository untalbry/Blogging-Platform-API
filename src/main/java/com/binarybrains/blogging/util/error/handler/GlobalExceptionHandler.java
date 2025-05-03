package com.binarybrains.blogging.util.error.handler;

import com.binarybrains.blogging.util.error.BlogException;
import com.binarybrains.blogging.util.error.ErrorInfo;
import com.binarybrains.blogging.util.error.ErrorResponse;
import com.binarybrains.blogging.util.error.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Ocurrio un error inesperado: " + ex.getLocalizedMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BlogException.class)
    public ResponseEntity<ErrorInfo> handleBlogException(BlogException  ex){
        ErrorInfo error = ex.getErrorInfo();
        error.setType(ErrorType.REQUEST);
        error.setRuta(Thread.currentThread().getStackTrace()[2].getClassName());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if("NOT_FOUND".equals(error.getCode())){
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity.status(status).body(error);
    }

}
