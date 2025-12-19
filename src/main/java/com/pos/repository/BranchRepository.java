package com.pos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pos.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

	List<Branch> findByStoreId(Long storeId);
}
