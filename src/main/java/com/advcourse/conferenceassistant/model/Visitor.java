package com.advcourse.conferenceassistant.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;
import java.util.Objects;


@Entity
@Data
public class Visitor {
    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String name;

    @ManyToOne
    private Conference visit;

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
