package com.advcourse.conferenceassistant.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Staff {
    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String pass;
    private String name;
    private String surname;

    @ManyToMany
    @JoinTable(
            name = "Staff_Conference",
            joinColumns = { @JoinColumn(name = "staff_id") },
    inverseJoinColumns = { @JoinColumn(name = "conf_id") }
    )
    private Set<Conference> colab;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "staff_role", joinColumns = @JoinColumn(name = "staff_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

}
