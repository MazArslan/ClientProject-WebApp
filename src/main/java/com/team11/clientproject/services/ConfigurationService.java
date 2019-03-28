package com.team11.clientproject.services;

public interface ConfigurationService {
    static final String EMAIL_CONFIGURATION = "email_authentication";

    int getConfiguration(String key);
}
