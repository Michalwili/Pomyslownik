package pl.michal.pomyslownik.category.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "categories")
public class Category {

    @Id
    private UUID id;

    @NotBlank(message = "{ideas.validation.name.NotBlank.message}")
    @Size(min = 3, max = 255)
    private String name;

    public Category() {
        this.id = UUID.randomUUID();
    }
    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

}
