package com.pos.mapper;

import com.pos.model.Category;
import com.pos.model.Product;
import com.pos.model.Store;
import com.pos.payload.dto.ProductDTO;

public class ProductMapper {

	public static ProductDTO toDTO(Product product) {
		
		ProductDTO prodDto = new ProductDTO();
		prodDto.setBrand(product.getBrand());
		prodDto.setId(product.getId());
		prodDto.setName(product.getName());
		prodDto.setSku(product.getSku());
		prodDto.setDescription(product.getDescription());
		prodDto.setMrp(product.getMrp());
		prodDto.setCategory(CategoryMapper.toDTO(product.getCategory()));
		prodDto.setSellingPrice(product.getSellingPrice());
		prodDto.setStoreId(product.getStore()!=null?product.getStore().getId():null);
		prodDto.setImage(product.getImage());
		prodDto.setCreatedAt(product.getCreatedAt());
		prodDto.setUpdatedAt(product.getUpdatedAt());
		
		return prodDto;
	}
	
	public static Product toEntity(ProductDTO prodDto, Store store, Category category) {
		Product product = new Product();
		product.setName(prodDto.getName());
		product.setSku(prodDto.getSku());
		product.setDescription(prodDto.getDescription());
		product.setMrp(prodDto.getMrp());
		product.setSellingPrice(prodDto.getSellingPrice());
		product.setBrand(prodDto.getBrand());
		product.setStore(store);
		product.setCategory(category);
		
		return product;
	}
}
