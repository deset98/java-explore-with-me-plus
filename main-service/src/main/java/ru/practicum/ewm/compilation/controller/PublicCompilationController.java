package ru.practicum.ewm.compilation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.category.service.CompilationService;

@RestController
@RequestMapping("/compilations")
public class PublicCompilationController {

    private final CompilationService compilationService;

    public PublicCompilationController(final CompilationService compilationService) {
        this.compilationService = compilationService;
    }
}