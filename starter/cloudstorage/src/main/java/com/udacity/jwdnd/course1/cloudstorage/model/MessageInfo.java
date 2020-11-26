package com.udacity.jwdnd.course1.cloudstorage.model;

public class MessageInfo {

    private String message;
    private boolean error;

    public MessageInfo(String message, boolean error) {
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
