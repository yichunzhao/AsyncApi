package com.ynz.asyncapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class DemoHandlerMethodArgument {

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

}
