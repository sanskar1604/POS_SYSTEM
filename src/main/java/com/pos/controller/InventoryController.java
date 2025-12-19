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

import com.pos.payload.dto.InventoryDTO;
import com.pos.payload.response.ApiResponse;
import com.pos.service.InventoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventories")
@Tag(name = "Inventory", description = "API for managing Inventory")
public class InventoryController {
	
	private final InventoryService inventoryService;
	
	@PostMapping
	@Operation(summary = "Create Inventory")
	public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventoryDto){
		return ResponseEntity.ok(inventoryService.createInventory(inventoryDto));
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Update Inventory")
	public ResponseEntity<InventoryDTO> updateInventory(@PathVariable Long id, @RequestBody InventoryDTO inventoryDto){
		return ResponseEntity.ok(inventoryService.updateInventory(id, inventoryDto));
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Inventory")
	public ResponseEntity<ApiResponse> deleteInventory(@PathVariable Long id){
		inventoryService.deleteInventory(id);
		
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Inventory deleted successfully");
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/branch/{branchId}/product/{productId}")
	@Operation(summary = "Get all inventory by branch_id and product_id")
	public ResponseEntity<InventoryDTO> getInventoryByProductAndBranchId(@PathVariable Long productId, @PathVariable Long branchId){
		return ResponseEntity.ok(inventoryService.getInventoryByProductIdAndBranchId(productId, branchId));
	}
	
	@GetMapping("/branch/{branchId}")
	@Operation(summary = "Get all inventory by branch_id")
	public ResponseEntity<List<InventoryDTO>> getInventoryByBranchId(@PathVariable Long branchId){
		return ResponseEntity.ok(inventoryService.getAllInventoryByBranchId(branchId));
	}

}
