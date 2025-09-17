package ru.practicum.ewm.category.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CompilationService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


}
