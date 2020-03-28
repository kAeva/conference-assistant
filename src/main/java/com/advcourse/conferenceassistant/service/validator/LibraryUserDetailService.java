package com.advcourse.conferenceassistant.service.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LibraryUserDetailService {

    @Autowired
    private StaffIsActive staffIsActive ;

    /**
     * check for existing staff with name = username in db
     * */
    @Transactional
    public boolean isUsernameAlreadyInUse(String username) {
        return staffIsActive.getActiveUser(username) == null;
    }

}

