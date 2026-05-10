package com.email.writer.app;

import jakarta.validation.constraints.NotBlank;

public class EmailRequest {

    @NotBlank(message = "Email content cannot be blank")
    private String emailContent;

    private String tone;

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }
}