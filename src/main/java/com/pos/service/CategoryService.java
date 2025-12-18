package com.pos.service;

import java.util.List;

import com.pos.exception.UserException;
import com.pos.payload.dto.CategoryDTO;

public interface CategoryService {

	
	CategoryDTO createCategory(CategoryDTO categoryDto) throws UserException;
	
	List<CategoryDTO> getCategoriesByStore(Long storeId);
	
	CategoryDTO updateCategory(CategoryDTO categoryDto, Long id) throws UserException;
	
	void deleteCategory(Long id) throws UserException;
}
