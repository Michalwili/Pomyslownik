package pl.michal.pomyslownik.question.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.michal.pomyslownik.category.model.Category;


import java.time.LocalDateTime;
import java.util.LinkedHashSet;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "questions")
@Getter
@Setter
@ToString
public class Question {

    @Id
    private UUID id;

    private String name;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Answer> answers;

    private LocalDateTime created;

    private LocalDateTime modified;

    public Question() {
        this.id = UUID.randomUUID();
    }

    public Question(String name) {
        this();
        this.name = name;
    }

    @PrePersist
    void prePersist() {
        created = LocalDateTime.now();
        modified = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        modified = LocalDateTime.now();
    }

    public Question addAnswer(Answer answer) {
        if (answers == null) {
            answers = new LinkedHashSet<>();
        }

        answer.setQuestion(this);
        answers.add(answer);

        return this;
    }
}