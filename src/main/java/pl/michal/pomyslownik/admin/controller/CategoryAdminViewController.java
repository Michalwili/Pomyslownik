package pl.michal.pomyslownik.admin.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.michal.pomyslownik.category.model.Category;
import pl.michal.pomyslownik.category.service.CategoryService;

import java.util.UUID;

@Controller
@RequestMapping("/admin/categories")
public class CategoryAdminViewController {

    private final CategoryService categoryService;

    public CategoryAdminViewController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String indexView(Model model){
        model.addAttribute("categories", categoryService.getCategories());
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
    public String edit(@ModelAttribute("category") Category category, @PathVariable UUID id){
        categoryService.updateCategory(id, category);
        return "redirect:/admin/categories";
    }

    @GetMapping("{id}/delete")
    public String deleteView(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }
}
