package com.challenge.interview.calendar.model

import java.util.*
import org.springframework.core.convert.converter.Converter;

enum class Role(val role: String) {
    CANDIDATE("candidate"),
    INTERVIEWER("interviewer");

    override fun toString(): String {
        return this.role;
    }
}

class StringToEnumConverter : Converter<String?, Role?> {
    override fun convert(source: String): Role {
        return Role.valueOf(source.uppercase(Locale.getDefault()))
    }
}