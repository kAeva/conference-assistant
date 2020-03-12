package com.advcourse.conferenceassistant.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String mail;
    private String pass;
    private String fname;
    private String lname;

    @ManyToOne(fetch = FetchType.LAZY)
    private Conference conf;
    @ManyToOne(fetch = FetchType.LAZY)
    private Topic topic;
}
