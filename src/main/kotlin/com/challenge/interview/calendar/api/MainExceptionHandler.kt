package com.challenge.interview.calendar.api

import com.challenge.interview.calendar.api.error.ErrorResponse
import com.challenge.interview.calendar.model.errors.HttpException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
@ResponseBody
class MainExceptionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass.name)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleMessageNotReadable(ex: HttpMessageNotReadableException): ErrorResponse {
        var responseBody = ErrorResponse("Invalid request")
        var cause = ex.cause
        when (cause){
            is MissingKotlinParameterException -> {
                val fields = setOf(cause.path.joinToString(separator = ".") { c -> c.fieldName })
                responseBody = ErrorResponse("Missing mandatory fields $fields")
            }
            is InvalidFormatException -> {
                val field = cause.value.toString()
                responseBody = ErrorResponse("Invalid data $field")
            }
            is JsonMappingException -> {
                val field = cause.message!!.split("\"")[1]

                responseBody = ErrorResponse("Invalid data $field")
            }
        }
        return responseBody
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleArgumentTypeMismatch(ex: MethodArgumentTypeMismatchException): ErrorResponse {
        return ErrorResponse("Invalid data on field ${ex.name}")
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingArguments(ex: MissingServletRequestParameterException): ErrorResponse {
        return ErrorResponse("Missing mandatory attribute ${ex.parameterName}")
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(HttpMediaTypeNotAcceptableException::class)
    fun handleNotAcceptable(e: HttpMediaTypeNotAcceptableException): ErrorResponse {
        return ErrorResponse("Invalid accept request header supplied")
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleUnsupportedMediaType(e: HttpMediaTypeNotSupportedException): ErrorResponse {
        return  ErrorResponse("Invalid content type header supplied")
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleUnsupportedMethod(e: HttpRequestMethodNotSupportedException): ErrorResponse {
        return ErrorResponse("Method not allowed")
    }

    @ExceptionHandler(HttpException::class)
    fun handleHttpResponseException(response: HttpServletResponse, ex: HttpException): ErrorResponse? {
        response.status = ex.code.value()
        if (ex.code.value() >= 500) {
            log.error("http response exception: ", ex)
        }
        return ErrorResponse(ex.message!!);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleInternalError(ex: Exception): ErrorResponse {
        val responseBody = ErrorResponse("Internal server error")
        log.error("Unexpected generic error happened: ", ex)
        return responseBody
    }
}