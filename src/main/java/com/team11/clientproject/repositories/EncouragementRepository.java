package com.team11.clientproject.repositories;

import com.team11.clientproject.dtos.Encouragement;

import java.util.Optional;

public interface EncouragementRepository {
    Optional<Encouragement> getEncouragementForNumberOfImages(int numberOfImages);
}
