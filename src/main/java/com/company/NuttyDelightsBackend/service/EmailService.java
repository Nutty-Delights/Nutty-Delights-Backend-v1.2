package com.company.NuttyDelightsBackend.service;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    public void sendEmail(SimpleMailMessage email);
}
