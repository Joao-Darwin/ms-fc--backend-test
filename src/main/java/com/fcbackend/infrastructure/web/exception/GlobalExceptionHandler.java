package com.fcbackend.infrastructure.web.exception;

import com.fcbackend.domain.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

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
}
