package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {
    @Modifying
    @Query("UPDATE Stat vs SET vs.hits = vs.hits+1 WHERE vs.app=:app AND vs.uri=:uri")
    void incrementHits(String app, String uri);
}
