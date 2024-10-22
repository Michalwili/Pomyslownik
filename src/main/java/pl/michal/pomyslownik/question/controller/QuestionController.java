package pl.michal.pomyslownik.question.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.michal.pomyslownik.question.service.QuestionService;
import pl.michal.pomyslownik.question.domain.model.Question;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/questions")
public class QuestionController {
    private QuestionService questionsService;

    public QuestionController(QuestionService questionsService) {
        this.questionsService = questionsService;
    }

    @GetMapping
    List<Question> getQuestions() {
        return questionsService.getQuestions();
    }

    @GetMapping("{id}")
    Question getQuestion(@PathVariable UUID id) {
        return questionsService.getQuestion(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Question createQuestion(@RequestBody Question question) {
        return questionsService.createQuestion(question);
   }

   @PostMapping("{id}")
   @ResponseStatus(HttpStatus.ACCEPTED)
   Question updateQuestion(@PathVariable UUID id, @RequestBody Question question) {
        return questionsService.updateQuestion(id, question);
   }

   @ResponseStatus(HttpStatus.NO_CONTENT)
   @DeleteMapping("{id}")
   void deleteQuestion(@PathVariable UUID id) {
        questionsService.deleteQuestion(id);

   }
}
