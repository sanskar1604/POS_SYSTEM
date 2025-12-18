package com.pos.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pos.exception.UserException;
import com.pos.mapper.ProductMapper;
import com.pos.model.Category;
import com.pos.model.Product;
import com.pos.model.Store;
import com.pos.model.User;
import com.pos.payload.dto.ProductDTO;
import com.pos.repository.CategoryRepository;
import com.pos.repository.ProductRepository;
import com.pos.repository.StoreRepository;
import com.pos.service.ProductService;
import com.pos.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;
	private final UserService userService;
	private final StoreRepository storeRepository;
	private final CategoryRepository categoryRepository;

	@Override
	public ProductDTO createProduct(ProductDTO productDto, User user) throws UserException {
		Store store = storeRepository.findById(productDto.getStoreId()).orElseThrow(() -> new UserException("Store not found"));
		
		Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new UserException("Category not found"));
		
		Product product = ProductMapper.toEntity(productDto, store, category);
		Product savedProduct = productRepository.save(product);
		return ProductMapper.toDTO(savedProduct);
	}

	@Override
	public ProductDTO updateProduct(Long id, ProductDTO prodDto, User user)  throws UserException {
		Product product = productRepository.findById(id).orElseThrow(() -> new UserException("Product not found"));
		
		product.setName(prodDto.getName());
		product.setDescription(prodDto.getDescription());
		product.setSku(prodDto.getSku());
		product.setImage(prodDto.getImage());
		product.setMrp(prodDto.getMrp());
		product.setSellingPrice(prodDto.getSellingPrice());
		product.setBrand(prodDto.getBrand());
		product.setUpdatedAt(LocalDateTime.now());
		
		if(prodDto.getCategoryId() != null) {
			Category category = categoryRepository.findById(prodDto.getCategoryId()).orElseThrow(() -> new UserException("Category not found"));
			
			if(category != null) {
				product.setCategory(category);
			}
		}
		
		Product savedProd = productRepository.save(product);
		return ProductMapper.toDTO(product);
	}

	@Override
	public void deleteProduct(Long id, User user) throws UserException {
		Product product = productRepository.findById(id).orElseThrow(() -> new UserException("Product not found"));
		productRepository.delete(product);
	}

	@Override
	public List<ProductDTO> getProductByStoreId(Long storeId) {
		List<Product> products = productRepository.findByStoreId(storeId);
		return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> searchByKeyword(Long storeId, String keyword) {
		List<Product> products = productRepository.searchByKeyword(storeId, keyword);
		return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
	}

}
