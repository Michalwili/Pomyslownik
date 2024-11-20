package pl.michal.pomyslownik.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.michal.pomyslownik.category.dto.CategoryWithStatisticsDto;
import pl.michal.pomyslownik.question.dto.QuestionDto;
import pl.michal.pomyslownik.question.service.QuestionService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class IndexViewController extends IdeasCommonViewController {

    private final QuestionService questionService;

    @GetMapping
    public String indexView(
            Model model
    ) {
        addGlobalAttributes(model);

        List<QuestionDto> questionsTop = questionService.findRandom(3);
        model.addAttribute("questionsTop", questionsTop);

        List<CategoryWithStatisticsDto> categories = categoryService.findAllWithStatistics();
        model.addAttribute("categories", categories);

        return "index/index";
    }
    public List<QuestionDto> topQuestionsByCategory(UUID categoryId) {
        List<QuestionDto> topQuestions = questionService.findTop(categoryId, 10);
        return topQuestions;
    }

    public List<QuestionDto> randomQuestions() {
        List<QuestionDto> randomQuestions = questionService.findRandom(2);
        return randomQuestions;
    }
}
