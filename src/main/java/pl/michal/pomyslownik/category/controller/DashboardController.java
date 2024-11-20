package pl.michal.pomyslownik.category.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.michal.pomyslownik.category.service.DashboardService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        logger.info("Dashboard method started");

        var dashboardStats = dashboardService.getDashboardStats();

        model.addAttribute("categoryCount", dashboardStats.getCategoriesCount());
        model.addAttribute("questionCount", dashboardStats.getQuestionsCount());
        model.addAttribute("answerCount", dashboardStats.getAnswersCount());

        logger.info("Dashboard stats returned");

        return "admin/index";
    }
}