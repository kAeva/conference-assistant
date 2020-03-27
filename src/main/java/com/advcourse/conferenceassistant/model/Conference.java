package com.advcourse.conferenceassistant.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Conference {
    @Id
    @GeneratedValue
    private Long id;

    private String theme;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private String address;

}
