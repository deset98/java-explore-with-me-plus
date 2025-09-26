package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.model.*;
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
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation currentCompilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборка не найдена"));
        List<Event> events = eventRepository.getEventsByIdIn(updateCompilationRequest.getEvents());

        if (updateCompilationRequest.getEvents().size() == 1 && updateCompilationRequest.getEvents().getFirst() == 0) {
            events = Collections.emptyList();
        } else {
            if (events.size() != updateCompilationRequest.getEvents().size()) {
                throw new NotFoundException("Некоторые события не найдены");
            }
        }

        Compilation updatedCompilation = compilationMapper.toEntity(updateCompilationRequest, events);
        compilationMapper.updateFields(currentCompilation, updatedCompilation);
        Compilation result = compilationRepository.save(currentCompilation);

        return compilationMapper.toCompilationDto(result);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable page = PageRequest.of(from / size, size);

        Page<Compilation> comps;
        if (pinned != null) {
            comps = compilationRepository.findAllByPinned(pinned, page);
        } else {
            comps = compilationRepository.findAll(page);
        }
        return comps.stream().map(compilationMapper::toCompilationDto).toList();
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборка не найдена"));
        return compilationMapper.toCompilationDto(compilation);
    }
}