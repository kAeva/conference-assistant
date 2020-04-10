package com.advcourse.conferenceassistant.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.Objects;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    private String question;
    private LocalDateTime time;
    private boolean isAnswered;


    @ManyToOne
    private Visitor author;

    @ManyToOne
    private Topic topic;

    @ManyToMany
    private Set<Visitor> likes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id.equals(question.id) &&
                question.equals(question.question) &&
                author.equals(question.author) &&
                topic.equals(question.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, author, topic);
    }
}
