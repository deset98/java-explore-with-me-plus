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
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;
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
    public List<UserDto> findAll(@RequestParam(required = false) List<Long> ids,
                                 @RequestParam(defaultValue = "0", required = false) @PositiveOrZero Integer from,
                                 @RequestParam(defaultValue = "10", required = false) @Positive Integer size) {
        log.debug("Метод findAll(); ids={}, from={}, size={}", ids, from, size);

        return userService.findAllBy(ids, from, size);
    }

    @PostMapping
    public ResponseEntity<UserDto> add(@RequestBody @Valid NewUserRequest newDto) {
        log.debug("Метод add(); newDto={}", newDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(newDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long userId) {
        log.debug("Метод delete(); userId={}", userId);

        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}