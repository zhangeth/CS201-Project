package com.example.demo.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	Boolean existsByCommentID(Long commentID);

	Optional<Comment> findByCommentID(Long commentID);
}