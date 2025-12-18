package com.pos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.exception.UserException;
import com.pos.model.Category;
import com.pos.payload.dto.CategoryDTO;
import com.pos.payload.response.ApiResponse;
import com.pos.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@Tag(name = "Category", description = "API for managing category")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	
	@PostMapping
	@Operation(summary = "Create Category")
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDto) throws UserException {
		return ResponseEntity.ok(categoryService.createCategory(categoryDto));
	}
	
	@GetMapping("/store/{storeId}")
	@Operation(summary = "Get Categories by store_id")
	public ResponseEntity<List<CategoryDTO>> getCategoriesByStoreId(@PathVariable Long storeId) throws UserException {
		return ResponseEntity.ok(categoryService.getCategoriesByStore(storeId));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update Category by category_id")
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDto, @PathVariable Long id) throws UserException {
		return ResponseEntity.ok(categoryService.updateCategory(categoryDto, id));
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Category by store_id")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) throws UserException {
		categoryService.deleteCategory(id);
		
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Category deleted successfully");
		return ResponseEntity.ok(apiResponse);
	}
}
