package ru.practicum.ewm.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.user.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users WHERE (:ids IS NULL OR id IN :ids) ORDER BY id LIMIT :size OFFSET :from", nativeQuery = true)
    List<User> findAllByParams(List<Long> ids, Integer from, Integer size);
}
