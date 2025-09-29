package ru.practicum.ewm.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentPublicDto {

    private String annotation;

    private String text;

    private String authorName;

    private String eventTitle;

    private LocalDateTime publishedOn;
}