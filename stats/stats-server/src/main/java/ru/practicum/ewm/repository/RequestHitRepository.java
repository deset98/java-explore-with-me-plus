package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.StatDto;
import ru.practicum.ewm.entity.EndpointHit;

import java.util.List;

@Repository
public interface RequestHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query(value = "SELECT new ru.practicum.ewm.StatDto(rh.app, rh.uri, COUNT(rh.id)) " +
            "FROM EndpointHit rh " +
            "WHERE rh.app = :app " +
            "AND FUNCTION('TO_TIMESTAMP', rh.timestamp, 'YYYY-MM-DD\"T\"HH24:MI:SS') " +
            "BETWEEN FUNCTION('TO_TIMESTAMP', :start, 'YYYY-MM-DD\"T\"HH24:MI:SS') " +
            "AND FUNCTION('TO_TIMESTAMP', :end, 'YYYY-MM-DD\"T\"HH24:MI:SS') " +
            "AND (:uris IS NULL OR rh.uri in :uris) " +
            "GROUP BY rh.app, rh.uri " +
            "ORDER BY COUNT(rh.id) DESC")
    List<StatDto> findNotUniqueStats(String start, String end, String app, List<String> uris);

    @Query(value = "SELECT new ru.practicum.ewm.StatDto(rh.app, rh.uri, COUNT(rh.id)) " +
            "FROM EndpointHit rh " +
            "WHERE rh.app = :app " +
            "AND FUNCTION('TO_TIMESTAMP', rh.timestamp, 'YYYY-MM-DD\"T\"HH24:MI:SS') " +
            "BETWEEN FUNCTION('TO_TIMESTAMP', :start, 'YYYY-MM-DD\"T\"HH24:MI:SS') " +
            "AND FUNCTION('TO_TIMESTAMP', :end, 'YYYY-MM-DD\"T\"HH24:MI:SS') " +
            "AND (:uris IS NULL OR rh.uri in :uris) " +
            "GROUP BY rh.app, rh.uri " +
            "ORDER BY COUNT(DISTINCT rh.id) DESC")
    List<StatDto> findUniqueStats(String start, String end, String app, List<String> uris);
}
