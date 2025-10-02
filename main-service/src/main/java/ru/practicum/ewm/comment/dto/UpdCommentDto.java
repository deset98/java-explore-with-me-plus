package ru.practicum.ewm.comment.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdCommentDto {

    // userId и eventId из path

    @Size(min = 1, max = 300)
    private String annotation;

    @Size(min = 1, max = 3000)
    private String text;
}