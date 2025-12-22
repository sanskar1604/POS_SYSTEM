package com.pos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pos.domain.StoreStatus;
import com.pos.mapper.StoreMapper;
import com.pos.model.Store;
import com.pos.model.User;
import com.pos.payload.dto.StoreDTO;
import com.pos.payload.response.ApiResponse;
import com.pos.service.StoreService;
import com.pos.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
@Tag(name = "Store", description = "API for managing store")
public class StoreController {

	private final StoreService storeService;
	private final UserService userService;
	
	@PostMapping
	@Operation(summary = "Create Store")
	public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDto, @RequestHeader("Authorization") String jwt){
		User user = userService.getUserFromJwtToken(jwt);
		return ResponseEntity.ok(storeService.createStore(storeDto, user));
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get store by store_id")
	public ResponseEntity<StoreDTO> getStoreById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception{
		return ResponseEntity.ok(storeService.getStoreById(id));
	}
	
	@GetMapping()
	@Operation(summary = "Get all stores")
	public ResponseEntity<List<StoreDTO>> getAllStore(@RequestHeader("Authorization") String jwt) throws Exception{
		return ResponseEntity.ok(storeService.getAllStore());
	}
	
	@GetMapping("/admin")
	@Operation(summary = "Get store by admin")
	public ResponseEntity<StoreDTO> getStoreByAdmin(){
		return ResponseEntity.ok(StoreMapper.toDTO(storeService.getStoreByAdmin()));
	}
	
	@GetMapping("/employee")
	@Operation(summary = "Get store by employee")
	public ResponseEntity<StoreDTO> getStoreByEmployee() throws Exception{
		return ResponseEntity.ok(storeService.getStoreByEmployee());
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Update store by store_id")
	public ResponseEntity<StoreDTO> updateStore(@PathVariable Long id, @RequestBody StoreDTO storeDto) throws Exception{
		return ResponseEntity.ok(storeService.updateStore(id, storeDto));
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete store by store_id")
	public ResponseEntity<ApiResponse> deleteStore(@PathVariable Long id){
		storeService.deleteStore(id);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Store deleted successfully");
		return ResponseEntity.ok(apiResponse);
	}
	
	
	@PutMapping("/{id}/moderate")
	@Operation(summary = "Moderate Store")
	public ResponseEntity<StoreDTO> moderateStore(@PathVariable Long id, @RequestParam StoreStatus status) throws Exception{
		return ResponseEntity.ok(storeService.moderateStore(id, status));
	}
}
