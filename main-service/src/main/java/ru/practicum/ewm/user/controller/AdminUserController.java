package ru.practicum.ewm.user.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.UserInputDto;
import ru.practicum.ewm.user.dto.UserResponseDto;
import ru.practicum.ewm.user.service.UserService;

import java.util.List;


@RestController
@Slf4j
@Validated
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> findAll(@RequestParam(required = false) List<Long> ids,
                                         @RequestParam(defaultValue = "0",
                                                       required = false) @PositiveOrZero Integer from,
                                         @RequestParam(defaultValue = "10", required = false) @Positive Integer size) {
        log.debug("Контроллер AdminUserController; Метод findAll(); ids={}, from={}, size={}", ids, from, size);

        return userService.findAll(ids, from, size);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> add(@RequestBody @Valid UserInputDto dto) {
        log.debug("Контроллер AdminUserController; Метод add(); dto={}", dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(dto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long userId) {
        log.debug("Контроллер AdminUserController; Метод delete(); userId={}", userId);

        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}