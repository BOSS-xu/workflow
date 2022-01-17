package com.rzon.workflow.exception;

public class PreconditionUnsatisfiedException extends RuntimeException {
    
    private static final long serialVersionUID = -300809562540443265L;

    private Object detail;
    
    public PreconditionUnsatisfiedException(final String message) {
        super(message);
    }
    
    public PreconditionUnsatisfiedException(final String message, final Object detail) {
        super(message);
        this.detail = detail;
    }

    public Object getDetail() {
        return detail;
    }
}
