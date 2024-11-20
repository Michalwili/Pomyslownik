package pl.michal.pomyslownik.question.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.michal.pomyslownik.category.common.dto.Message;
import pl.michal.pomyslownik.category.service.CategoryService;
import pl.michal.pomyslownik.question.domain.model.Answer;
import pl.michal.pomyslownik.question.domain.model.Question;
import pl.michal.pomyslownik.question.service.AnswerService;
import pl.michal.pomyslownik.question.service.QuestionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerViewController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final CategoryService categoryService;

    @PostMapping("/create")
    public String createAnswer(@RequestParam UUID id, @RequestParam String name, RedirectAttributes ra) {
        try {
            Optional<Question> optionalQuestion = questionService.getQuestion(id);

            if (optionalQuestion.isPresent()) {
                // Jeśli pytanie istnieje
                Answer answer = new Answer();
                answer.setName(name);
                answer.setQuestion(optionalQuestion.get());
                answerService.saveAnswer(answer);
                ra.addFlashAttribute("message", "Odpowiedź została pomyślnie dodana.");
            } else {
                // Jeśli pytanie nie istnieje
                ra.addFlashAttribute("error", "Pytanie o podanym ID nie istnieje.");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Wystąpił błąd podczas dodawania odpowiedzi.");
        }
        return "redirect:/questions/" + id;
    }

    @GetMapping("/{id}/edit")
    public String editView(@PathVariable UUID id, @RequestParam UUID questionId, Model model) {
        try {
            Answer answer = answerService.getAnswer(id);
            model.addAttribute("answer", answer);
            model.addAttribute("questionId", questionId);
        } catch (Exception e) {
            model.addAttribute("error", "Wystąpił błąd podczas ładowania odpowiedzi do edycji.");
        }
        return "/question/edit";
    }


    @PostMapping("/{id}/edit")
    public String editAnswer(@PathVariable UUID id, @RequestParam UUID questionId, @ModelAttribute Answer answerRequest, RedirectAttributes ra) {
        try {
            answerService.updateAnswer(id, answerRequest);
            ra.addFlashAttribute("message", "Odpowiedź została pomyślnie zaktualizowana.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Wystąpił błąd podczas zapisywania odpowiedzi.");
        }
        return "redirect:/questions/" + questionId;
    }


    @GetMapping("/{id}/delete")
    public String deleteView(@PathVariable UUID id, @RequestParam UUID questionId, RedirectAttributes ra) {
        try {
            answerService.deleteAnswer(id);
            ra.addFlashAttribute("message", "Odpowiedź została pomyślnie usunięta.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Wystąpił błąd podczas usuwania odpowiedzi.");
        }
        return "redirect:/questions/" + questionId;
    }

}
