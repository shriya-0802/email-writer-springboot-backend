package com.email.writer.app;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "*")
public class EmailGeneratorController {

    private final EmailGeneratorService emailGeneratorService;

    public EmailGeneratorController(EmailGeneratorService emailGeneratorService) {
        this.emailGeneratorService = emailGeneratorService;
    }

    // Endpoint that returns JSON
    @PostMapping(value = "/generate",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<?> generateEmail(@Valid @RequestBody EmailRequest emailRequest) {
        try {
            if (emailRequest.getEmailContent() == null || emailRequest.getEmailContent().trim().isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(new EmailResponse(null, false, "Email content cannot be empty"));
            }

            String generatedEmail = emailGeneratorService.generateEmailReply(emailRequest);

            if (generatedEmail != null && !generatedEmail.startsWith("Error")) {
                return ResponseEntity.ok(new EmailResponse(generatedEmail, true, null));
            } else {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new EmailResponse(null, false, generatedEmail));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EmailResponse(null, false, "Server error: " + e.getMessage()));
        }
    }

    // New endpoint that returns plain text only
    @PostMapping(value = "/generate/text",
            produces = MediaType.TEXT_PLAIN_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> generateEmailPlain(@Valid @RequestBody EmailRequest emailRequest) {
        try {
            if (emailRequest.getEmailContent() == null || emailRequest.getEmailContent().trim().isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: Email content cannot be empty");
            }

            String generatedEmail = emailGeneratorService.generateEmailReply(emailRequest);

            if (generatedEmail != null && !generatedEmail.startsWith("Error")) {
                return ResponseEntity.ok(generatedEmail);
            } else {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(generatedEmail);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}