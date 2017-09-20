package com.grocero.dtos;

public class ResponseDto {

    private String message;

    public ResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "message='" + message + '\'' +
                '}';
    }
}
