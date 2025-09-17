package ru.practicum.ewm.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.user.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE (u.id IN :ids) ORDER BY u.id LIMIT :size OFFSET :from")
    List<User> findAllByParams(List<Long> ids, Pageable pageable);
}
