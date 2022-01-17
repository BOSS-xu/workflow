package com.rzon.workflow.constant;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

public class ErrorMessage {

    private ErrorCode code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object detail;

    public ErrorMessage() {
    }

    public ErrorMessage(final ErrorCode code, final String message) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(message);

        this.code = code;
        this.message = message;
        this.detail = null;
    }

    public ErrorMessage(final ErrorCode code, final String message, final Object detail) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(message);

        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public ErrorCode getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getDetail() {
        return this.detail;
    }
}
