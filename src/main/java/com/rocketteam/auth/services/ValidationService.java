package com.rocketteam.auth.services;

public interface ValidationService {

    /**
     * Checks if email is taken already
     * @param email
     * @return Status of email
     */
    boolean isEmailTaken(String email);

    /**
     * Checks if phone number is taken already
     * @param phoneNumber
     * @return Status of phone number
     */
    boolean isPhoneNumberTaken(String phoneNumber);

}
