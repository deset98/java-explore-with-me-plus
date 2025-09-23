package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.model.Event;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    Optional<Event> findByInitiatorIdAndId(Long initiatorId, Long eventId);

    Page<Event> findAllByInitiator_Id(Long initiatorId, Pageable pageable);

}