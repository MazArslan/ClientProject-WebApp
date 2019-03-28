package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Tutorial;

import java.util.Optional;

public interface TutorialsRepository {
    Optional<Tutorial> findByName(String name);
}
