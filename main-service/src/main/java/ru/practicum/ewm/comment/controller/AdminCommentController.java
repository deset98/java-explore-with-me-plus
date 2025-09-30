package ru.practicum.ewm.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentPublicDto;
import ru.practicum.ewm.comment.service.CommentService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events/{eventId}/comment/{commentId}/cancel")
public class AdminCommentController {

    private final CommentService serviceService;

    @PatchMapping
    public CommentPublicDto patchComment(@PathVariable Long eventId, @PathVariable Long commentId) {
        return serviceService.cancelComment(eventId, commentId);
    }

//    сделать комментарий непубличным (установить статус HIDE)
//    @PatchMapping("/cancelled")

}