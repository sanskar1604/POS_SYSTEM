package com.pos.mapper;

import com.pos.model.Store;
import com.pos.model.User;
import com.pos.payload.dto.StoreDTO;

import lombok.Data;

@Data
public class StoreMapper {

	public static StoreDTO toDTO(Store store) {
		StoreDTO storeDto = new StoreDTO();
		storeDto.setId(store.getId());
		storeDto.setBrand(store.getBrand());
		storeDto.setStoreAdmin(UserMapper.toDto(store.getStoreAdmin()));
		storeDto.setDescriptiom(store.getDescriptiom());
		storeDto.setStoreType(store.getStoreType());
		storeDto.setContact(store.getContact());
		storeDto.setCreatedAt(store.getCreatedAt());
		storeDto.setUpdatedAt(store.getUpdatedAt());
		storeDto.setStatus(store.getStatus());
		
		return storeDto;
	}
	
	public static Store toEntity(StoreDTO storeDto, User storeAdmin) {
		Store store = new Store();
		store.setId(storeDto.getId());
		store.setBrand(storeDto.getBrand());
		store.setContact(storeDto.getContact());
		store.setCreatedAt(storeDto.getCreatedAt());
		store.setDescriptiom(storeDto.getDescriptiom());
		store.setStatus(storeDto.getStatus());
		store.setStoreAdmin(storeAdmin);
		store.setStoreType(storeDto.getStoreType());
		store.setUpdatedAt(storeDto.getUpdatedAt());
		
		return store;
	}
}
