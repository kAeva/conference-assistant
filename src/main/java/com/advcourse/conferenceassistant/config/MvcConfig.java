package com.advcourse.conferenceassistant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    String path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        StringBuilder builder = new StringBuilder();
        builder.append("file:///")
                .append(path)
                .append(File.separator);
        registry.addResourceHandler("/images/**")
                .addResourceLocations(builder.toString());

    }
}