package com.podzilla.warehouse.e2e;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * Base class for E2E tests that interact with a running instance of the application.
 * This assumes the application is running on localhost:8080 with context path /api.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class E2eTestBase {

    protected static final String BASE_URL = "http://localhost:8080/api";
    protected RestTemplate restTemplate;

    @BeforeAll
    public void setup() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        restTemplate = builder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
        
        // Verify the application is running
        try {
            restTemplate.getForEntity("http://localhost:8080//actuator/health", String.class);
        } catch (Exception e) {
            System.err.println("WARNING: The application does not appear to be running at " + BASE_URL);
            System.err.println("Make sure to start the application using Docker before running these tests.");
        }
    }
}