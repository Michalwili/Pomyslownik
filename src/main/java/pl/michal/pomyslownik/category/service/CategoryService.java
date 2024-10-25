package pl.michal.pomyslownik.category.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.michal.pomyslownik.category.model.Category;
import pl.michal.pomyslownik.category.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<Category> getCategories() {
       return categoryRepository.findAll();
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
        categoryRepository.deleteById(id);

    }
}
