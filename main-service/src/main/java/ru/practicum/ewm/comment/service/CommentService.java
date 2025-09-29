package ru.practicum.ewm.comment.service;

import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.ewm.comment.dto.CommentPublicDto;

import java.util.List;

public interface CommentService {
    List<CommentPublicDto> getPublicComments(@PathVariable Long eventId);
}