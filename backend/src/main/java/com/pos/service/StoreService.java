package com.pos.service;

import java.util.List;

import com.pos.domain.StoreStatus;
import com.pos.model.Store;
import com.pos.model.User;
import com.pos.payload.dto.StoreDTO;

public interface StoreService {

	StoreDTO createStore(StoreDTO storeDto, User user);
	
	StoreDTO getStoreById(Long id) throws Exception;
	
	List<StoreDTO> getAllStore();
	
	Store getStoreByAdmin();
	
	StoreDTO updateStore(Long id, StoreDTO storeDto) throws Exception;
	
	void deleteStore(Long id);
	
	StoreDTO getStoreByEmployee() throws Exception;
	
	StoreDTO moderateStore(Long id, StoreStatus status) throws Exception;
}

