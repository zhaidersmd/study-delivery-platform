package com.labi.studyjobservice.demo;

import org.junit.jupiter.api.*;

@DisplayName("Test Class Demo")
class CalculatorTest {

    @BeforeAll()
    static void beforeAll() {
        System.out.println("1. BeforeAll");
    }

    @BeforeEach
    @Order(2)
    void beforeEach() {
        System.out.println("2. BeforeEach");
    }

    @BeforeEach
    @Order(1)
    void beforeEach2() {
        System.out.println("2. BeforeEach2");
    }

    @Test
    void testAdd() {
        System.out.println("3. Test Add");
    }

    @Test
    void testSubtract() {
        System.out.println("3. Test Subtract");
    }

    @AfterEach
    void afterEach() {
        System.out.println("4. AfterEach");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("5. AfterAll");
    }
}
