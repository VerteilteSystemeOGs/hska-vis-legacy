package de.hska.iwi.EShop.category.api;

import de.hska.iwi.EShop.category.service.CategoryService;
import de.hska.iwi.EShop.integration.product.ApiException;
import de.hska.iwi.EShop.integration.product.api.ProductApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;
    private final ProductApi productApi;

    public CategoryController(CategoryService categoryService, ProductApi productApi) {
        this.categoryService = categoryService;
        this.productApi = productApi;
    }

    @Override
    public ResponseEntity<CategoryDTO> createNewCategory(CreateNewCategoryRequestDTO categoryRequest) {
        final var category = categoryService.createCategory(categoryRequest.getCategoryName());
        return ResponseEntity.ok(CategoryDTO.builder().id(category.getId()).name(category.getName()).build());
    }

    @Override
    public ResponseEntity<Void> deleteCategoryById(Integer id) {
        try {
            productApi.existsWithCategoryId(id);
            if (productApi.getApiClient().getStatusCode() == 200) {
                return ResponseEntity.status(409).build();
            }
        } catch (ApiException e) {
            // something went wrong
            // höchstwahrscheinlich nur ein 404 code, weil keine Produkte existieren, die die Kategorie nutzen
            // => kann gelöscht werden.
            return ResponseEntity.internalServerError().build();
        }

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
