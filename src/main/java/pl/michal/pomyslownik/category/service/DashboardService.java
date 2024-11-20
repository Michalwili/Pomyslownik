package pl.michal.pomyslownik.category.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.michal.pomyslownik.category.common.dto.DashboardStats;
import pl.michal.pomyslownik.category.repository.CategoryRepository;
import pl.michal.pomyslownik.question.domain.repository.AnswerRepository;
import pl.michal.pomyslownik.question.domain.repository.QuestionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Service
public class DashboardService {

    private static final Logger logger = LoggerFactory.getLogger(DashboardService.class);

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public DashboardService(CategoryRepository categoryRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public DashboardStats getDashboardStats() {
        long categoriesCount = categoryRepository.count();
        long questionsCount = questionRepository.count();
        long answersCount = answerRepository.count();

        // Logowanie wartości
        logger.info("Categories count: {}", categoriesCount);
        logger.info("Questions count: {}", questionsCount);
        logger.info("Answers count: {}", answersCount);

        return new DashboardStats(categoriesCount, questionsCount, answersCount);
    }
}
