package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.CommentFullDto;
import ru.practicum.ewm.comment.dto.CommentPublicDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;

import java.util.List;

public interface CommentService {
    List<CommentPublicDto> getPublicComments(Long eventId);

    CommentFullDto addComment(NewCommentDto dto, Long eventId, Long userId);
}