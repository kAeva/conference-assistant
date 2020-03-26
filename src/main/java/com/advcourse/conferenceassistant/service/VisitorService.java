package com.advcourse.conferenceassistant.service;

import com.advcourse.conferenceassistant.model.Visitor;
import com.advcourse.conferenceassistant.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorService implements IVisitorService {
    @Autowired
    private VisitorRepository visitorRepository;


    @Override
    public Visitor registerNewVisitorAccount(Visitor accountVisitor) {

        Visitor save = visitorRepository.save(accountVisitor);
        return save;
    }

    private boolean emailExists(String email) {
        Visitor visitor = visitorRepository.findByEmail(email);
        if (visitor != null) {
            return true;
        }
        return false;
    }
}
