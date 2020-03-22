package com.advcourse.conferenceassistant;

import com.advcourse.conferenceassistant.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConferenceAssistantApplication {

    @Autowired
    private ConferenceRepository conferenceRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VisitorRepository visitorRepository;

    public static void main(String[] args) {
        SpringApplication.run(ConferenceAssistantApplication.class, args);
    }

}
