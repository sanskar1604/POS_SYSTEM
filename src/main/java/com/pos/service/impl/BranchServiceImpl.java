package com.pos.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pos.exception.UserException;
import com.pos.mapper.BranchMapper;
import com.pos.model.Branch;
import com.pos.model.Store;
import com.pos.model.User;
import com.pos.payload.dto.BranchDTO;
import com.pos.repository.BranchRepository;
import com.pos.repository.StoreRepository;
import com.pos.service.BranchService;
import com.pos.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
	
	private final BranchRepository branchRepository;
	private final UserService userService;
	private final StoreRepository storeRepository;
	
	@Override
	public BranchDTO createBranch(BranchDTO branchDto) {
		User currentUser = userService.getCurrentUser();
		Store store = storeRepository.findByStoreAdminId(currentUser.getId());
		
		Branch branch = BranchMapper.toEntity(branchDto, store);
		branchRepository.save(branch);
		return BranchMapper.toDTO(branch);
	}

	@Override
	public BranchDTO updateBranch(Long id, BranchDTO branchDto) throws UserException {
		Branch existingBranch = branchRepository.findById(id).orElseThrow(() -> new UserException("Branch not exists"));
		
		existingBranch.setName(branchDto.getName());
		existingBranch.setAddress(branchDto.getAddress());
		existingBranch.setWorkingDays(branchDto.getWorkingDays());
		existingBranch.setEmail(branchDto.getEmail());
		existingBranch.setPhone(branchDto.getPhone());
		existingBranch.setOpenTime(branchDto.getOpenTime());
		existingBranch.setCloseTime(branchDto.getCloseTime());
		existingBranch.setUpdatedAt(LocalDateTime.now());
		
		Branch updatedBranch = branchRepository.save(existingBranch);
		return BranchMapper.toDTO(updatedBranch);
	}

	@Override
	public void deleteBranch(Long id) throws UserException {
		Branch existingBranch = branchRepository.findById(id).orElseThrow(() -> new UserException("Branch not exists"));
		branchRepository.delete(existingBranch);
	}

	@Override
	public List<BranchDTO> getAllBranchByStoreId(Long storeId) {
		List<Branch> branches = branchRepository.findByStoreId(storeId);
		return branches.stream().map(BranchMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public BranchDTO getBranchById(Long id) throws UserException {
		Branch existingBranch = branchRepository.findById(id).orElseThrow(() -> new UserException("Branch not exists"));
		
		return BranchMapper.toDTO(existingBranch);
	}

}
