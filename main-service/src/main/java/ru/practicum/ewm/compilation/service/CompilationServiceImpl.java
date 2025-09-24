package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.CompilationMapper;
import ru.practicum.ewm.compilation.model.NewCompilationDto;
import ru.practicum.ewm.compilation.model.CompilationDto;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events = eventRepository.getEventsByIdIn(newCompilationDto.getEvents());
        if (events.size() != newCompilationDto.getEvents().size()) {
            throw new NotFoundException("Некоторые события не найдены");
        }
        log.info(events.toString());

        Compilation saveData = compilationMapper.toEntity(newCompilationDto, events);
        Compilation result = compilationRepository.save(saveData);

        log.info(result.getEvents().toString());
        return compilationMapper.toCompilationDto(result);
    }

    @Override
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
        compilationRepository.findAll().stream().map(compilationMapper::toCompilationDto).toList();
    }

    @Override
    public CompilationDto updateCompilation(Long compId, NewCompilationDto newCompilationDto) {
        Compilation currentCompilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборка не найдена"));
        List<Event> events = eventRepository.getEventsByIdIn(newCompilationDto.getEvents());

        if (newCompilationDto.getEvents().size() == 1 && newCompilationDto.getEvents().getFirst() == 0) {
            events = Collections.emptyList();
        } else {
            if (events.size() != newCompilationDto.getEvents().size()) {
                throw new NotFoundException("Некоторые события не найдены");
            }
        }

        Compilation updatedCompilation = compilationMapper.toEntity(newCompilationDto, events);
        compilationMapper.updateFields(currentCompilation, updatedCompilation);
        Compilation result = compilationRepository.save(currentCompilation);

        return compilationMapper.toCompilationDto(result);
    }
}