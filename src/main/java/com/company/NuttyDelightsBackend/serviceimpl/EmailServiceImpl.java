package com.company.NuttyDelightsBackend.serviceimpl;

import com.company.NuttyDelightsBackend.service.EmailService;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    private  JavaMailSender javaMailSender;

    @Override
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);

    }
}
