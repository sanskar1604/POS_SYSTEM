package com.pos.service;

import java.util.List;

import com.pos.exception.UserException;
import com.pos.model.User;
import com.pos.payload.dto.ProductDTO;

public interface ProductService {

	ProductDTO createProduct(ProductDTO productDto, User user)  throws Exception;
	
	ProductDTO updateProduct(Long id, ProductDTO prodDto, User user) throws Exception;
	
	void deleteProduct(Long id, User user) throws Exception;
	
	List<ProductDTO> getProductByStoreId(Long storeId);
	
	List<ProductDTO> searchByKeyword(Long storeId, String keyword);
	
}
