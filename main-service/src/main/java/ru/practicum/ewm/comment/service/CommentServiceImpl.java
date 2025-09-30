package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comment.dto.CommentFullDto;
import ru.practicum.ewm.comment.dto.CommentPublicDto;
import ru.practicum.ewm.comment.mapper.CommentMapper;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.model.CommentState;
import ru.practicum.ewm.comment.repository.CommentRepository;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Override
    public CommentPublicDto cancelComment(Long eventId, Long commentId) {
       eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий не найден"));

        if (!comment.getEvent().getId().equals(eventId)) {
            throw new ConflictException("Комментарий не принадлежит указанному событию");
        }

        comment.setState(CommentState.HIDE);
        Comment updatedComment = commentRepository.save(comment);

        return commentMapper.toPublicDto(updatedComment);
    }

    @Override
    public List<CommentFullDto> getUserCommentsForEvent(Long userId, Long eventId) {
        List<Comment> comments = commentRepository.findAllByEventIdAndAuthorId(eventId, userId);

        return comments.stream().map(commentMapper::toFullDto).toList();
    }
}