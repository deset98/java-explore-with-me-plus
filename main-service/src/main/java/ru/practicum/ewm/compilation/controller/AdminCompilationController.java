package ru.practicum.ewm.compilation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.category.service.CategoryService;

@RestController
@RequestMapping("/compilations")
public class AdminCompilationController {

    private final CategoryService compilationService;

    public AdminCompilationController(final CategoryService compilationService) {
        this.compilationService = compilationService;
    }
}