package com.challenge.interview.calendar.api;

import com.challenge.interview.calendar.api.error.ErrorResponse;
import com.challenge.interview.calendar.model.errors.HttpException;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@ResponseBody
public class MainExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @NonNull
    public ErrorResponse handleMessageNotReadable(@NonNull HttpMessageNotReadableException ex) {
        ErrorResponse responseBody = new ErrorResponse("invalid request");
        if (ex.getCause() instanceof JsonMappingException) {
            JsonMappingException cause = (JsonMappingException) ex.getCause();
            String field = cause.getPath().stream().map(c -> c.getFieldName()).collect(Collectors.joining("."));
            responseBody = new ErrorResponse("invalid data " + field);
        }
        return responseBody;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @NonNull
    public ErrorResponse handleArgumentTypeMismatch(@NonNull MethodArgumentTypeMismatchException ex) {
        return new ErrorResponse("Invalid data on field " + ex.getName());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @NonNull
    public ErrorResponse handleMissingArguments(@NonNull MissingServletRequestParameterException ex) {
        return new ErrorResponse("Missing mandatory attribute " + ex.getParameterName());
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @NonNull
    public ErrorResponse handleNotAcceptable(@NonNull HttpMediaTypeNotAcceptableException e) {
        return new ErrorResponse("Invalid accept request header supplied");
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @NonNull
    public ErrorResponse handleUnsupportedMediaType(@NonNull HttpMediaTypeNotSupportedException e) {
        return new ErrorResponse("Invalid content type header supplied");
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @NonNull
    public ErrorResponse handleUnsupportedMethod(@NonNull HttpRequestMethodNotSupportedException e) {
        return new ErrorResponse("Method not allowed");
    }

    @ExceptionHandler({HttpException.class})
    @NonNull
    public ErrorResponse handleHttpResponseException(@NonNull HttpServletResponse response, @NonNull HttpException ex) {
        response.setStatus(ex.getCode().value());
        if (ex.getCode().value() >= 500) {
            this.log.error("http response exception: ", (Throwable)ex);
        }

        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @NonNull
    public ErrorResponse handleInternalError(@NonNull Exception ex) {
        this.log.error("Unexpected generic error happened: ", ex);
        return new ErrorResponse("Internal server error");
    }
}
