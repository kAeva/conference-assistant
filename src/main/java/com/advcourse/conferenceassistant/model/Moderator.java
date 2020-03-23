package com.advcourse.conferenceassistant.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Moderator {
    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String pass;
    private String name;
    private String surname;

}
