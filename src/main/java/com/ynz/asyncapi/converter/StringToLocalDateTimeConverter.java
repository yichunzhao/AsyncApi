package com.ynz.asyncapi.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class StringToLocalDateTimeConverter implements Converter<String,LocalDate>{

    @Override
    public LocalDate convert(String s) {

        return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
