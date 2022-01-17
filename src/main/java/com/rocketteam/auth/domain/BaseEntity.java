package com.rocketteam.auth.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@MappedSuperclass
public class BaseEntity {

    /** Unique ID of data */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    /** Creation timestamp of data */
    @CreationTimestamp
    private Timestamp createdAt;

    /** Last modified date */
    @LastModifiedDate
    private Timestamp lastModifiedAt;

}
