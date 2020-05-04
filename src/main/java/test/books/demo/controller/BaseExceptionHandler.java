package test.books.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public final ErrorDetails handleAllExceptions(Throwable ex, WebRequest request) {
        return new ErrorDetails("error", ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getMessage();
        message = message.substring(message.indexOf("${{") + 3);
        message = message.substring(0, message.indexOf("}}"));

        ErrorDetails errorDetails = new ErrorDetails("error", message);

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @Getter
    @AllArgsConstructor
    private static class ErrorDetails {
        private String status;
        private String message;
    }
}
