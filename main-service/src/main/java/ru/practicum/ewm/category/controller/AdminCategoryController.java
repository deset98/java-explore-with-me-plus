package ru.practicum.ewm.category.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.category.service.CompilationService;

@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CompilationService categoryService;

    public AdminCategoryController(final CompilationService categoryService) {
        this.categoryService = categoryService;
    }

}
