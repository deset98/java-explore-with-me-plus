package ru.practicum.ewm.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentFullDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.service.CommentService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{userId}/events/{eventId}/comments")
public class PrivateCommentController {

    private final CommentService commentService;

    //    создать комментарий
//    возвращает FullDto
//    @PostMapping
    @PostMapping
    public CommentFullDto addComment(@Valid @RequestBody NewCommentDto dto, @PathVariable Long eventId, @PathVariable Long userId) {
        return commentService.addComment(dto, eventId, userId);
    }


//    получить все комментарии текущего пользователя по текущему событию
//    возвращает коллекцию FullDto
//    @GetMapping


//    удалить комментарий
//    @DeleteMapping("/{commentId}")


//    изменить комментарий
//    возвращает FullDto
//    @PatchMapping("/{commentId}")


}