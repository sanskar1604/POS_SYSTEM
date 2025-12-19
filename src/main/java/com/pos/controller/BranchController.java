package com.pos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.payload.dto.BranchDTO;
import com.pos.payload.response.ApiResponse;
import com.pos.service.BranchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branches")
@Tag(name = "Branch", description = "API for managing Branch")
public class BranchController {

	private final BranchService branchService;
	
	@PostMapping
	@Operation(summary = "Create Branch")
	public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDto){
		return ResponseEntity.ok(branchService.createBranch(branchDto));
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get branch by branch_id")
	public ResponseEntity<BranchDTO> getBranchById(@PathVariable Long id){
		return ResponseEntity.ok(branchService.getBranchById(id));
	}
	
	@GetMapping("/store/{storeId}")
	@Operation(summary = "Get all branches by store_id")
	public ResponseEntity<List<BranchDTO>> getAllBranchesByStoreId(@PathVariable Long storeId){
		return ResponseEntity.ok(branchService.getAllBranchByStoreId(storeId));
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Update Branch by branch_id")
	public ResponseEntity<BranchDTO> updateBranch(@PathVariable Long id, @RequestBody BranchDTO branchDto){
		return ResponseEntity.ok(branchService.updateBranch(id, branchDto));
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Branch by branch_id")
	public ResponseEntity<ApiResponse> deleteBranch(@PathVariable Long id){
		branchService.deleteBranch(id);
		
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Branch deleted successfully");
		
		return ResponseEntity.ok(apiResponse);
	}
	
	
}
