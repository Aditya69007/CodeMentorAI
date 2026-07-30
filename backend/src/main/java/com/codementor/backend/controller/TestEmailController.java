package com.codementor.backend.controller;

import com.codementor.backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestEmailController {

    private final EmailService emailService;

    @PostMapping("/email")
    public String sendTestEmail(
            @RequestParam String to
    ) {

        emailService.sendEmail(
                to,
                "CodeMentorAI Test Email",
                """
                <html>
                    <body style="font-family:Arial,sans-serif;">
                        <h2>🎉 Email Configuration Successful</h2>

                        <p>Your CodeMentorAI email service is working correctly.</p>

                        <p>You are now ready to implement:</p>

                        <ul>
                            <li>Forgot Password</li>
                            <li>Email Verification</li>
                            <li>Welcome Emails</li>
                            <li>Security Alerts</li>
                        </ul>

                        <hr>

                        <p>
                            <b>CodeMentorAI</b><br>
                            AI Powered Coding Platform
                        </p>

                    </body>
                </html>
                """
        );

        return "Test email sent successfully.";

    }

}