package com.advcourse.conferenceassistant.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Data
public class Conference {
    @Id
    @GeneratedValue
    private Long id;

    private String theme;
    private String description;
    private Date start;
    private Date end;
    private String adress;

    @OneToMany(mappedBy = "conf", cascade = CascadeType.ALL)
    private Set<Topic> topics;
    @OneToMany(mappedBy = "conf",cascade = CascadeType.ALL)
    private Set<User> user;
}
