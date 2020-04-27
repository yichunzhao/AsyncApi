package com.ynz.asyncapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchResult {

    private String name;

    private LocalDateTime birthDate;

}
