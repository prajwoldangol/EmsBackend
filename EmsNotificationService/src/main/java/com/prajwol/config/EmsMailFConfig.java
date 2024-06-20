package com.prajwol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmsMailFConfig {
    @Bean
    public JavaMailSender mailSender(){
        JavaMailSenderImpl imp = new JavaMailSenderImpl();
        imp.setHost("smtp.gmail.com");
        imp.setPort(587);
        imp.setUsername("email@gmail.com");
        imp.setPassword("your password");
        Properties props = imp.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.default-encoding", "UTF-8");
        props.put("mail.test-connection", "false");
        return  imp ;
    }
}
