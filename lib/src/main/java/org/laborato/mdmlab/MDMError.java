package org.laborato.mdmlab;

public class MDMError {
    public int code;
    public MDMError() {
        code = 0;
    }
    public MDMError(int code) {
        this.code = code;
    }

    public static String getMessage(int code) {
        switch (code) {
            case NO_ERROR:
                return "";
            case ERROR_NO_MDM:
                return "Laborato MDM not installed";
            case ERROR_INTERNAL:
                return "Internal Laborato MDM error";
            case ERROR_GENERAL:
                return "General error";
            case ERROR_DISCONNECTED:
                return "MDM service not connected";
            case ERROR_INVALID_PARAMETER:
                return "Invalid parameter";
            case ERROR_VERSION:
                return "Please update Laborato MDM launcher";
            case ERROR_KEY_NOT_MATCH:
                return "API key is not correct";
            case ERROR_NOT_CONFIGURED:
                return "Mobile agent is not configured";
            default:
                return "Unknown error";
        }
    }

    public String getMessage() {
        return getMessage(code);
    }

    public static final int NO_ERROR = 0;
    public static final int ERROR_NO_MDM = 1;           // Laborato MDM not installed
    public static final int ERROR_INTERNAL = 2;         // Launcher internal error
    public static final int ERROR_GENERAL = 3;          // Library error
    public static final int ERROR_DISCONNECTED = 4;     // Service disconnected
    public static final int ERROR_INVALID_PARAMETER = 5;
    public static final int ERROR_VERSION = 6;          // Launcher needs to update
    public static final int ERROR_KEY_NOT_MATCH = 7;    // API key does not match
    public static final int ERROR_NOT_CONFIGURED = 8;  // Laborato MDM is not configured
}
