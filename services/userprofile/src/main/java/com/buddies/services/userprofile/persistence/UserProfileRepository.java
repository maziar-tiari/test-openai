package com.buddies.services.userprofile.persistence;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
	Optional<UserProfile> findByUsername(String username) throws IllegalArgumentException;

	Optional<UserProfile> findByUserId(String userId);

	void deleteByUserId(String userId);
}
