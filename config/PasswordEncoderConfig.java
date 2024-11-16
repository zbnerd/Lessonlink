package com.lessonlink.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class PasswordEncoderConfig {

    @Profile("default")
    @Bean
    public PasswordEncoder defaultPasswordEncoder() {
        return new BCryptPasswordEncoder(6);
    }

    @Profile("prod")
    @Bean
    public PasswordEncoder prodPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
