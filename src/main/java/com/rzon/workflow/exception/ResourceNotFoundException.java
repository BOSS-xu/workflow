package com.rzon.workflow.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8454625959503126134L;

    private Object detail;
    
    public ResourceNotFoundException(final String message) {
        super(message);
    }
    
    public ResourceNotFoundException(final String message, final Object detail) {
        super(message);
        this.detail = detail;
    }

    public Object getDetail() {
        return detail;
    }
}
