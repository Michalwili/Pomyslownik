package pl.michal.pomyslownik;

import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pomyslownik")
@Data
public class IdeasConfiguration {
    private String name;

    @Value("${paging.pageSize:2}")
    private int pagingPageSize;
}
