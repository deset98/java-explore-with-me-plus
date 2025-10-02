package ru.practicum.ewm.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentFullDto;
import ru.practicum.ewm.comment.service.CommentService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events/{eventId}/comments/{commentId}")
public class AdminCommentController {

    private final CommentService serviceService;

    @PatchMapping
    public ResponseEntity<CommentFullDto> patchComment(@PathVariable Long eventId,
                                                       @PathVariable Long commentId,
                                                       @RequestParam boolean published) {
        log.info("Метод patchComment(); eventId={}, commentId={}", eventId, commentId);

        CommentFullDto result = serviceService.hide(eventId, commentId, published);
        return ResponseEntity.ok(result);
    }
}