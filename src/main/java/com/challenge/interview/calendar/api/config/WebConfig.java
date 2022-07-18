package com.challenge.interview.calendar.api.config;

import com.challenge.interview.calendar.model.RoleStringToEnumConverter;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    public void addFormatters(@NonNull FormatterRegistry registry) {
        registry.addConverter((Converter)(new RoleStringToEnumConverter()));
    }
}
