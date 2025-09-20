package ru.practicum.ewm.compilation.service;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.model.NewCompilationDto;
import ru.practicum.ewm.compilation.model.ResponseCompilationDto;

@Service
public interface CompilationService {
    ResponseCompilationDto createCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Long compId);

    ResponseCompilationDto updateCompilation(Long compId, NewCompilationDto newCompilationDto);
}
