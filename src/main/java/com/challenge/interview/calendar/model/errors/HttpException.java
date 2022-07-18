package com.challenge.interview.calendar.model.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class HttpException extends Exception {
    private HttpStatus code;
    private String message;
}
