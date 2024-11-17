package pl.michal.pomyslownik.category.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.pomyslownik.category.model.Category;
import pl.michal.pomyslownik.category.repository.CategoryRepository;
import pl.michal.pomyslownik.question.domain.repository.QuestionRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    public CategoryService(CategoryRepository categoryRepository, QuestionRepository questionRepository) {
        this.categoryRepository = categoryRepository;
        this.questionRepository = questionRepository;
    }

    @Transactional(readOnly = true)
    public Page<Category> getCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Category> getCategories(String search, Pageable pageable) {
        if(search == null) {
            return categoryRepository.findAll(pageable);
        } else {
            return categoryRepository.findByNameContainingIgnoreCase(search, pageable);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Category> getCategory(UUID id) {
       return categoryRepository.findById(id);
    }

    @Transactional
    public Category createCategory(Category categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        return categoryRepository.save(category);

    }

    @Transactional
    public Category updateCategory(UUID id, Category categoryRequest) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(categoryRequest.getName());
            return categoryRepository.save(category);
        } else {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
    }

    @Transactional
    public void deleteCategory(UUID id) {
        questionRepository.deleteByCategoryId(id);
        categoryRepository.deleteById(id);
    }
}
