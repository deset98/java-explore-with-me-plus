package ru.practicum.ewm.user.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.user.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value =
            """
                    SELECT *
                    FROM users u
                    WHERE (COALESCE(:ids, NULL) IS NULL OR u.id IN (:ids))
                    ORDER BY u.id
                    LIMIT :size
                    OFFSET :from
             """,
            nativeQuery = true)
    List<User> findAllByParams(List<Long> ids, Integer from, Integer size);

    boolean existsByEmail(String email);
}
