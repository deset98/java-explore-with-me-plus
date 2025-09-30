package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.CommentFullDto;
import ru.practicum.ewm.comment.dto.UpdCommentDto;

public interface CommentService {

    void delete(Long userId, Long commentId);

    CommentFullDto update(Long userId, Long commentId, UpdCommentDto updDto);
}