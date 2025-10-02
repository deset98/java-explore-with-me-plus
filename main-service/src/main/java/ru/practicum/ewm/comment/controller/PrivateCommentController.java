package ru.practicum.ewm.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentFullDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.service.CommentService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events/{eventId}/comments")
public class PrivateCommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentFullDto> addComment(@RequestBody @Valid NewCommentDto dto,
                                                     @PathVariable Long eventId,
                                                     @PathVariable Long userId) {
        log.info("Метод addComment(); even");

        CommentFullDto result = commentService.add(dto, eventId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }



    @GetMapping
    public ResponseEntity<List<CommentFullDto>> getCommentsBy(@PathVariable Long userId,
                                                              @PathVariable Long eventId) {
        log.info("Метод getCommentsByUserId(); userId={} eventId={}", userId, eventId);

        List<CommentFullDto> result = commentService.getAllBy(userId, eventId);
        return ResponseEntity.ok(result);
    }



//    удалить комментарий
//    @DeleteMapping("/{commentId}")



//    изменить комментарий
//    возвращает FullDto
//    @PatchMapping("/{commentId}")



}