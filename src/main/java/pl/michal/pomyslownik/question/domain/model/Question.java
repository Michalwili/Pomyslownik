package pl.michal.pomyslownik.question.domain.model;

import jakarta.persistence.*;
import pl.michal.pomyslownik.category.model.Category;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    private UUID id;

    private String name;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

    public Question() {
        super();
        this.id = UUID.randomUUID();
    }

    public Question addAnswer(Answer answer) {
        if(answers == null) {
            answers = new LinkedHashSet<>();
        }
        answer.setQuestion(this);
        answers.add(answer);

        return this;
    }

    public Question(String name) {
        this();
        this.name = name;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Answer> getAnswers() {
        return Collections.unmodifiableSet(answers);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
