package ru.practicum.ewm.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.CategoryRequestDto;
import ru.practicum.ewm.comment.dto.CommentFullDto;
import ru.practicum.ewm.comment.dto.UpdCommentDto;
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
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long userId, @PathVariable Long eventId, @PathVariable Long commentId) {
        log.debug("Метод deleteComment(); userId={}, eventId={}, commentId={}", userId, eventId, commentId);

        serviceService.delete(userId, commentId);
    }

//    изменить комментарий
//    возвращает FullDto
//    @PatchMapping("/{commentId}")
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentFullDto> updateComment(@PathVariable Long userId, @PathVariable Long eventId,
                                                        @PathVariable Long commentId,
                                                        @Valid @RequestBody UpdCommentDto updDto) {
        log.debug("Метод updateComment(); updCommentDto={}", updDto);

        CommentFullDto result = serviceService.update(userId, commentId, updDto);
        return ResponseEntity.ok(result);
    }



}