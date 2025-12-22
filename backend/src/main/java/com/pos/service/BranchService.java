package com.pos.service;

import java.util.List;

import com.pos.exception.UserException;
import com.pos.model.User;
import com.pos.payload.dto.BranchDTO;

public interface BranchService {

	BranchDTO createBranch(BranchDTO branchDto) throws Exception ;
	
	BranchDTO updateBranch(Long id, BranchDTO branchDto) throws Exception ;
	
	void deleteBranch(Long id) throws Exception ;
	
	List<BranchDTO> getAllBranchByStoreId(Long storeId);
	
	BranchDTO getBranchById(Long id) throws Exception ;
}
