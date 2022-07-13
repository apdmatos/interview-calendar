package com.challenge.interview.calendar.model.errors

import org.springframework.http.HttpStatus

class BadRequestException(message: String) : HttpException(HttpStatus.BAD_REQUEST, message);