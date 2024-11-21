package pl.michal.pomyslownik.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.michal.pomyslownik.category.model.Category;
import pl.michal.pomyslownik.category.service.CategoryService;
import pl.michal.pomyslownik.question.service.QuestionService;

import static pl.michal.pomyslownik.category.controller.ControllerUtils.paging;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminViewController {

    private final QuestionService questionService;


    @GetMapping
    public String indexView(Model model) {
        model.addAttribute("statistics", questionService.statistics());
        return "admin/index";
    }

}
