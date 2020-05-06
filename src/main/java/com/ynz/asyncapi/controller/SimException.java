package com.ynz.asyncapi.controller;

import com.ynz.asyncapi.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ex")
public class SimException {

    //simulating an NotFoundException.
    @GetMapping("/find")
    public ResponseEntity<Void> findButNotFound() {
        throw new NotFoundException("it is not found.");
    }
}
