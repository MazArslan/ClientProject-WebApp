package com.team11.clientproject.services;

import com.team11.clientproject.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Saves the feedback
     *
     * @param question1 the first question
     */
    @Override
    public void addFeedback(int question1) {
        feedbackRepository.saveFeedback(question1);
    }
}
