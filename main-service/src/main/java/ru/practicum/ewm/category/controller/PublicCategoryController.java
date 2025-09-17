package ru.practicum.ewm.category.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.category.service.CompilationService;

@RestController
@RequestMapping("/categories")
public class PublicCategoryController {

    private final CompilationService categoryService;

    public PublicCategoryController(final CompilationService categoryService) {
        this.categoryService = categoryService;
    }

}