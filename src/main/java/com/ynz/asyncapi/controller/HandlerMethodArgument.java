package com.ynz.asyncapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/args")
public class HandlerMethodArgument {

    @GetMapping("/requestHeader")
    public ResponseEntity<Map<String, String>> injectRequestHeader(@RequestHeader("content-type") String contentType) {
        log.info("request handler in injectRequestHeader");
        Map<String, String> result = new HashMap<>();
        result.put("Accept-Encoding", contentType);

        ResponseEntity<Map<String, String>> response = new ResponseEntity(result, HttpStatus.FOUND);
        return response;
    }

    @GetMapping("/cookie")
    public ResponseEntity<Map<String, String>> injectCookieValue(@CookieValue("JSESSIONID") String cookie) {
        log.info("request handler in injectCookieValue");

        Map<String, String> result = new HashMap<>();
        result.put("JSESSIONID", cookie);

        ResponseEntity<Map<String, String>> response = new ResponseEntity(result, HttpStatus.FOUND);
        return response;
    }

    @GetMapping("/callbackUri")
    public ResponseEntity<URI> injectUriComponent(UriComponentsBuilder builder) {
        log.info("demo building uri using UriComponentBuilder");
        URI uri = builder.path("/expectedBath").buildAndExpand("uriBuilder").toUri();
        log.info("build URI: " + uri);
        return new ResponseEntity(uri, HttpStatus.CREATED);
    }

    @GetMapping(value = "/callbackUri", params = "t1")
    public ResponseEntity<URI> injectUriComponentT1(UriComponentsBuilder builder) {
        log.info("demo building uri using UriComponentBuilder");
        URI uri = builder.path("/expectedBath").buildAndExpand("uriBuilder").toUri();

        log.info("build URI: " + uri);
        return new ResponseEntity(uri, HttpStatus.CREATED);
    }


}
