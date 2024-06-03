package com.prajwol.service;

import com.prajwol.dto.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmsEmailService {
    private JavaMailSender javaMailSender;

    @Autowired
    public EmsEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @KafkaListener(topics = "send-employee-email", groupId = "emp-email-group")
    public void receiveNewUserMessage(EmailDto emailDto) throws MessagingException {


        // Process the received message and send notification emails
        sendHtmlEmail(emailDto.getTo(), emailDto.getSubject(), emailDto.getBody());
    }

    public void sendHtmlEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage msg =  javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg,true,"UTF-8");
        helper.setFrom("noreply@account.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body,true);
        javaMailSender.send(msg);
    }
}
