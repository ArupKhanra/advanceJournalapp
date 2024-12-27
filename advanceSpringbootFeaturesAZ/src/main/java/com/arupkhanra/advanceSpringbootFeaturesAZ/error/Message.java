package com.arupkhanra.advanceSpringbootFeaturesAZ.error;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


public enum Message {

    ERROR("Error occurred", "An unexpected error occurred", LocalDateTime.now()),
    WARNING("Warning", "This is a warning message", LocalDateTime.now());

    private final String code;
    private final String message;
    private final LocalDateTime timestamp;

    // Constructor
    Message(String code, String message, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}