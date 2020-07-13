package eu.acme.demo.error;

import eu.acme.demo.exception.DemoApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Optional;

@ControllerAdvice
@Slf4j
public class ExceptionHandling {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ErrorResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ErrorResponse
                .builder()
                .errorCode(ValidationMessageKeys.VALIDATION_ERROR)
                .errorMessage(ex.getCause().getMessage())
                .build();
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        Optional<DemoApplicationException> demoApplicationException = getRootException(ex, DemoApplicationException.class);
        return demoApplicationException
                .map(exception -> ResponseEntity
                        .status(exception.getStatus().getStatusCode())
                        .body(ErrorResponse
                                .builder()
                                .errorCode(exception.getMessageKey())
                                .errorMessage(exception.getDetail())
                                .build()))
                .orElseGet(() -> ResponseEntity.status(500).body(handleAllExceptions(ex)));
    }

    private ErrorResponse handleAllExceptions(Exception ex) {
        log.error("System error while processing the request", ex);
        return ErrorResponse.builder()
                .errorCode(ValidationMessageKeys.SYSTEM_ERROR)
                .errorMessage(ValidationMessageKeys.SYSTEM_ERROR_MESSAGE)
                .build();
    }

    private <T> Optional<T> getRootException(Throwable exception, Class<T> rootException) {
        Throwable cause = exception;
        while (cause != null) {
            if (rootException.isAssignableFrom(cause.getClass())) {
                return Optional.of(rootException.cast(cause));
            }
            cause = cause.getCause();
        }
        return Optional.empty();
    }

}
