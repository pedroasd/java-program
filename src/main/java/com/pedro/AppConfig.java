package com.pedro;

import com.pedro.exception.CustomExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class AppConfig {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.pedro.oxm");
        return marshaller;
    }

    @Bean
    public CustomExceptionResolver customExceptionResolver() {
        return new CustomExceptionResolver();
    }
}

