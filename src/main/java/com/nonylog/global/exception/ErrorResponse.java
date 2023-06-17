package com.nonylog.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> validation = new HashMap<>();

    public void addValidation(final String fieldName, final String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}
