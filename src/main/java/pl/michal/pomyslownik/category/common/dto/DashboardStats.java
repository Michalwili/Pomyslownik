package pl.michal.pomyslownik.category.common.dto;

import lombok.Getter;

@Getter
public class DashboardStats {

    private final long categoriesCount;
    private final long questionsCount;
    private final long answersCount;

    public DashboardStats(long categoriesCount, long questionsCount, long answersCount) {
        this.categoriesCount = categoriesCount;
        this.questionsCount = questionsCount;
        this.answersCount = answersCount;
    }

}
