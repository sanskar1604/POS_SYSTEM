package com.pos.payload.dto;

import java.time.LocalDateTime;

import com.pos.model.Store;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private Long id;
	private String name;
	private String sku;
	private String description;
	private Double mrp;
	private Double sellingPrice;
	private String brand;
	private String image;
	private CategoryDTO category;
	private Long categoryId;
	private Long storeId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
