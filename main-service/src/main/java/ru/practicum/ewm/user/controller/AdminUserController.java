package ru.practicum.ewm.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.model.UserInputDto;
import ru.practicum.ewm.user.model.UserResponseDto;
import ru.practicum.ewm.user.service.UserService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> findAll(@RequestParam(required = false) List<Long> ids, @RequestParam(defaultValue = "0", required = false) Integer from, @RequestParam(defaultValue = "10", required = false) Integer size) {
        return userService.findAll(ids, from, size);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> add(@Valid @RequestBody UserInputDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(dto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}