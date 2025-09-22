package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.CompilationMapper;
import ru.practicum.ewm.compilation.model.NewCompilationDto;
import ru.practicum.ewm.compilation.model.ResponseCompilationDto;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public ResponseCompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = eventRepository.getEventsByIdIn(newCompilationDto.getEvents());
        if (events.size() != newCompilationDto.getEvents().size()) {
            throw new NotFoundException("Некоторые события не найдены");
        }

        Compilation saveData = CompilationMapper.toEntity(newCompilationDto, events);
        Compilation result = compilationRepository.save(saveData);

        return CompilationMapper.toResponseDto(result);
    }

    @Override
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
        log.info("Успешное удаление подборки!");
    }

    @Override
    public ResponseCompilationDto updateCompilation(Long compId, NewCompilationDto newCompilationDto) {
        Compilation currentCompilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборка не найдена"));
        List<Event> events = eventRepository.getEventsByIdIn(newCompilationDto.getEvents());
        if (events.size() != newCompilationDto.getEvents().size()) {
            throw new NotFoundException("Некоторые события не найдены");
        }

        Compilation updatedCompilation = CompilationMapper.toEntity(newCompilationDto, events);
        CompilationMapper.updateFields(currentCompilation, updatedCompilation);
        Compilation result = compilationRepository.save(currentCompilation);

        return CompilationMapper.toResponseDto(result);
    }
}