package ru.practicum.ewm.category.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.category.model.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNameIgnoreCase(String name);

    @Query(value =
            """
            SELECT *
            FROM categories
            ORDER BY id
            LIMIT :size
            OFFSET :from
            """,
            nativeQuery = true)
    List<Category> findCategoriesByOffsetAndLimit(int from, int size);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long catId);
}