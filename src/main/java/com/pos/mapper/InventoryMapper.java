package com.pos.mapper;

import com.pos.model.Branch;
import com.pos.model.Inventory;
import com.pos.model.Product;
import com.pos.payload.dto.InventoryDTO;

public class InventoryMapper {

	public static InventoryDTO toDTO(Inventory inventory) {
		InventoryDTO inDto = new InventoryDTO();
		
		inDto.setId(inventory.getId());
		inDto.setBranchId(inventory.getBranch().getId());
		inDto.setProductId(inventory.getProduct().getId());
		inDto.setProduct(ProductMapper.toDTO(inventory.getProduct()));
		inDto.setQuantity(inventory.getQuantity());
		
		return inDto;
	}
	
	public static Inventory toEntity(InventoryDTO inventoryDto, Branch branch, Product product) {
		Inventory inventory = new Inventory();
		
		inventory.setBranch(branch);
		inventory.setProduct(product);
		inventory.setQuantity(inventoryDto.getQuantity());
		
		return inventory;
	}
}
