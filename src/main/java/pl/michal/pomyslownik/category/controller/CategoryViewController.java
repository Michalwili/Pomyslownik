package pl.michal.pomyslownik.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.michal.pomyslownik.category.common.dto.Message;
import pl.michal.pomyslownik.category.model.Category;
import pl.michal.pomyslownik.category.service.CategoryService;
import pl.michal.pomyslownik.common.controller.IdeasCommonViewController;
import pl.michal.pomyslownik.question.domain.model.Question;
import pl.michal.pomyslownik.question.service.QuestionService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryViewController extends IdeasCommonViewController {

    private final CategoryService categoryService;
    private final QuestionService questionService;

    @GetMapping("/{id}")
    public String singleView(@PathVariable UUID id, Model model) {
        Category category = categoryService.getCategory(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found for id: " + id));
        List<Question> questions = questionService.findAllByCategoryId(id);
        Page<Category> categories = categoryService.getCategories(Pageable.unpaged());

        model.addAttribute("category", category);
        model.addAttribute("questions", questions);
        model.addAttribute("categories", categories);
        addGlobalAttributes(model);

        return "category/single";
    }

    @GetMapping("{id}/delete")
    public String deleteView(@PathVariable UUID id, RedirectAttributes ra) {
        try {
            questionService.deleteQuestion(id);
            ra.addFlashAttribute("message", Message.info("Kategoria usunięta"));
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("error", "Nie można usunąć kategorii, ponieważ jest powiązana z innymi elementami.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Wystąpił błąd podczas usuwania kategorii.");
        }
        return "redirect:/admin";
    }



}
