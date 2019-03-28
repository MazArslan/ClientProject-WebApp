package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.EqualityDiversity;
import com.team11.clientproject.dtos.User;

public interface EqualityDiversityRepository {
    void insertEqualityDiversity(EqualityDiversity equalityDiversity);

    Boolean findUserCompletion(User user);
}
