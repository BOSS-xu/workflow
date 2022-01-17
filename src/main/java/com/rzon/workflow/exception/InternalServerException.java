package com.rzon.workflow.exception;

public class InternalServerException extends RuntimeException {
    
    private static final long serialVersionUID = 2516238603253640950L;

    private Object detail;
    
    public InternalServerException(final String message) {
        super(message);
    }
    
    public InternalServerException(final String message, final Object detail) {
        super(message);
        this.detail = detail;
    }

    public Object getDetail() {
        return detail;
    }

}
