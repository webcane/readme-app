package cane.brothers.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mniedre
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            DataIntegrityViolationException ex, WebRequest request) {

        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError body = null;

        if (ex != null) {
            if (ex.getCause() instanceof ConstraintViolationException) {

                List<String> errors = new ArrayList<>();
                ConstraintViolationException cEx = (ConstraintViolationException) ex.getCause();
                for (ConstraintViolation<?> violation : cEx.getConstraintViolations()) {
                    errors.add(violation.getRootBeanClass().getName() + " " +
                            violation.getPropertyPath() + ": " + violation.getMessage());
                }
                body = new ApiError(status, cEx.getLocalizedMessage(), errors);
            } else {
                body = new ApiError(status, ex.getLocalizedMessage(), "Error due to unique index or primary key violation");
            }

        }

        return this.handleExceptionInternal(ex, body, headers, status, request);
    }
}
