package com.example.shoppinglist.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class MailSenderConfig {

    @Value("${spring.mail.username}")
    private String myEmail;
}
