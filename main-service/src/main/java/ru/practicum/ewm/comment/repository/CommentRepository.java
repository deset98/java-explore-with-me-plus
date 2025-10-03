package ru.practicum.ewm.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.comment.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByEventIdAndAuthorId(Long eventId, Long userId);

    List<Comment> findByEventId(Long eventId);

    boolean existsByIdAndEventId(Long id, Long eventId);

    boolean existsByIdAndAuthorId(Long commentId, Long authorId);
}