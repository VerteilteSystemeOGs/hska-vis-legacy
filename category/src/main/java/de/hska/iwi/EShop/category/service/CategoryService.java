package de.hska.iwi.EShop.category.service;

import de.hska.iwi.EShop.category.persistence.Category;
import de.hska.iwi.EShop.category.persistence.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(final int id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(final String categoryName) {
        final var category = Category.builder().name(categoryName).build();
        return categoryRepository.save(category);
    }

    public void deleteCategoryById(final int id) {
        categoryRepository.deleteById(id);
    }
}
