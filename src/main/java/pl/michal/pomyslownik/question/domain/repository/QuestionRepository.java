package pl.michal.pomyslownik.question.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.michal.pomyslownik.common.dto.StatisticsDto;
import pl.michal.pomyslownik.question.domain.model.Question;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findAllByCategory_Id(UUID id, Pageable pageable);

    void deleteByCategory_Id(UUID categoryId);

    @Query("select q from Question q left join q.answers a group by q order by count(a) desc")
    Page<Question> findHot(Pageable pageable);

    @Query("select q from Question q left join q.answers a where a is null group by q order by q.id desc")
    Page<Question> findUnanswered(Pageable pageable);

    @Query(value = "select * from questions q where upper(q.name) like upper('%' || :query || '%')",
            countQuery = "select count(*) from questions q where upper(q.name) like upper('%' || :query || '%')",
            nativeQuery = true)
    Page<Question> findByQuery(String query, Pageable pageable);

    @Query(value = "select * from questions q order by random() limit :limit", nativeQuery = true)
    List<Question> findRandomQuestions(int limit);

    @Query(value = "select new pl.michal.pomyslownik.common.dto.StatisticsDto(" +
            "  count(distinct q), " +
            "  count(distinct a), " +
            "  (select count(*) from Category)) " +
            "from Question q " +
            "left join q.answers a")
    StatisticsDto statistics();

    long count();
}
