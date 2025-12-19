package com.pos.service;

import java.util.List;

import com.pos.payload.dto.InventoryDTO;

public interface InventoryService {

	InventoryDTO createInventory(InventoryDTO inventoryDto);
	InventoryDTO updateInventory(Long id, InventoryDTO inventoryDto);
	void deleteInventory(Long id);
	InventoryDTO getInventoryById(Long id);
	InventoryDTO getInventoryByProductIdAndBranchId(Long productId, Long branchId);
	List<InventoryDTO> getAllInventoryByBranchId(Long branchId);
}
