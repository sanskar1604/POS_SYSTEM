package com.pos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pos.model.User;
import com.pos.payload.dto.ProductDTO;
import com.pos.payload.response.ApiResponse;
import com.pos.service.ProductService;
import com.pos.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "API for managing products")
public class ProductController {

	private final ProductService productService;
	private final UserService userService;
	
	@PostMapping
	@Operation(summary = "Create Products")
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO, @RequestHeader("Authorization") String jwt){
		User user = userService.getUserFromJwtToken(jwt);
		return ResponseEntity.ok(productService.createProduct(productDTO, user));
	}
	
	@GetMapping("/{storeId}")
	@Operation(summary = "Get products by store_id")
	public ResponseEntity<List<ProductDTO>> getProductByStoreId(@PathVariable Long storeId, @RequestHeader("Authorization") String jwt){
		return ResponseEntity.ok(productService.getProductByStoreId(storeId));
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Update product by product id")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO, @RequestHeader("Authorization") String jwt){
		User user = userService.getUserFromJwtToken(jwt);
		return ResponseEntity.ok(productService.updateProduct(id, productDTO, user));
	}
	
	@GetMapping("/{storeId}/search")
	@Operation(summary = "Search product by keyword")
	public ResponseEntity<List<ProductDTO>> searchByKeyword(@PathVariable Long storeId, @RequestParam String keyword, @RequestHeader("Authorization") String jwt){
		return ResponseEntity.ok(productService.searchByKeyword(storeId, keyword));
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete product by product id")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id,  @RequestHeader("Authorization") String jwt){
		User user = userService.getUserFromJwtToken(jwt);
		productService.deleteProduct(id, user);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Product deleted successfully");
		return ResponseEntity.ok(apiResponse);
	}
}
