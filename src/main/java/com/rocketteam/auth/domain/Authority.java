package com.rocketteam.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Authority extends BaseEntity {

    /** Authority name with given pattern 'view:products' */
    @Column(name = "authority_name", unique = true)
    private String authorityName;

    /** Assigned Role of Authority */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
    private List<Role> roles = new ArrayList<>();

}
