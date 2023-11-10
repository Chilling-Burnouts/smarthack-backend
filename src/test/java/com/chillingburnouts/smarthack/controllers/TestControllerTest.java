package com.chillingburnouts.smarthack.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.chillingburnouts.smarthack.controllers.TestController;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TestControllerTest {
    TestController testController = new TestController();

    @Test
    void test1() {
        ResponseEntity<String> actualResult = testController.test();
        assertTrue(Objects.requireNonNull(actualResult.getBody()).contains("Hello, Smarthack!"));
    }
}