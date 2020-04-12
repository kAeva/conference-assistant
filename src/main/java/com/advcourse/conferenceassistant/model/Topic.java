package com.advcourse.conferenceassistant.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Topic {
    @Id
    @GeneratedValue
    private Long id;

    private String theme;
    private String description;
    private String speaker;
    private String speakerimg;
    private String speakerdesc;
    private LocalDateTime start;
    private LocalDateTime end;
    private boolean ended;


    @ManyToOne
    private Conference conference;

}
