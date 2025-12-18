package com.pos.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pos.domain.UserRole;
import com.pos.exception.UserException;
import com.pos.mapper.CategoryMapper;
import com.pos.model.Category;
import com.pos.model.Store;
import com.pos.model.User;
import com.pos.payload.dto.CategoryDTO;
import com.pos.repository.CategoryRepository;
import com.pos.repository.StoreRepository;
import com.pos.service.CategoryService;
import com.pos.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	private final UserService userService;
	private final StoreRepository storeRepository;
	
	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDto) throws UserException {
		User user = userService.getCurrentUser();
		
		Store store = storeRepository.findById(categoryDto.getStoreId()).orElseThrow(() -> new UserException("store not found"));
		
		Category category = new Category();
		category.setStore(store);
		category.setName(categoryDto.getName());
		
		checkAuthoriy(user, store);
		
		categoryRepository.save(category);
		return CategoryMapper.toDTO(category);
	}

	@Override
	public List<CategoryDTO> getCategoriesByStore(Long storeId) {
		List<Category> categories = categoryRepository.findByStoreId(storeId);
		return categories.stream().map(CategoryMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDto, Long id) throws UserException {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new UserException("Category not found"));
		User user = userService.getCurrentUser();
		
		category.setName(categoryDto.getName());
		
		checkAuthoriy(user, category.getStore());
		
		return CategoryMapper.toDTO(categoryRepository.save(category));
	}

	@Override
	public void deleteCategory(Long id) throws UserException {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new UserException("Categiry not found"));
		User user = userService.getCurrentUser();
		
		checkAuthoriy(user, category.getStore());
		
		categoryRepository.delete(category);
		
	}
	
	private void checkAuthoriy(User user, Store store) throws UserException {
		boolean isAdmin = user.getRole().equals(UserRole.ROLE_STORE_ADMIN);
		boolean isManeger = user.getRole().equals(UserRole.ROLE_STORE_MANAGER);
		boolean isSameStore = user.equals(store.getStoreAdmin());
		
		if(!(isAdmin && isSameStore) && !isManeger) {
			throw new UserException("You don't have permission to manage this category");
		}
	}

}
