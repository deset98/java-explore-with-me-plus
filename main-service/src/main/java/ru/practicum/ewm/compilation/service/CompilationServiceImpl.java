package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.CompilationMapper;
import ru.practicum.ewm.compilation.model.NewCompilationDto;
import ru.practicum.ewm.compilation.model.ResponseCompilationDto;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;

    @Override
    public ResponseCompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        //получение списка Эвентов

        Compilation saveData = CompilationMapper.toEntity(newCompilationDto, List.of());
        Compilation result = compilationRepository.save(saveData);

        return CompilationMapper.toResponseDto(result);
    }

    @Override
    public void deleteCompilation(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборка не найдена"));

        compilationRepository.delete(compilation);
        log.info("Успешное удаление подборки!");
    }

    @Override
    public ResponseCompilationDto updateCompilation(Long compId, NewCompilationDto newCompilationDto) {
        Compilation currentCompilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборка не найдена"));
        //получение списка Эвентов

        Compilation updatedCompilation = CompilationMapper.toEntity(newCompilationDto, List.of());
        CompilationMapper.updateFields(currentCompilation, updatedCompilation);
        Compilation result = compilationRepository.save(currentCompilation);

        return CompilationMapper.toResponseDto(result);
    }
}