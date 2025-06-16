package baji.springframework.spring6restmvc.Controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {

    private final View error;

    public CustomErrorController(View error) {
        this.error = error;
    }

    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException exception) {
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();
        List errors = null;
        if (exception.getCause().getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException ve = (ConstraintViolationException) exception.getCause().getCause();

            errors = ve.getConstraintViolations().stream().
                    map(constraintViolation -> {
                        Map<String, String> errMap = new HashMap<>();
                        errMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
                        return errMap;
                    }).collect(Collectors.toList());
        }

        return responseEntity.body(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception) {

        List erroList = exception.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());

//        return ResponseEntity.badRequest().body(exception.getBindingResult().getFieldErrors());
        return ResponseEntity.badRequest().body(erroList);
    }

}
