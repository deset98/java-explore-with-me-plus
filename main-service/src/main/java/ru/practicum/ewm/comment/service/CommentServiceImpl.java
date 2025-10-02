package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comment.dto.CommentFullDto;
import ru.practicum.ewm.comment.dto.CommentPublicDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.dto.UpdCommentDto;
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

    // Admin API:
    @Override
    public CommentFullDto hide(Long eventId, Long commentId, boolean published) {
        log.info("Метод hide(); eventId={}; commentId={}", eventId, commentId);

        if (!commentRepository.existsByIdAndEventId(commentId, eventId)) {
            throw new ConflictException("Комментарий не принадлежит указанному событию; eventId={}; commentId={}",
                    eventId, commentId);
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment id={} не найден", commentId));
        if (published) {
            comment.setState(CommentState.PUBLIC);
        } else {
            comment.setState(CommentState.HIDE);
        }
        comment = commentRepository.save(comment);

        return commentMapper.toFullDto(comment);
    }


    // Public API:
    @Override
    public List<CommentPublicDto> getAllBy(Long eventId) {
        log.info("Метод getAllBy(); eventId = {}", eventId);

        List<Comment> comments = commentRepository.findByEventId(eventId);

        return comments.stream()
                .map(commentMapper::toPublicDto)
                .toList();
    }


    // Private API:
    @Override
    public CommentFullDto add(NewCommentDto dto, Long eventId, Long userId) {
        log.info("Метод add(); eventId={}, userId={}; dto={}", eventId, userId, dto);

        if (!eventRepository.existsByIdAndInitiatorId(eventId, userId)) {
            throw new ConflictException("Инициатор не может комментировать свои события; eventId={}, userId={}",
                    eventId, userId);
        }

        Comment comment = commentMapper.toEntity(dto);
        comment.setAuthor(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User id={}, не найден", userId)));
        comment.setEvent(eventRepository
                .findById(eventId).orElseThrow(() -> new NotFoundException("Event id={}, не найден", eventId)));
        comment = commentRepository.save(comment);

        return commentMapper.toFullDto(comment);
    }

    @Override
    public List<CommentFullDto> getAllBy(Long userId, Long eventId) {
        log.info("Метод getUserCommentsForEvent(); eventId={}; commentId={}", userId, eventId);

        List<Comment> comments = commentRepository.findAllByEventIdAndAuthorId(eventId, userId);

        return comments.stream()
                .map(commentMapper::toFullDto)
                .toList();
    }

    @Override
    public void delete(Long userId, Long commentId) {
        log.info("Метод delete(); userId={}, commentId={}", userId, commentId);

        this.checkExistsUserAndComment(userId, commentId);

        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentFullDto update(Long userId, Long commentId, UpdCommentDto updDto) {
        log.info("Метод update(); userId={}, commentId={}, dto: {}", userId, commentId, updDto);

        this.checkExistsUserAndComment(userId, commentId);

        Comment comment = commentRepository.findById(commentId).get();
        commentMapper.updateFromDto(updDto, comment);
        comment = commentRepository.save(comment);

        return commentMapper.toFullDto(comment);
    }


    private void checkExistsUserAndComment(Long userId, Long commentId) {
        log.info("Метод checkExistsUserAndComment(); userId={}, commentId={}", userId, commentId);

        if (!commentRepository.existsByIdAndAuthorId(commentId, userId)) {
            if (!userRepository.existsById(userId)) {
                throw new NotFoundException("User id={}, не существует", userId);
            } else if (!commentRepository.existsById(commentId)) {
                throw new NotFoundException("Comment id={}, не существует", commentId);
            } else {
                throw new ConflictException("Пользователь не является автором комментария; " +
                        "userId={}, commentId={}", userId, commentId);
            }
        }
    }
}