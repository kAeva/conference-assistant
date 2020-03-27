package com.advcourse.conferenceassistant.config;

import com.advcourse.conferenceassistant.model.*;
import com.advcourse.conferenceassistant.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;


import java.time.LocalDateTime;

@Configuration
public class DatabaseInitials {

    @Autowired
    private ConferenceRepository conferenceRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext cntx) {
        return args -> {

            Set<Role> rolesAll = Set.of(Role.ADMIN, Role.MODERATOR);
            Set<Role> rolesModerator = Set.of( Role.MODERATOR);


            Staff staff1 = new Staff();
            staff1.setName("Barbara");
            staff1.setSurname("Liskov");
            staff1.setEmail("liskovbrbr@mail.com");
            staff1.setPass("super34secret9pass");
            staff1.setRoles(rolesModerator);


            Staff staff2 = new Staff();
            staff2.setName("Darth");
            staff2.setSurname("Vader");
            staff2.setEmail("dark_lord@sith.com");
            staff2.setPass("darkforce");
            staff2.setRoles(rolesAll);



            Conference conf1 = new Conference();
            conf1.setTheme("Dig Data Conference");
            conf1.setDescription("Big Data Conference Europe is a three-day conference with technical talks in the fields of Big Data, High Load, Data Science, Machine Learning and AI.");
            conf1.setStart(LocalDateTime.of(2020, 5, 14, 10, 00));
            conf1.setEnd(LocalDateTime.of(2020, 5, 14, 16, 00));
            conf1.setAddress("Crowne Plaza Vilnius M. K. Čiurlionio str. 84, Vilnius, Lithuania");
            conf1 = conferenceRepository.save(conf1);

            staff1.setColab(conf1);
            staff2.setColab(conf1);
            staff1 = moderatorRepository.save(staff1);
            staff2 = moderatorRepository.save(staff2);

            Topic topic1 = new Topic();
            topic1.setTheme("Deconstructing Deep Learning");
            topic1.setSpeaker("Mark West");
            topic1.setStart(LocalDateTime.of(2020, 5, 14, 10, 15));
            topic1.setEnd(LocalDateTime.of(2020, 5, 14, 11, 00));
            topic1 = topicRepository.save(topic1);
            topic1.setConf(conf1);

            Visitor visitor1 = new Visitor();
            visitor1.setEmail("imagenius@gmail.com");
            visitor1.setName("Anonymous");
            visitor1 = visitorRepository.save(visitor1);

            Visitor visitor2 = new Visitor();
            visitor2.setEmail("superdeveloper@gmail.com");
            visitor2.setName("Valentyn");
            visitor2 = visitorRepository.save(visitor2);

            Visitor visitor3 = new Visitor();
            visitor3.setEmail("stopcorruption@gmail.com");
            visitor3.setName("Antonina");
            visitor3 = visitorRepository.save(visitor3);


            Visitor visitor4 = new Visitor();
            visitor4.setEmail("wikihowyouknow@gmail.com");
            visitor4.setName("Taras");
            visitor4 = visitorRepository.save(visitor4);


            Visitor visitor5 = new Visitor();
            visitor5.setEmail("yasscleverone@gmail.com");
            visitor5.setName("Chloe");
            visitor5 = visitorRepository.save(visitor5);


            Visitor visitor6 = new Visitor();
            visitor6.setEmail("andrewtetcher@gmail.com");
            visitor6.setName("Loony");
            visitor6 = visitorRepository.save(visitor6);


            Question question1 = new Question();
            question1.setQuestion("What is the biggest challenge you had in your practice?");
            question1.setTopic(topic1);
            question1.setTime(LocalDateTime.now());
            question1.setAuthor(visitor1);
            question1.setLikes(new HashSet<>(Arrays.asList(visitor1)));
            question1 = questionRepository.save(question1);


            Topic topic2 = new Topic();
            topic2.setTheme("You can AI like an Expert");
            topic2.setSpeaker("Jon McLoone");
            topic2.setStart(LocalDateTime.of(2020, 5, 14, 11, 00));
            topic2.setEnd(LocalDateTime.of(2020, 5, 14, 12, 30));
            topic2.setConf(conf1);
            topic2 = topicRepository.save(topic2);


            Question question2 = new Question();
            question2.setQuestion("What should I do next, right after this lecture, to become an AI pro?");
            question2.setTopic(topic2);
            question2.setTime(LocalDateTime.now());
            question2.setAuthor(visitor2);
            question2.setLikes(new HashSet<>(Arrays.asList(visitor2, visitor1)));
            question2 = questionRepository.save(question2);

            Topic topic3 = new Topic();
            topic3.setTheme("Deep Learning for Lazy People... Neural Architecture Search with Automated Machine Learning");
            topic3.setSpeaker("Diego Hueltes");
            topic3.setStart(LocalDateTime.of(2020, 5, 14, 12, 30));
            topic3.setEnd(LocalDateTime.of(2020, 5, 14, 13, 00));
            topic3 = topicRepository.save(topic3);
            topic3.setConf(conf1);

            Question question3 = new Question();
            question3.setQuestion("How actually lazy are you?");
            question3.setTopic(topic3);
            question3.setTime(LocalDateTime.now());
            question3.setAuthor(visitor4);
            question3.setLikes(new HashSet<>(Arrays.asList(visitor3, visitor4)));
            question3 = questionRepository.save(question3);

            Topic topic4 = new Topic();
            topic4.setTheme("Predicting the Moment of Birth using Sensor Data in Dairy Cows");
            topic4.setSpeaker("Miel Hostens");
            topic4.setStart(LocalDateTime.of(2020, 5, 14, 13, 00));
            topic4.setEnd(LocalDateTime.of(2020, 5, 14, 14, 00));
            topic4 = topicRepository.save(topic4);
            topic4.setConf(conf1);


            Question question4 = new Question();
            question4.setQuestion("Why cows???");
            question4.setTopic(topic4);
            question4.setTime(LocalDateTime.now());
            question4.setAuthor(visitor5);
            question4.setLikes(new HashSet<>(Arrays.asList(visitor5)));
            question4 = questionRepository.save(question4);

            Topic topic5 = new Topic();
            topic5.setTheme("Knowledge and AI Powering Microsoft & Office 365 Products");
            topic5.setSpeaker("David Gorena Elizondo");
            topic5.setStart(LocalDateTime.of(2020, 5, 14, 14, 00));
            topic5.setEnd(LocalDateTime.of(2020, 5, 14, 14, 30));
            topic5 = topicRepository.save(topic5);
            topic5.setConf(conf1);

            Question question5 = new Question();
            question5.setQuestion("What is the most powerful AI tool provided by Miscrosoft?");
            question5.setTopic(topic5);
            question5.setTime(LocalDateTime.now());
            question5.setAuthor(visitor6);
            question5.setLikes(new HashSet<>(Arrays.asList(visitor5, visitor1)));
            question5 = questionRepository.save(question5);

        };
    }
}
