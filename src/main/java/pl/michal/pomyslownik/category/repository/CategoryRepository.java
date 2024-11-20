package pl.michal.pomyslownik.category.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.michal.pomyslownik.category.dto.CategoryWithStatisticsDto;
import pl.michal.pomyslownik.category.model.Category;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Page<Category> findByNameContainingIgnoreCase(String search, Pageable pageable);

    @Query("SELECT new pl.michal.pomyslownik.category.dto.CategoryWithStatisticsDto(" +
            "c.id, c.name, count(distinct q), count(a)) " +
            "FROM Category c " +
            "LEFT JOIN c.questions q " +
            "LEFT JOIN q.answers a " +
            "GROUP BY c.id ")
    List<CategoryWithStatisticsDto> findAllWithStatistics();

    long count();

}
