package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.StatDto;
import ru.practicum.ewm.entity.RequestHit;

import java.util.List;

@Repository
public interface RequestHitRepository extends JpaRepository<RequestHit, Long> {
    @Query(value = "SELECT new ru.practicum.ewm.StatDto(rh.app, rh.uri, COUNT(rh.id)) " +
            "FROM RequestHit rh " +
            "WHERE rh.app = :app " +
            "AND FUNCTION('STR_TO_DATE', rh.timestamp, '%Y-%m-%d %H:%i:%s') " +
            "BETWEEN FUNCTION('STR_TO_DATE', :start, '%Y-%m-%d %H:%i:%s') " +
            "AND FUNCTION('STR_TO_DATE', :end, '%Y-%m-%d %H:%i:%s') " +
            "AND (:uris IS NULL OR rh.uri in :uris) " +
            "GROUP BY rh.uri " +
            "ORDER BY COUNT(rh.id) DESC")
    List<StatDto> findNotUniqueStats(String start, String end, String app, List<String> uris);

    @Query(value = "SELECT new ru.practicum.ewm.StatDto(rh.app, rh.uri, COUNT(rh.id)) " +
            "FROM RequestHit rh " +
            "WHERE rh.app = :app " +
            "AND FUNCTION('STR_TO_DATE', rh.timestamp, '%Y-%m-%d %H:%i:%s') " +
            "BETWEEN FUNCTION('STR_TO_DATE', :start, '%Y-%m-%d %H:%i:%s') " +
            "AND FUNCTION('STR_TO_DATE', :end, '%Y-%m-%d %H:%i:%s') " +
            "AND (:uris IS NULL OR rh.uri in :uris) " +
            "GROUP BY rh.uri " +
            "ORDER BY COUNT(DISTINCT rh.id) DESC")
    List<StatDto> findUniqueStats(String start, String end, String app, List<String> uris);
}
