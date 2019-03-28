package com.team11.clientproject.services;

import com.team11.clientproject.dtos.EqualityDiversity;
import com.team11.clientproject.dtos.User;

public interface EqualityDiversityService {
    void insertEntry(EqualityDiversity equalityDiversity);

    Boolean findUserCompletion(User user);
}
