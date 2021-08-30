package com.enuri.nielsen.admin.controller.shoppingDiary.converter;

import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Column;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter {

    public static class StringToLocalDateConverter implements Converter<String, LocalDate> {
        @Override
        public LocalDate convert(String source) {
            if(source == null || source.equals("")) return null;
            //System.out.println("String -> LocalDate 동작");
            return LocalDate.parse(source);
        }
    }

    public static class LocalDateToStringConverter implements Converter<LocalDate, String> {
        @Override
        public String convert(LocalDate source) {
            if(source == null) return null;
            //System.out.println("LocalDate -> String 동작");
            return source.toString();
        }
    }
}
