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
    private StaffRepository staffRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext cntx) {
        return args -> {

            Set<Role> rolesAll = Set.of(Role.ADMIN, Role.MODERATOR);
            Set<Role> rolesModerator = Set.of(Role.MODERATOR);


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
            conf1.setTheme("Big Data Conference");
            conf1.setDescription("Big Data Conference Europe is a three-day conference with technical talks in the fields of Big Data, High Load, Data Science, Machine Learning and AI.");
            conf1.setStart(LocalDateTime.of(2020, 5, 14, 10, 00));
            conf1.setEnd(LocalDateTime.of(2020, 5, 14, 16, 00));
            conf1.setAddress("Crowne Plaza Vilnius M. K. Čiurlionio str. 84, Vilnius, Lithuania");
            conf1 = conferenceRepository.save(conf1);

            Conference conf2 = new Conference();
            conf2.setTheme("Small Data Conference");
            conf2.setDescription("Small Data Conference Europe is a three-day conference with technical talks in the fields of Small Data, High Load, Data Science, Machine Learning and AI.");
            conf2.setStart(LocalDateTime.of(2020, 6, 14, 9, 30));
            conf2.setEnd(LocalDateTime.of(2020, 6, 14, 15, 20));
            conf2.setAddress("1638  Conference Center Way, Leesburg, Virginia");
            conferenceRepository.save(conf2);

            Conference conf3 = new Conference();
            conf3.setTheme("Social Science Conference");
            conf3.setDescription("Masters International Research & Development Center cordially invites abstracts and/or full papers from the broad range of Social Science fields:\n" +
                    "\n" +
                    "Accounting\n" +
                    "Arts, Banking\n" +
                    "Culture\n" +
                    "Economics \n" +
                    "Education\n" +
                    "Environment\n" +
                    "European Union\n" +
                    "Finance, Heritage\n"
            );
            conf3.setStart(LocalDateTime.of(2021, 3, 27, 9, 05));
            conf3.setEnd(LocalDateTime.of(2021, 3, 27, 15, 45));
            conf3.setAddress("University of Washington Rome Center, Rome, Italy");
            conferenceRepository.save(conf3);

            Staff staff3 = new Staff();
            staff3.setName("Ivan");
            staff3.setSurname("Ivanenko");
            staff3.setEmail("iii@gmail.com");
            staff3.setPass("123");
            staff3.setRoles(rolesAll);
            staff3.setColab(conf2);


            Staff staff4 = new Staff();
            staff4.setName("Petro");
            staff4.setSurname("Petrenko");
            staff4.setEmail("follov@gmail.com");
            staff4.setPass("1234");
            staff4.setRoles(rolesModerator);
            staff4.setColab(conf2);


            Staff staff5 = new Staff();
            staff5.setName("PetroSamePass");
            staff5.setSurname("Petrenko");
            staff5.setEmail("follov@gmail.com");
            staff5.setPass("1234");
            staff5.setRoles(rolesModerator);
            staff5.setColab(conf2);


            Staff staff6 = new Staff();
            staff6.setName("John");
            staff6.setSurname("Connor");
            staff6.setEmail("johnC@uk.net");
            staff6.setPass("12");
            staff6.setRoles(rolesModerator);
            staff6.setColab(conf3);


            Staff staff7 = new Staff();
            staff7.setName("Sarah");
            staff7.setSurname("Connor");
            staff7.setEmail("johnC@uk.net");
            staff7.setPass("12345");
            staff7.setRoles(rolesAll);
            staff7.setColab(conf3);


            staff1.setColab(conf1);
            staff2.setColab(conf1);
            staffRepository.save(staff1);
            staffRepository.save(staff2);
            staffRepository.save(staff3);
            staffRepository.save(staff4);
            staffRepository.save(staff5);
            staffRepository.save(staff6);
            staffRepository.save(staff7);

            Topic topic1 = new Topic();
            topic1.setDescription("description Deconstructing Deep ");
            topic1.setSpeakerdesc("description Mark West");

            topic1.setTheme("Deconstructing Deep Learning");
            topic1.setSpeaker("Mark West");
            topic1.setStart(LocalDateTime.of(2020, 5, 14, 10, 15));
            topic1.setEnd(LocalDateTime.of(2020, 5, 14, 11, 00));
            topic1.setConf(conf1);
            topicRepository.save(topic1);


            Visitor visitor1 = new Visitor();
            visitor1.setEmail("imagenius@gmail.com");
            visitor1.setName("Anonymous");
            visitor1.setVisit(conf1);
            visitor1 = visitorRepository.save(visitor1);

            Visitor visitor2 = new Visitor();
            visitor2.setEmail("superdeveloper@gmail.com");
            visitor2.setName("Valentyn");
            visitor2.setVisit(conf1);
            visitor2 = visitorRepository.save(visitor2);

            Visitor visitor3 = new Visitor();
            visitor3.setEmail("stopcorruption@gmail.com");
            visitor3.setName("Antonina");
            visitor3.setVisit(conf2);
            visitor3 = visitorRepository.save(visitor3);


            Visitor visitor4 = new Visitor();
            visitor4.setEmail("wikihowyouknow@gmail.com");
            visitor4.setName("Taras");
            visitor4.setVisit(conf1);
            visitor4 = visitorRepository.save(visitor4);


            Visitor visitor5 = new Visitor();
            visitor5.setEmail("yasscleverone@gmail.com");
            visitor5.setName("Chloe");
            visitor5.setVisit(conf2);
            visitor5 = visitorRepository.save(visitor5);


            Visitor visitor6 = new Visitor();
            visitor6.setEmail("andrewtetcher@gmail.com");
            visitor6.setName("Loony");
            visitor6.setVisit(conf3);
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
            topic2.setDescription("desc You can AI like");
            topic2.setSpeakerdesc("desc Jon McLoone");
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
            topic3.setDescription("desc Deep Learning for");
            topic3.setSpeakerdesc("descr Diego Hueltes");
            topic3.setSpeaker("Diego Hueltes");
            topic3.setStart(LocalDateTime.of(2020, 5, 14, 12, 30));
            topic3.setEnd(LocalDateTime.of(2020, 5, 14, 13, 00));
            topic3.setConf(conf2);
            topic3 = topicRepository.save(topic3);


            Question question3 = new Question();
            question3.setQuestion("How actually lazy are you?");
            question3.setTopic(topic3);
            question3.setTime(LocalDateTime.now());
            question3.setAuthor(visitor4);
            question3.setLikes(new HashSet<>(Arrays.asList(visitor3, visitor4)));
            question3 = questionRepository.save(question3);

            Topic topic4 = new Topic();
            topic4.setTheme("Predicting the Moment of Birth using Sensor Data in Dairy Cows");
            topic4.setDescription("desc Predicting the Moment");
            topic4.setSpeakerdesc("desc Miel Hostens");
            topic4.setSpeaker("Miel Hostens");
            topic4.setStart(LocalDateTime.of(2020, 5, 14, 13, 00));
            topic4.setEnd(LocalDateTime.of(2020, 5, 14, 14, 00));
            topic4.setConf(conf2);
            topic4 = topicRepository.save(topic4);

            Question question4 = new Question();
            question4.setQuestion("Why cows???");
            question4.setTopic(topic4);
            question4.setTime(LocalDateTime.now());
            question4.setAuthor(visitor5);
            question4.setLikes(new HashSet<>(Arrays.asList(visitor5)));
            question4 = questionRepository.save(question4);

            Topic topic5 = new Topic();
            topic5.setTheme("Knowledge and AI Powering Microsoft & Office 365 Products");
            topic5.setDescription("desc Knowledge and AI Powe");
            topic5.setSpeakerdesc("desc David Gorena Elizondo");
            topic5.setSpeaker("David Gorena Elizondo");
            topic5.setStart(LocalDateTime.of(2020, 5, 14, 14, 00));
            topic5.setEnd(LocalDateTime.of(2020, 5, 14, 14, 30));
            topic5.setConf(conf3);
            topic5 = topicRepository.save(topic5);


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