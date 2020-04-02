package com.advcourse.conferenceassistant.service.dto.mapper;

import com.advcourse.conferenceassistant.model.Conference;
import com.advcourse.conferenceassistant.service.dto.ConferenceDto;

import java.time.LocalDateTime;

public class ConferenceMapper {

    public static ConferenceDto toDto(Conference entity) {
        return new ConferenceDto(
                entity.getId(),
                entity.getTheme(),
                entity.getDescription(),
                entity.getStart(),
                entity.getEnd(),
                entity.getAddress() );
    }

    public static Conference fromDto(ConferenceDto dto){
        return new Conference(
                dto.getId(),
                dto.getTheme(),
                dto.getDescription(),
                dto.getStart(),
                dto.getEnd(),
                dto.getAddress() );
    }
}
