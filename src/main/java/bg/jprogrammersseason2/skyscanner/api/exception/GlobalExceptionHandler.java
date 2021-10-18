package bg.jprogrammersseason2.skyscanner.api.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler
{
  @ExceptionHandler(JsonProcessingException.class)
  public ResponseEntity<Object> handleJsonProcessingException(JsonProcessingException ex)
  {
    Map<String, Object> errorBody = new HashMap<>();
    errorBody.put("errors", "Cannot convert the response.");
    errorBody.put("timestamp", LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
  }

  @ExceptionHandler(InvalidParamsException.class)
  public ResponseEntity<Object> handleInvalidParamsException(InvalidParamsException ex)
  {
    Map<String, Object> errorBody = new HashMap<>();
    if (ex.getErrors() != null)
    errorBody.put("errors", ex.getErrors());

    if(ex.getMessage() != null)
    errorBody.put("message", ex.getMessage());
    errorBody.put("timestamp", LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
  }

  @ExceptionHandler(TypeMismatchException.class)
  public ResponseEntity<Object> handleTypeMismatchException(TypeMismatchException ex)
  {
    Map<String, Object> errorBody = new HashMap<>();
    System.out.println(ex.getMessage());
    ex.printStackTrace();

    if(ex.getMessage() != null)
      errorBody.put("message", ex.getMessage());
    errorBody.put("timestamp", LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
  }

  @ExceptionHandler({ ConstraintViolationException.class})
  public ResponseEntity<Map<String, Object>> handleConstraintExceptions(ConstraintViolationException exception)
  {
    Map<String, Object> exceptionBody = new HashMap<>();
    exceptionBody.put("timestamp", LocalDateTime.now());
    List<String> errors = exception.getConstraintViolations()
        .stream().map(ConstraintViolation::getMessageTemplate).collect(Collectors.toList());
      exceptionBody.put("errors", errors);

    return ResponseEntity.badRequest().body(exceptionBody);
  }
}
