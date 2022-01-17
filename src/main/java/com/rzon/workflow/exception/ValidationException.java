package com.rzon.workflow.exception;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = -8465423848306076988L;
    
    private Object detail;
    
    public ValidationException(final String message) {
        super(message);
    }
    
    public ValidationException(final String message, final Object detail) {
        super(message);
        this.detail = detail;
    }

    public Object getDetail() {
        return detail;
    }

}
