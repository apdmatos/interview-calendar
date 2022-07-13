package com.challenge.interview.calendar.model.errors

import org.springframework.http.HttpStatus

class NotFoundException(message: String?) : HttpException(HttpStatus.NOT_FOUND, message);