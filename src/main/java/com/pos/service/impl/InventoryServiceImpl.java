package com.pos.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pos.exception.UserException;
import com.pos.mapper.InventoryMapper;
import com.pos.model.Branch;
import com.pos.model.Inventory;
import com.pos.model.Product;
import com.pos.payload.dto.InventoryDTO;
import com.pos.repository.BranchRepository;
import com.pos.repository.InventoryRepository;
import com.pos.repository.ProductRepository;
import com.pos.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

	private final InventoryRepository inventoryRepository;
	private final BranchRepository branchRepository;
	private final ProductRepository productRepository;

	@Override
	public InventoryDTO createInventory(InventoryDTO inventoryDto) {
		
		Branch branch = branchRepository.findById(inventoryDto.getBranchId()).orElseThrow(() -> new UserException("Branch not exist..."));
		Product product = productRepository.findById(inventoryDto.getProductId()).orElseThrow(() -> new UserException("Product not exist..."));
		
		Inventory inventory = InventoryMapper.toEntity(inventoryDto, branch, product);
		
		inventoryRepository.save(inventory);
		
		return InventoryMapper.toDTO(inventory);
	}

	@Override
	public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDto) {
		Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new UserException("Inventory not found..."));
		
		inventory.setQuantity(inventoryDto.getQuantity());
		Inventory updatedInventory = inventoryRepository.save(inventory);
		return InventoryMapper.toDTO(updatedInventory);
	}

	@Override
	public void deleteInventory(Long id) {
		Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new UserException("Inventory not found..."));
		
		inventoryRepository.delete(inventory);
		
	}

	@Override
	public InventoryDTO getInventoryById(Long id) {
		Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new UserException("Inventory not found..."));
		return InventoryMapper.toDTO(inventory);
	}

	@Override
	public InventoryDTO getInventoryByProductIdAndBranchId(Long productId, Long branchId) {
		Inventory inventory = inventoryRepository.findByProductIdAndBranchId(productId, branchId);
		return InventoryMapper.toDTO(inventory);
	}

	@Override
	public List<InventoryDTO> getAllInventoryByBranchId(Long branchId) {
		List<Inventory> inventories = inventoryRepository.findByBranchId(branchId);
		return inventories.stream().map(InventoryMapper::toDTO).collect(Collectors.toList());
	}
}
