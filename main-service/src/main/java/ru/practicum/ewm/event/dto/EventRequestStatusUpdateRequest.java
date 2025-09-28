package ru.practicum.ewm.event.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateRequest {

    @Builder.Default
    private Set<Long> requestIds = new HashSet<>();

    @NotNull
    private UpdRequestStatus status;
}