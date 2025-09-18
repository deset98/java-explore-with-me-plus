package ru.practicum.ewm.compilation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.compilation.service.CompilationService;

@RestController
@RequestMapping("/compilations")
public class AdminCompilationController {

    private final CompilationService compilationService;

    public AdminCompilationController(final CompilationService compilationService) {
        this.compilationService = compilationService;
    }
}