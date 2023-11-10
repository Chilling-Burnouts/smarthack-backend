package com.chillingburnouts.smarthack.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "https://frontend-application-production-4490.up.railway.app")
@RestController
public class TestController {
    @Value("${app.environment}")
    private String profile;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello, Smarthack! We're on " + profile);
    }
}
