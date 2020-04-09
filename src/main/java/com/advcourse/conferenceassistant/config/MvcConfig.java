package com.advcourse.conferenceassistant.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class MvcConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        StringBuilder builder=new StringBuilder();
        builder.append("file:///")
                .append(System.getProperty("user.dir"))
                .append(File.separator)
                .append("src")
                .append(File.separator)
                .append("main")
                .append(File.separator)
                .append("resources")
                .append(File.separator)
                .append("static")
                .append(File.separator)
                .append("images")
                .append(File.separator);
        registry.addResourceHandler("/images/**")
                .addResourceLocations(builder.toString());

    }
}