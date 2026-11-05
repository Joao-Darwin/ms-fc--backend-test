package com.fcbackend.infrastructure.web.exception;

import com.fcbackend.application.exception.TweetNotFoundException;
import com.fcbackend.domain.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        var problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "One or more fields are invalid."
        );

        problem.setType(URI.create(
                "https://api.exemplo.com/problems/validation-error"
        ));

        problem.setTitle("Invalid data");
        problem.setInstance(URI.create(request.getRequestURI()));

        List<Map<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage()
                ))
                .toList();

        problem.setProperty("traceId", MDC.get("traceId"));
        problem.setProperty("errors", errors);

        return problem;
    }

    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleActivationConflict(
            BusinessException ex,
            HttpServletRequest request) {

        var problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );

        problem.setType(URI.create(
                "https://api.exemplo.com/problems/activation-conflict"
        ));

        problem.setTitle("Business rule error");
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("traceId", MDC.get("traceId"));

        return problem;
    }

    @ExceptionHandler(TweetNotFoundException.class)
    public ProblemDetail handleTweetNotFound(
            TweetNotFoundException ex,
            HttpServletRequest request) {

        var problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );

        problem.setType(URI.create(
                "https://api.exemplo.com/problems/tweet-not-found"
        ));

        problem.setTitle("Tweet not found");
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("traceId", MDC.get("traceId"));

        return problem;
    }
}
