package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.ResponseExtHitDto;
import ru.practicum.ewm.entity.Hit;

import java.time.Instant;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query("""
                SELECT new ru.practicum.ewm.ResponseExtHitDto(
                    rh.app,
                    rh.uri,
                    CASE WHEN :unique = true THEN COUNT(DISTINCT rh.ip) ELSE COUNT(rh.id) END)
                FROM Hit rh
                WHERE rh.timestamp BETWEEN :start AND :end
                  AND (:uris IS NULL OR rh.uri IN :uris)
                GROUP BY rh.app, rh.uri
                ORDER BY
                    CASE WHEN :unique = true THEN COUNT(DISTINCT rh.ip) ELSE COUNT(rh.id) END DESC
            """)
    List<ResponseExtHitDto> getStats(Instant start, Instant end, List<String> uris, boolean unique);

    @Query("""
                SELECT COUNT(h.id)
                FROM Hit h
                WHERE h.timestamp BETWEEN :start AND :end
            """)
    Long countHits(Instant start, Instant end);
}