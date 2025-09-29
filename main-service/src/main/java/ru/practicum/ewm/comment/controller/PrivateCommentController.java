package ru.practicum.ewm.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.comment.service.CommentService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/{userId}/events/{eventId}/comments")
public class PrivateCommentController {

    private final CommentService serviceService;

//    создать комментарий
//    возвращает FullDto
//    @PostMapping



//    получить все комментарии текущего пользователя по текущему событию
//    возвращает коллекцию FullDto
//    @GetMapping



//    удалить комментарий
//    @DeleteMapping("/{commentId}")



//    изменить комментарий
//    возвращает FullDto
//    @PatchMapping("/{commentId}")



}