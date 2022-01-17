package com.rocketteam.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "USERS")
@Table(name = "USERS")
public class User {

    /** Unique id column in UUID type **/
    @Id
    @GeneratedValue(generator = "UUIDGenerator")
    @GenericGenerator(name = "UUIDGenerator",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(updatable = false, insertable = false)
    private UUID id;

    /** First name of User */
    @Column(name = "first_name")
    private String firstName;

    /** Last name of User */
    @Column(name = "last_name")
    private String lastName;

    /** Hashed Password of User */
    @Column(name = "password")
    private String password;

    /** Phone number of User */
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    /** Unique Email of User */
    @Column(name = "email", unique = true)
    private String email;

    /** Role of User */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Role role;

    /** Account locked status of User */
    @Column(name = "is_account_non_locked")
    private Boolean isAccountNonLocked;

    /** Account expired status of User */
    @Column(name = "is_account_non_expired")
    private Boolean isAccountNonExpired;

    /** Credentials expired status of User */
    @Column(name = "is_credentials_non_expired")
    private Boolean isCredentialsNonExpired;

    /** Account enabled status of User */
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    /** Creation timestamp of User */
    @CreationTimestamp
    private Timestamp createdAt;

    /** Updated timestamp of User */
    @UpdateTimestamp
    private Timestamp lastModifiedAt;

    /**
     * Setting true all account status fields
     */
    @PrePersist
    public void prePersist() {
        this.isCredentialsNonExpired = true;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isEnabled = true;
    }

}
