package com.email.writer.app;

public class EmailResponse {
    private String generatedEmail;
    private boolean success;
    private String errorMessage;

    public EmailResponse(String generatedEmail, boolean success, String errorMessage) {
        this.generatedEmail = generatedEmail;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public String getGeneratedEmail() {
        return generatedEmail;
    }

    public void setGeneratedEmail(String generatedEmail) {
        this.generatedEmail = generatedEmail;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}