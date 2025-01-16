package pl.michal.pomyslownik.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.michal.pomyslownik.IdeasConfiguration;

import pl.michal.pomyslownik.category.model.Category;
import pl.michal.pomyslownik.category.service.CategoryService;
import pl.michal.pomyslownik.common.controller.IdeasCommonViewController;
import pl.michal.pomyslownik.question.domain.model.Answer;
import pl.michal.pomyslownik.question.domain.model.Question;
import pl.michal.pomyslownik.question.service.AnswerService;
import pl.michal.pomyslownik.question.service.QuestionService;


import java.util.Optional;
import java.util.UUID;

import static pl.michal.pomyslownik.category.controller.ControllerUtils.*;

@Controller
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionViewController extends IdeasCommonViewController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final CategoryService categoryService;
    private final IdeasConfiguration ideasConfiguration;

    @GetMapping
    public String indexView(Model model) {
        model.addAttribute("questions", questionService.getQuestions());
        addGlobalAttributes(model);
        return "question/index";

    }

    @GetMapping("{id}")
    public String singleView(Model model, @PathVariable UUID id) {

    Question question = questionService.getQuestion(id)
            .orElseThrow(() -> new IllegalArgumentException("Question not found for id: " + id));

    Optional<Category> category = Optional.ofNullable(question.getCategory());

    model.addAttribute("question", question);
    model.addAttribute("category", category);
    model.addAttribute("answers", answerService.getAnswers(id));

    addGlobalAttributes(model);

    return "question/single";
    }


    @GetMapping("add")
    public String addView(Model model){
        model.addAttribute("question", new Question());

        return "question/add";
    }

    @PostMapping
    public String add(Question question){
        questionService.createQuestion(question);

        return "redirect:/questions";
    }

    @GetMapping("hot")
    public String hotView(
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model
    ){
        PageRequest pageRequest = PageRequest.of(page - 1, 5);

        Page<Question> questionsPage = questionService.findHot(pageRequest);
        model.addAttribute("questionsPage", questionsPage);
        paging(model, questionsPage);
        addGlobalAttributes(model);

        return "question/index";
    }

    @GetMapping("unanswered")
    public String UnansweredView(
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model
    ){
        PageRequest pageRequest = PageRequest.of(page - 1, ideasConfiguration.getPagingPageSize());

        Page<Question> questionsPage = questionService.findUnanswered(pageRequest);
        model.addAttribute("questionsPage", questionsPage);
        paging(model, questionsPage);
        addGlobalAttributes(model);

        return "question/index";
    }
    @PostMapping("/create")
    public String createQuestion(@RequestParam UUID id, @RequestParam String name, RedirectAttributes ra) {
        try {
            Optional<Category> optionalCategory = categoryService.getCategory(id);

            if (optionalCategory.isPresent()) {
                Question question = new Question();
                question.setName(name);
                question.setCategory(optionalCategory.get());
                questionService.saveQuestion(question);
                ra.addFlashAttribute("message", "Pytanie zostało pomyślnie dodanae.");
            } else {
                ra.addFlashAttribute("error", "Kategoria o podanym ID nie istnieje.");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Wystąpił błąd podczas dodawania pytania.");
        }
        return "redirect:/categories/" + id;
    }


}
