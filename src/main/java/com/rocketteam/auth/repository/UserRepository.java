package com.rocketteam.auth.repository;

import com.rocketteam.auth.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends PagingAndSortingRepository<User, UUID> {

    /**
     * Searches user in database and returns Optional<User>
     * @param identifier Identifier field of User.
     *                   It could be email or phone number.
     * @return The Optional object of User
     */
    @Query("SELECT u FROM USERS u WHERE u.email = :identifier OR u.phoneNumber = :identifier")
    Optional<User> findUserByIdentifier(@Param("identifier") String identifier);

    /**
     * Checks if email is taken or not
     * @param email
     * @return Status of availability
     */
    boolean existsByEmail(String email);

    /**
     * Checks if phone number is taken or not
     * @param phoneNumber
     * @return Status of phone number
     */
    boolean existsByPhoneNumber(String phoneNumber);

}
