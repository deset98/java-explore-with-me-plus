package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.ewm.comment.dto.CommentFullDto;
import ru.practicum.ewm.comment.dto.CommentPublicDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.mapper.CommentMapper;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.repository.CommentRepository;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<CommentPublicDto> getPublicComments(@PathVariable Long eventId) {
        return commentRepository.findByEventId(eventId).stream()
                .map(com -> commentMapper.toPublicDto(com))
                .collect(Collectors.toList());
    }


    // Private API:
    @Override
    public CommentFullDto addComment(NewCommentDto dto, Long eventId, Long userId) {
        Comment commentEnt = commentMapper.toEntity(dto);
        commentEnt.setAuthor(userRepository.findById(userId).get());
        commentEnt.setEvent(eventRepository.findById(eventId).get());
        Comment savedComment = commentRepository.save(commentEnt);
        return commentMapper.toFullDto(savedComment);
    }


}