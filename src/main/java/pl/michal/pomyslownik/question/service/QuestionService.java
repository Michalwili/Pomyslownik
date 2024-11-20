package pl.michal.pomyslownik.question.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.pomyslownik.common.dto.StatisticsDto;
import pl.michal.pomyslownik.question.domain.model.Question;
import pl.michal.pomyslownik.question.domain.repository.AnswerRepository;
import pl.michal.pomyslownik.question.domain.repository.QuestionRepository;
import pl.michal.pomyslownik.question.dto.QuestionDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    private final AnswerRepository answerRepository;

    @Transactional(readOnly = true)
    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Question> getQuestion(UUID id) {
        return  questionRepository.findById(id);
    }

    @Transactional
    public Question createQuestion(Question questionRequest) {
        Question question = new Question();

        question.setName(questionRequest.getName());

        return questionRepository.save(question);
    }

    @Transactional
    public Question updateQuestion(UUID id, Question questionRequest) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isEmpty()) {
            throw new EntityNotFoundException("Question not found with id: " + id);
        }

        Question question = optionalQuestion.get();
        question.setName(questionRequest.getName());
        return questionRepository.save(question);
    }

    @Transactional
    public void deleteQuestion(UUID id) {
        answerRepository.deleteByQuestionId(id);
        questionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Question> findAllByCategoryId(UUID id) {
        return questionRepository.findAllByCategory_Id(id, Pageable.unpaged());
    }

//    @Transactional(readOnly = true)
//    public Page<Question> findHot(Pageable pageable) {
//        return questionRepository.findHot(pageable);
//    }
    @Transactional(readOnly = true)
    public Page<Question> findHot(Pageable pageable) {
        Page<Question> result = questionRepository.findHot(pageable);
        if (result == null) {
            return Page.empty(pageable);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public Page<Question> findUnanswered(Pageable pageable) {
        return questionRepository.findUnanswered(pageable);

    }

    @Transactional(readOnly = true)
    public Page<Question> findByQuery(String query, Pageable pageable) {
        return questionRepository.findByQuery(query, pageable);
    }
    @Transactional(readOnly = true)
    public List<QuestionDto> findTop(int limit) {
        return questionRepository.findAll(PageRequest.of(0, limit))
                .get()
                .map(questionMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> findTop(UUID categoryId, int limit) {
        return questionRepository.findAllByCategory_Id(categoryId, PageRequest.of(0, limit))
                .stream()
                .map(questionMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> findRandom(int limit) {
        return questionRepository.findRandomQuestions(limit)
                .stream()
                .map(questionMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StatisticsDto statistics() {
        return questionRepository.statistics();
    }
}

