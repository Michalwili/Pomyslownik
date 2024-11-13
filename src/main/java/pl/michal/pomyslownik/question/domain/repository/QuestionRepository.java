package pl.michal.pomyslownik.question.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.michal.pomyslownik.question.domain.model.Question;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findAllByCategoryId(UUID id);

    void deleteByCategoryId(UUID categoryId);

}
