package pl.michal.pomyslownik.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsDto {
    private long answers;
    private long questions;
    private long categories;

}
