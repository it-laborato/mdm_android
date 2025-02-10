package com.hmdm;

public class MDMException extends Exception {
    public MDMError mdmError;
    public String comment;
    public MDMException(int code) {
        super(MDMError.getMessage(code));
        mdmError = new MDMError(code);
    }
    public MDMException(int code, String comment) {
        super(MDMError.getMessage(code) + ": " + comment);
        mdmError = new MDMError(code);
        this.comment = comment;
    }
}
