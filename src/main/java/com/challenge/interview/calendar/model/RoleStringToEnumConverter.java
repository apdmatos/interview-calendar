package com.challenge.interview.calendar.model;

import org.springframework.core.convert.converter.Converter;

import java.util.Locale;

public class RoleStringToEnumConverter implements Converter<String, Role> {
    @Override
    public Role convert(String source) {
        return Role.valueOf(source.toUpperCase(Locale.getDefault()));
    }
}
