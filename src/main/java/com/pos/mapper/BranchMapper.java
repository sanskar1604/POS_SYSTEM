package com.pos.mapper;

import com.pos.model.Branch;
import com.pos.model.Store;
import com.pos.payload.dto.BranchDTO;

public class BranchMapper {

	public static BranchDTO toDTO(Branch branch) {
		BranchDTO branchDto = new BranchDTO();
		
		branchDto.setId(branch.getId());
		branchDto.setName(branch.getName());
		branchDto.setAddress(branch.getAddress());
		branchDto.setPhone(branch.getPhone());
		branchDto.setEmail(branch.getEmail());
		branchDto.setCloseTime(branch.getCloseTime());
		branchDto.setOpenTime(branch.getOpenTime());
		branchDto.setWorkingDays(branch.getWorkingDays());
		branchDto.setStoreId(branch.getStore()!=null?branch.getStore().getId():null);
		branchDto.setCreatedAt(branch.getCreatedAt());
		branchDto.setUpdatedAt(branch.getUpdatedAt());
		
		return branchDto;
		
	}
	
	public static Branch toEntity(BranchDTO branchDto, Store store) {
		Branch branch = new Branch();
		
		branch.setName(branchDto.getName());
		branch.setAddress(branchDto.getAddress());
		branch.setStore(store);
		branch.setEmail(branchDto.getEmail());
		branch.setPhone(branchDto.getPhone());
		branch.setCloseTime(branchDto.getCloseTime());
		branch.setOpenTime(branchDto.getOpenTime());
		branch.setWorkingDays(branchDto.getWorkingDays());
		branch.setCreatedAt(branchDto.getCreatedAt());
		branch.setUpdatedAt(branchDto.getUpdatedAt());
		
		return branch;
	}
}
