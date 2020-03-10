package com.learreactivespring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.learnreactivespring.controller.AppErrorController;

@Configuration
public class AppConfiguration {
    @Autowired
    private ErrorAttributes errorAttributes;

    @Bean
    public AppErrorController appErrorController() {
        return new AppErrorController(errorAttributes);
    }
}
