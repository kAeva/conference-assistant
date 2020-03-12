package com.advcourse.conferenceassistant.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Data
public class Topic {
    @Id
    @GeneratedValue
    private Long id;

    private String theme;
    private String description;
    private Date start;
    private Date end;

    @ManyToOne(fetch = FetchType.LAZY)
    private Conference conf;

    @OneToMany(mappedBy = "topic",cascade = CascadeType.ALL)
    private Set<Question> questions;
    @OneToMany(mappedBy = "topic",cascade = CascadeType.ALL)
    private Set<User> user;
}
