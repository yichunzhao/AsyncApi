package com.ynz.asyncapi.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SearchResult {

    private String name;

    private LocalDate birthDate;

}
