package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
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
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {

    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;

    private final CompilationMapper compilationMapper;

    // Admin API:
    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto newDto) {
        log.debug("Метод create(); newDto={}", newDto);

        List<Event> events = eventRepository.getEventsByIdIn(newDto.getEvents());

        if (events.size() != newDto.getEvents().size()) {
            throw new NotFoundException("Некоторые события не найдены");
        }

        log.info(events.toString());

        Compilation compilation = compilationMapper.toEntity(newDto, events);
        compilation = compilationRepository.save(compilation);

        log.info(compilation.getEvents().toString());

        return compilationMapper.toDto(compilation);
    }

    @Override
    @Transactional
    public CompilationDto update(Long compId, UpdateCompilationRequest updDto) {
        log.debug("Метод update(); compId={}, updDto={}", compId, updDto);

        Compilation currentCompilation = this.findCompilationBy(compId);

        List<Event> events = eventRepository.getEventsByIdIn(updDto.getEvents());

        if (updDto.getEvents().size() == 1 && updDto.getEvents().getFirst() == 0) {
            events = Collections.emptyList();
        } else {
            if (events.size() != updDto.getEvents().size()) {
                throw new NotFoundException("Некоторые события не найдены");
            }
        }

        Compilation updatedCompilation = compilationMapper.toEntity(updDto, events);
        compilationMapper.updateFields(currentCompilation, updatedCompilation);
        Compilation result = compilationRepository.save(currentCompilation);

        return compilationMapper.toDto(result);
    }

    @Override
    @Transactional
    public void delete(Long compId) {
        log.debug("Метод delete(); compId={}", compId);

        compilationRepository.deleteById(compId);
    }

    // Public API:
    @Override
    public List<CompilationDto> getAllBy(Boolean pinned, Integer from, Integer size) {
        log.debug("Метод getAllBy(); pinned={}, from={}, size={}", pinned, from, size);

        Pageable page = PageRequest.of(from / size, size);
        Page<Compilation> comps;

        if (pinned != null) {
            comps = compilationRepository.findAllByPinned(pinned, page);
        } else {
            comps = compilationRepository.findAll(page);
        }

        return comps.stream()
                .map(compilationMapper::toDto)
                .toList();
    }

    @Override
    public CompilationDto getBy(Long compId) {
        log.debug("Метод getBy(); compId={}", compId);

        Compilation compilation = this.findCompilationBy(compId);

        return compilationMapper.toDto(compilation);
    }


    private Compilation findCompilationBy(Long compId) {
        return compilationRepository.findById(compId).orElseThrow(() -> new NotFoundException("Подборка не найдена"));
    }
}