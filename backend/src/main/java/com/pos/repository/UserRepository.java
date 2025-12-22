package com.pos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pos.model.Store;
import com.pos.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
	List<User> findByStore(Store store);
	
	List<User> findByBranchId(Long branchId);
}
