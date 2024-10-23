package pl.michal.pomyslownik.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.michal.pomyslownik.category.service.CategoryService;

@Controller
@RequestMapping("/admin")
public class AdminViewController {


    @GetMapping
    public String indexView() {
        return "admin/index";
    }
}
