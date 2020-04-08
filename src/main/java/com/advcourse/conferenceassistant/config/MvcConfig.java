package com.advcourse.conferenceassistant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.location}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file://" + "/home/volodymyr/IdeaProjects/git/conference-assistant/src/main/resources/static/images/");
        /*registry.addResourceHandler("/images/**")
                .addResourceLocations("/static/images/");*/
    }
}