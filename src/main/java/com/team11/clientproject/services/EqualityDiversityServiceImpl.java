package com.team11.clientproject.services;

import com.team11.clientproject.dtos.EqualityDiversity;
import com.team11.clientproject.dtos.User;
import com.team11.clientproject.repositories.EqualityDiversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Deals with business logic related to equality/diversity.
 */
@Service
public class EqualityDiversityServiceImpl implements EqualityDiversityService {

    private EqualityDiversityRepository equalityDiversityRepository;

    @Autowired
    public EqualityDiversityServiceImpl(EqualityDiversityRepository equalityDiversityRepository) {
        this.equalityDiversityRepository = equalityDiversityRepository;
    }

    /**
     * Saves an equality/diversity form entry
     *
     * @param equalityDiversity the equality/diversity entry
     */
    @Override
    public void insertEntry(EqualityDiversity equalityDiversity) {
        equalityDiversityRepository.insertEqualityDiversity(equalityDiversity);
    }

    /**
     * Finds if a user has completed equality/diversity
     */
    @Override
    public Boolean findUserCompletion(User user) {
        return equalityDiversityRepository.findUserCompletion(user);
    }


}
