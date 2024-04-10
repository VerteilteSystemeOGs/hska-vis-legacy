package de.hska.iwi.EShop.category.api;

import de.hska.iwi.EShop.category.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ResponseEntity<CategoryDTO> createNewCategory(CreateNewCategoryRequestDTO categoryRequest) {
        final var category = categoryService.createCategory(categoryRequest.getCategoryName());
        return ResponseEntity.ok(CategoryDTO.builder().id(category.getId()).name(category.getName()).build());
    }

    @Override
    public ResponseEntity<Void> deleteCategoryById(Integer id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getCategories().stream()
                .map(category -> CategoryDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .toList());
    }

    @Override
    public ResponseEntity<CategoryDTO> getCategoryById(Integer id) {
        return categoryService.getCategoryById(id)
                .map(category -> ResponseEntity.ok(CategoryDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build()))
                .orElse(ResponseEntity.notFound().build());
    }
}
