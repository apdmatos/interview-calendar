package com.challenge.interview.calendar.model.errors

import org.springframework.http.HttpStatus

abstract class HttpException(val code: HttpStatus, message: String?) : java.lang.Exception(message)