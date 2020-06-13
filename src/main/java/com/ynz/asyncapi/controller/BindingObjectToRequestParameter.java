package com.ynz.asyncapi.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * Demo how to binding individual objects to request parameters.
 */
@RestController
@RequestMapping("/binding")
public class BindingObjectToRequestParameter {

    @GetMapping("/findByDate/{date}")
    public ResponseEntity<LocalDate> findByDate(@PathVariable("date") LocalDate date) {
        return ResponseEntity.status(HttpStatus.FOUND).body(date.plusDays(1L));
    }
}
