package com.dgeniust.jwt_project.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION("Uncategorized exception", 9999, HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED("User existed",1001, HttpStatus.BAD_REQUEST),
    INVALID_USERNAME("Username must be at least {min} characters", 1002, HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("Password must be at least {min} characters",1003, HttpStatus.BAD_REQUEST),
    INVALID_KEY("Invalid key", 1004, HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED("User not existed", 1005, HttpStatus.NOT_FOUND),
    UNAUTHENTICATED("Unauthenticated",1006, HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("You do not have permission",1007, HttpStatus.FORBIDDEN),
    INVALID_DOB("Your age must be at least {min}",1008, HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(String message, int code, HttpStatusCode statusCode) {
        this.message = message;
        this.code = code;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
