package com.pos.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pos.domain.StoreStatus;
import com.pos.exception.UserException;
import com.pos.mapper.StoreMapper;
import com.pos.model.Store;
import com.pos.model.StoreContact;
import com.pos.model.User;
import com.pos.payload.dto.StoreDTO;
import com.pos.repository.StoreRepository;
import com.pos.service.StoreService;
import com.pos.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	
	private final StoreRepository storeRepository;
	private final UserService userService;

	@Override
	public StoreDTO createStore(StoreDTO storeDto, User user) {
		Store store = StoreMapper.toEntity(storeDto, user);
		return StoreMapper.toDTO(storeRepository.save(store));
	}

	@Override
	public StoreDTO getStoreById(Long id) throws UserException {
		Store store = storeRepository.findById(id).orElseThrow(() -> new UserException("Store not found"));
		return StoreMapper.toDTO(store);
	}

	@Override
	public List<StoreDTO> getAllStore() {
		List<Store> stores = storeRepository.findAll();
		return stores.stream().map(StoreMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public Store getStoreByAdmin() {
		User admin = userService.getCurrentUser();
		return storeRepository.findByStoreAdminId(admin.getId());
	}

	@Override
	public StoreDTO updateStore(Long id, StoreDTO storeDto) throws UserException {
		User currentUser = userService.getCurrentUser();
		Store existingStore = storeRepository.findByStoreAdminId(currentUser.getId());
		if(existingStore == null) {
			throw new UserException("Store not found");
		}
		
		existingStore.setBrand(storeDto.getBrand());
		existingStore.setDescriptiom(storeDto.getDescriptiom());
		
		if(storeDto.getStoreType() != null) {
			existingStore.setStoreType(storeDto.getStoreType());
		}
		
		if(storeDto.getContact() != null) {
			StoreContact contact = StoreContact.builder()
					.address(storeDto.getContact().getAddress())
					.phone(storeDto.getContact().getPhone())
					.email(storeDto.getContact().getEmail())
					.build();
			existingStore.setContact(contact);
		}
		
		Store updatedStore = storeRepository.save(existingStore);
		return StoreMapper.toDTO(updatedStore);
	}

	@Override
	public void deleteStore(Long id) {
		Store store = getStoreByAdmin();
		storeRepository.delete(store);
		
	}

	@Override
	public StoreDTO getStoreByEmployee() throws UserException {
		User currentUser = userService.getCurrentUser();
		if(currentUser == null) {
			throw new UserException("You don't have permission to access this store");
		}
		
		return StoreMapper.toDTO(currentUser.getStore());
	}

	@Override
	public StoreDTO moderateStore(Long id, StoreStatus status) throws Exception {
		Store store = storeRepository.findById(id).orElseThrow(() -> new Exception("Store not found...."));
		
		store.setStatus(status);
		Store updatedStore = storeRepository.save(store);
		return StoreMapper.toDTO(updatedStore);
	}

}
