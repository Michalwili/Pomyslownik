package pl.michal.pomyslownik.question.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.pomyslownik.question.domain.model.Question;
import pl.michal.pomyslownik.question.domain.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

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
    public Question updateQuestion(UUID
                                               id, Question questionRequest) {
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
        questionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Question> findAllByCategoryId(UUID id) {
        return questionRepository.findAllByCategoryId(id);
    }
}

