package com.advcourse.conferenceassistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Conference {
    @Id
    @GeneratedValue
    private Long id;

    private String theme;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conference that = (Conference) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(theme, that.theme) &&
                Objects.equals(description, that.description) &&
                Objects.equals(start, that.start) &&
                Objects.equals(end, that.end) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, theme, description, start, end, address);
    }
}
