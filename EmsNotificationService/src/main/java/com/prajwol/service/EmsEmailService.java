package com.prajwol.service;

import com.prajwol.dto.EmailDto;
import com.prajwol.dto.EmsPhoneVerifyDto;
import com.prajwol.feing.EmsPhoneVerifyService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class EmsEmailService {
    private JavaMailSender javaMailSender;
    private EmsPhoneVerifyService emsPhoneVerifyService;
    @Autowired
    public EmsEmailService(JavaMailSender javaMailSender,  EmsPhoneVerifyService emsPhoneVerifyService) {
        this.javaMailSender = javaMailSender;
        this.emsPhoneVerifyService = emsPhoneVerifyService;
    }
    @KafkaListener(topics = "send-employee-email", groupId = "emp-email-group")
    public void receiveNewUserMessage(EmailDto emailDto) throws MessagingException {
        // Process the received message and send notification emails
        sendHtmlEmail(emailDto.getTo(), emailDto.getSubject(), emailDto.getBody());
        // sms
        String email_to = "";

        List<String> countries = Arrays.asList("US", "CA", "UK");
        EmsPhoneVerifyDto data =  emsPhoneVerifyService.verifyPhone(emailDto.getPhone(),countries);
        log.info("SMS: " + data.getSms_email());
        log.info("Phone is valid: " + data.isValid());
        log.info("domain" +data.getSms_domain()) ;
        if (data != null && data.isValid()) {
            email_to = "N/A".equals(data.getSms_email()) ?
                    ("N/A".equals(data.getSms_domain()) ? null : emailDto.getPhone() + "@" + data.getSms_domain())
                    : data.getSms_email();
            log.info("emailto" +email_to) ;
            if (email_to != null) {
                sendHtmlEmail(email_to, emailDto.getSubject(), emailDto.getBody());
            }
        }
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

//    private String getCarrierEmail(String carrier){
//        Map<String,String> carriersList = new HashMap<>();
////        "@txt.att.net"
////        @sms.myboostmobile.com
//        //@sms.cricketwireless.net
//        //@msg.fi.google.com
//        //@mymetropcs.com
//        //@messaging.sprintpcs.com
//        //@tmomail.net
//        //@email.uscc.net
//        //@vtext.com
//        //@vtext.com -> xfinity
//        //@vmobl.com
//
//        return null;
//    }
}
