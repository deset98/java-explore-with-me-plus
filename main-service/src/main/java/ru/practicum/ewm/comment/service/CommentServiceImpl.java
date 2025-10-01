package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comment.dto.CommentFullDto;
import ru.practicum.ewm.comment.dto.CommentPublicDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.mapper.CommentMapper;
import ru.practicum.ewm.comment.model.Comment;
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
    // ...


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


}