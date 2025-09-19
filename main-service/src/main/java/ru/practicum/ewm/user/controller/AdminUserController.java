package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.model.UserInputDto;
import ru.practicum.ewm.user.model.UserResponseDto;
import ru.practicum.ewm.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Validated
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> findAll(@RequestParam List<Long> ids, @RequestParam(defaultValue = "0") Integer from, @RequestParam(defaultValue = "10") Integer size) {
        return userService.findAll(ids, from, size);
    }

    @PostMapping
    public UserResponseDto add(@RequestBody UserInputDto dto) {
        return userService.add(dto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        return userService.delete(userId);
    }
}