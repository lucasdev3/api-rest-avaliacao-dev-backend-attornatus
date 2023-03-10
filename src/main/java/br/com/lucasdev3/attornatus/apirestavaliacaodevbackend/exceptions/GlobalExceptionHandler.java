package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.exceptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.exceptions.exceptionsAnnotations.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, List<String>>> handleValidationErrors(
      MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult().getFieldErrors()
        .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
    LOGGER.debug(errors.toString());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, List<String>>> handleValidationErrors(
      ConstraintViolationException ex) {
    List<String> errors = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
        .collect(Collectors.toList());
    LOGGER.debug(errors.toString());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    LOGGER.debug(errors.toString());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RuntimeException.class)
  public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(
      RuntimeException ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    LOGGER.debug(errors.toString());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private Map<String, List<String>> getErrorsMap(List<String> errors) {
    LinkedHashMap<String, List<String>> errorResponse = new LinkedHashMap<>();
    List<String> infos = new ArrayList<>();
    infos.add(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS").format(new Date()));
    errorResponse.put("infos", infos);
    errorResponse.put("error", errors);
    return errorResponse;
  }

}
