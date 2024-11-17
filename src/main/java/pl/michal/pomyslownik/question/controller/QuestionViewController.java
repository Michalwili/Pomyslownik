package pl.michal.pomyslownik.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.michal.pomyslownik.IdeasConfiguration;
import pl.michal.pomyslownik.category.controller.ControllerUtils;
import pl.michal.pomyslownik.category.service.CategoryService;
import pl.michal.pomyslownik.common.controller.IdeasCommonViewController;
import pl.michal.pomyslownik.question.domain.model.Question;
import pl.michal.pomyslownik.question.service.AnswerService;
import pl.michal.pomyslownik.question.service.QuestionService;

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



//    @GetMapping("{id}")
//    public String singleView(Model model, @PathVariable UUID id) {
//        model.addAttribute("question", questionService.getQuestion(id));
//        model.addAttribute("answers", answerService.getAnswers(id));
//        model.addAttribute("categories", categoryService.getCategories());
//        return "question/single";
//    }

    @GetMapping("{id}")
    public String singleView(Model model, @PathVariable UUID id) {
        Question question = questionService.getQuestion(id)
                .orElseThrow(() -> new IllegalArgumentException("Question not found for id: " + id));
        model.addAttribute("question", question);
        model.addAttribute("answers", answerService.getAnswers(id));
        addGlobalAttributes(model);
        return "question/single";
    }

    @GetMapping("add")
    public String addView(Model model) {
        model.addAttribute("question", new Question());
        return "question/add";
    }

    @PostMapping
    public String add(Question question) {
        questionService.createQuestion(question);
        return "redirect:/questions";
    }

    @GetMapping("hot")
    public String hotView(
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model
    ){
        PageRequest pageRequest = PageRequest.of(page - 1, ideasConfiguration.getPagingPageSize());

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
}
