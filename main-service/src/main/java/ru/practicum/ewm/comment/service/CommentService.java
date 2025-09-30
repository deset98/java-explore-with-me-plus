package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.CommentFullDto;
import ru.practicum.ewm.comment.dto.CommentPublicDto;

import java.util.List;

public interface CommentService {

    CommentPublicDto cancelComment(Long eventId, Long commentId);

    List<CommentFullDto> getUserCommentsForEvent(Long userId, Long eventId);
}