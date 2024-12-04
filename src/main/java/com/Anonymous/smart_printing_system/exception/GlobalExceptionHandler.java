package com.Anonymous.smart_printing_system.exception;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Global Exception Handler for all Controllers
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    @Value("${com.sps.serverName}")
    private String serverName;


    private String loggerName;


    @PostConstruct
    private void createLoggerName()
    {
        String handlerName = "GlobalExceptionHandler";
        loggerName = serverName + ":" + handlerName;
    }


    @ExceptionHandler(PaperNotEnoughException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handlePaperNotEnoughException(PaperNotEnoughException paperNotEnoughException)
    {
        return ApiError
                .builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(paperNotEnoughException.getMessage())
                .error("PAPER_NOT_ENOUGH")
                .build();
    }


    /**
     * Handle the UserAlreadyExistsException.
     *
     * @param  userAlreadyExistsException the exception.
     * @return ResponseEntity with status CONFLICT.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException)
    {
        Map<String, String> response = new HashMap<>();
        response.put(loggerName, userAlreadyExistsException.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCustomerIdNotFoundException(UserNotFoundException customerNotFoundException)
    {
        Map<String, String> response = new HashMap<>();
        response.put(loggerName, customerNotFoundException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(UsernameNotFoundException usernameNotFoundException)
    {
        Map<String, String> response = new HashMap<>();
        response.put(loggerName, usernameNotFoundException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException badCredentialsException)
    {
        Map<String, String> response = new HashMap<>();
        response.put(loggerName, badCredentialsException.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResourceFoundException(NoResourceFoundException noResourceFoundException)
    {
        Map<String, String> response = new HashMap<>();
        response.put(loggerName, noResourceFoundException.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }


    /**
     * Catch the exception thrown by the
     * annotation PreAuthorize in methods
     * @param authorizationDeniedException thrown
     * when authenticated user can't access
     * the resources which required higher Role
     * @return ResponseEntity with 403 Forbidden
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAuthorizationDeniedException(AuthorizationDeniedException authorizationDeniedException)
    {
        Map<String, String> response = new HashMap<>();
        response.put(loggerName, authorizationDeniedException.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException) 
    {
        List<String> errors = methodArgumentNotValidException
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> 
                {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    return fieldName + ": " + message;
                })
                .collect(Collectors.toList());
        
        errors.add(loggerName + ": " + methodArgumentNotValidException.getMessage());
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    /**
     *
     * @param exception Exception which is caught all
     * @return ResponseEntity with INTERNAL_SERVER_ERROR code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception exception)
    {
        Map<String, String> response = new HashMap<>();
        response.put(loggerName +":CatchAll", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
