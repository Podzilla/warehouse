package com.podzilla.warehouse.e2e;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Utility class for E2E tests with helper methods for common operations.
 */
public class E2eTestUtils {

    /**
     * Performs a GET request to the specified endpoint.
     *
     * @param restTemplate The RestTemplate to use
     * @param baseUrl The base URL of the API
     * @param endpoint The endpoint to call (without the base URL)
     * @param responseType The expected response type
     * @return The response entity
     */
    public static <T> ResponseEntity<T> get(RestTemplate restTemplate, String baseUrl, String endpoint, Class<T> responseType) {
        String url = baseUrl + endpoint;
        return restTemplate.getForEntity(url, responseType);
    }

    /**
     * Performs a GET request to the specified endpoint with a parameterized type reference.
     *
     * @param restTemplate The RestTemplate to use
     * @param baseUrl The base URL of the API
     * @param endpoint The endpoint to call (without the base URL)
     * @param typeReference The parameterized type reference for the response
     * @return The response entity
     */
    public static <T> ResponseEntity<T> get(RestTemplate restTemplate, String baseUrl, String endpoint, ParameterizedTypeReference<T> typeReference) {
        String url = baseUrl + endpoint;
        return restTemplate.exchange(url, HttpMethod.GET, null, typeReference);
    }

    /**
     * Performs a POST request to the specified endpoint with the given request body.
     *
     * @param restTemplate The RestTemplate to use
     * @param baseUrl The base URL of the API
     * @param endpoint The endpoint to call (without the base URL)
     * @param requestBody The request body
     * @param responseType The expected response type
     * @return The response entity
     */
    public static <T> ResponseEntity<T> post(RestTemplate restTemplate, String baseUrl, String endpoint, Object requestBody, Class<T> responseType) {
        String url = baseUrl + endpoint;
        return restTemplate.postForEntity(url, requestBody, responseType);
    }

    /**
     * Performs a POST request to the specified endpoint with the given request body and a parameterized type reference.
     *
     * @param restTemplate The RestTemplate to use
     * @param baseUrl The base URL of the API
     * @param endpoint The endpoint to call (without the base URL)
     * @param requestBody The request body
     * @param typeReference The parameterized type reference for the response
     * @return The response entity
     */
    public static <T> ResponseEntity<T> post(RestTemplate restTemplate, String baseUrl, String endpoint, Object requestBody, ParameterizedTypeReference<T> typeReference) {
        String url = baseUrl + endpoint;
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, typeReference);
    }

    /**
     * Performs a PUT request to the specified endpoint with the given request body.
     *
     * @param restTemplate The RestTemplate to use
     * @param baseUrl The base URL of the API
     * @param endpoint The endpoint to call (without the base URL)
     * @param requestBody The request body
     * @param responseType The expected response type
     * @return The response entity
     */
    public static <T> ResponseEntity<T> put(RestTemplate restTemplate, String baseUrl, String endpoint, Object requestBody, Class<T> responseType) {
        String url = baseUrl + endpoint;
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody);
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType);
    }

    /**
     * Performs a PUT request to the specified endpoint with the given request body and a parameterized type reference.
     *
     * @param restTemplate The RestTemplate to use
     * @param baseUrl The base URL of the API
     * @param endpoint The endpoint to call (without the base URL)
     * @param requestBody The request body
     * @param typeReference The parameterized type reference for the response
     * @return The response entity
     */
    public static <T> ResponseEntity<T> put(RestTemplate restTemplate, String baseUrl, String endpoint, Object requestBody, ParameterizedTypeReference<T> typeReference) {
        String url = baseUrl + endpoint;
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody);
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, typeReference);
    }

    /**
     * Performs a DELETE request to the specified endpoint.
     *
     * @param restTemplate The RestTemplate to use
     * @param baseUrl The base URL of the API
     * @param endpoint The endpoint to call (without the base URL)
     * @param responseType The expected response type
     * @return The response entity
     */
    public static <T> ResponseEntity<T> delete(RestTemplate restTemplate, String baseUrl, String endpoint, Class<T> responseType) {
        String url = baseUrl + endpoint;
        HttpEntity<Object> requestEntity = new HttpEntity<>(null);
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, responseType);
    }

    /**
     * Performs a DELETE request to the specified endpoint with a parameterized type reference.
     *
     * @param restTemplate The RestTemplate to use
     * @param baseUrl The base URL of the API
     * @param endpoint The endpoint to call (without the base URL)
     * @param typeReference The parameterized type reference for the response
     * @return The response entity
     */
    public static <T> ResponseEntity<T> delete(RestTemplate restTemplate, String baseUrl, String endpoint, ParameterizedTypeReference<T> typeReference) {
        String url = baseUrl + endpoint;
        HttpEntity<Object> requestEntity = new HttpEntity<>(null);
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, typeReference);
    }
}
