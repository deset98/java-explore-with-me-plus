package ru.practicum.ewm.compilation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.category.service.CategoryService;

@RestController
@RequestMapping("/compilations")
public class PublicCompilationController {

    private final CategoryService compilationService;

    public PublicCompilationController(final CategoryService compilationService) {
        this.compilationService = compilationService;
    }
}