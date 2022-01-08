package com.example.demo.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
	Boolean existsByGroupID(Long groupID);

	Optional<Group> findByGroupID(Long groupID);
}