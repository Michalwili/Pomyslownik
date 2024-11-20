package pl.michal.pomyslownik.admin.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.michal.pomyslownik.category.common.dto.Message;
import pl.michal.pomyslownik.category.model.Category;
import pl.michal.pomyslownik.category.service.CategoryService;
import java.util.UUID;

import static pl.michal.pomyslownik.category.controller.ControllerUtils.paging;

@Controller
@RequestMapping("/admin/categories")
public class CategoryAdminViewController {

    private final CategoryService categoryService;

    public CategoryAdminViewController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String indexView(
            @RequestParam(name = "s", required = false) String search,
            @RequestParam(name = "field", required = false, defaultValue = "id") String field,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), field);

        String reverseSort = direction.equals("asc") ? "desc" : "asc";

        Page<Category> categoriesPage = categoryService.getCategories(search, pageable);
        model.addAttribute("categoriesPage", categoriesPage);
        model.addAttribute("search", search);
        model.addAttribute("reverseSort", reverseSort);
        model.addAttribute("direction", direction);
        model.addAttribute("field", field);

        paging(model, categoriesPage);

        return "admin/category/index";
    }


    @GetMapping("{id}")
    public String editView(Model model, @PathVariable UUID id) {
        Category category = categoryService.getCategory(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        model.addAttribute("category", category);
        return "admin/category/edit";
    }


    @PostMapping("{id}")
    public String edit(@Valid @ModelAttribute("category") Category category,
                       BindingResult bindingResult,
                       @PathVariable UUID id,
                       RedirectAttributes ra,
                       Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("category", category);
            model.addAttribute("message", Message.error("Błąd zapisu"));
            return "admin/category/edit";
        }
        try {
        categoryService.updateCategory(id, category);
        ra.addFlashAttribute("message", Message.info("Kategoria zapisana"));
        } catch (Exception e) {
            model.addAttribute("category", category);
            model.addAttribute("message", Message.error("Nieznany błąd zapisu"));
            return "admin/category/edit";
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("{id}/delete")
    public String deleteView(@PathVariable UUID id, RedirectAttributes ra) {
        try {
            categoryService.deleteCategory(id);
            ra.addFlashAttribute("message", Message.info("Kategoria usunięta"));
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("error", "Nie można usunąć kategorii, ponieważ jest powiązana z innymi elementami.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Wystąpił błąd podczas usuwania kategorii.");
        }
        return "redirect:/admin/categories";
    }
}
