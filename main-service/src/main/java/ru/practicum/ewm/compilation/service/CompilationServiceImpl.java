package ru.practicum.ewm.compilation.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.repository.CompilationRepository;

@Service
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;

    public CompilationServiceImpl(final CompilationRepository compilationRepository) {
        this.compilationRepository = compilationRepository;
    }

}