package ru.practicum.ewm;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHitDto {

    private String app;

    private String uri;

    private long hits;
}
