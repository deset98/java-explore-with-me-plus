package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comment.dto.CommentFullDto;
import ru.practicum.ewm.comment.dto.UpdCommentDto;
import ru.practicum.ewm.comment.mapper.CommentMapper;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.repository.CommentRepository;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    // Admin API:
    // ...



    // Public API:
    // ...



    // Private API:
    // ...
    @Override
    public void delete(Long userId, Long commentId) {
        log.debug("Метод delete(); userId={}, commentId={}", userId, commentId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id=" + commentId + " не найден"));

        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ConflictException("Пользователь не является автором комментария");
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentFullDto update(Long userId, Long commentId, UpdCommentDto updDto) {
        log.debug("Метод update(); userId={}, commentId={}, dto: {}", userId, commentId, updDto);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id=" + commentId + " не найден"));

        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ConflictException("Пользователь не является автором комментария");
        }

        comment.setAnnotation(updDto.getAnnotation());
        comment.setText(updDto.getText());
        comment = commentRepository.save(comment);

        return commentMapper.toFullDto(comment);
    }
}