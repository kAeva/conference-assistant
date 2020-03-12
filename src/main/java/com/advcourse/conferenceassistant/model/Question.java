package com.advcourse.conferenceassistant.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    private String question;
    private Date time;
    private int likes;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    private Set<Visitor> author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "question_likes",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id"))
    private Set<Visitor> user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Topic topic;
}
