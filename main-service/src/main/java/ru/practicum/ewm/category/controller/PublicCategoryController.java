package ru.practicum.ewm.category.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.category.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class PublicCategoryController {

    private final CategoryService categoryService;

    public PublicCategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

}