package com.enuri.nielsen.admin.config;

import com.enuri.nielsen.admin.controller.shoppingDiary.converter.LocalDateConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateConverter.LocalDateToStringConverter());
        registry.addConverter(new LocalDateConverter.StringToLocalDateConverter());
    }
}
