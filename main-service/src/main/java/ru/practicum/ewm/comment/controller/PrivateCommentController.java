package ru.practicum.ewm.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentFullDto;
import ru.practicum.ewm.comment.service.CommentService;

import java.util.List;

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


    @GetMapping
    public ResponseEntity<List<CommentFullDto>> getCommentsByUserId(@PathVariable Long userId,
                                                                    @PathVariable Long eventId) {
        log.info("Метод getCommentsByUserId(); userId={} eventId={}", userId, eventId);

        List<CommentFullDto> result = serviceService.getUserCommentsForEvent(userId, eventId);
        return ResponseEntity.ok(result);
    }


//    удалить комментарий
//    @DeleteMapping("/{commentId}")



//    изменить комментарий
//    возвращает FullDto
//    @PatchMapping("/{commentId}")



}