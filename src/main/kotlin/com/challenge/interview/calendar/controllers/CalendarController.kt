package com.challenge.interview.calendar.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CalendarController {
    @GetMapping("/")
    fun hello(): String {
        return "hello from controller"
    }
}