package com.pos.service;

import java.util.List;

import com.pos.payload.dto.InventoryDTO;

public interface InventoryService {

	InventoryDTO createInventory(InventoryDTO inventoryDto) throws Exception;
	InventoryDTO updateInventory(Long id, InventoryDTO inventoryDto) throws Exception;
	void deleteInventory(Long id) throws Exception;
	InventoryDTO getInventoryById(Long id) throws Exception;
	InventoryDTO getInventoryByProductIdAndBranchId(Long productId, Long branchId);
	List<InventoryDTO> getAllInventoryByBranchId(Long branchId);
}
