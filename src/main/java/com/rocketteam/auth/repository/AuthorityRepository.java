package com.rocketteam.auth.repository;

import com.rocketteam.auth.domain.Authority;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AuthorityRepository extends PagingAndSortingRepository<Authority, Long> {
}
