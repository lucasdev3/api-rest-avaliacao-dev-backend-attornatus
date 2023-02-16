package br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.exceptions;

import br.com.lucasdev3.attornatus.apirestavaliacaodevbackend.exceptions.exceptionsAnnotations.RestControllerAdvice;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, List<String>>> handleValidationErrors(
      MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult().getFieldErrors()
        .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
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
