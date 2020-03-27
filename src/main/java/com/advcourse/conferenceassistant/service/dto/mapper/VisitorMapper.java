package com.advcourse.conferenceassistant.service.dto.mapper;

import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.service.dto.VisitorDto;

public class VisitorMapper {

    public static VisitorDto toDto(Visitor entity){
return new VisitorDto(entity.getId(),entity.getEmail());
    }

    public static Visitor fromDto(VisitorDto dto){
        Visitor visitor = new Visitor();
        visitor.setId(dto.getId());
        String email = dto.getEmail();
        visitor.setEmail(email);
        visitor.setName(dto.getEmail().substring(0,email.indexOf('@') ));
        return visitor;
    }
}

