package com.pos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pos.model.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
	
	Store findByStoreAdminId(long adminId);

}
