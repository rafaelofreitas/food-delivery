package br.com.fooddelivery.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {
    INVALID_DATA("/invalid-data", "Invalid Data"),
    SYSTEM_ERROR("/system-error", "System Error"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid Parameter"),
    INCOMPREHENSIBLE_MESSAGE("/incomprehensible-message", "Incomprehensible Message"),
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource Not Found"),
    ENTITY_IN_US("/entity-in-use", "Entity in Use"),
    BUSINESS_ERROR("/business-mistake", "Business Rule Violation");

    private String title;
    private String uri;

    ErrorType(String path, String title) {
        this.uri = "http://localhost:8080" + path;
        this.title = title;
    }
}