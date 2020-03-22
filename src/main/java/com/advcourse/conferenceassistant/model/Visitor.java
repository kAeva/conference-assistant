package com.advcourse.conferenceassistant.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Visitor {
    @Id
    @GeneratedValue
    private Long id;

    private String mail;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @ManyToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Question> likes;
}
