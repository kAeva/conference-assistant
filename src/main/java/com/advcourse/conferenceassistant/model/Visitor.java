package com.advcourse.conferenceassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Set;
import java.util.Objects;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Visitor {
    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "Visitor_Conference",
            joinColumns = { @JoinColumn(name = "visitor_id") },
            inverseJoinColumns = { @JoinColumn(name = "conf_id") }
    )
    private Set<Conference> visit;

    @ManyToMany(mappedBy = "likes")
    private Set<Question> liked;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor guest = (Visitor) o;
        return id.equals(guest.id) &&
                email.equals(guest.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
