package se.sdaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.sdaproject.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
