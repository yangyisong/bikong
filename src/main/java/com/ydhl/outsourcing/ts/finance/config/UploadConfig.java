package com.ydhl.outsourcing.ts.finance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


@Configuration
public class UploadConfig {

    @Bean
    public CommonsMultipartResolver filterMultipartResolver() {
        CommonsMultipartResolver filterMultipartResolver = new CommonsMultipartResolver();
        filterMultipartResolver.setDefaultEncoding("UTF-8");
        // multipartResolver.setMaxUploadSize(maxUploadSize);
        return filterMultipartResolver;
    }
}
