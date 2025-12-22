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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pos.domain.UserRole;
import com.pos.model.User;
import com.pos.payload.dto.UserDTO;
import com.pos.payload.response.ApiResponse;
import com.pos.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee", description = "API for managing employee")
public class EmployeeController {

	private final EmployeeService employeeService;
	
	@PostMapping("/store/{storeId}")
	@Operation(summary = "Create store employee")
	public ResponseEntity<UserDTO> createStoreEmployee(@PathVariable Long storeId, @RequestBody UserDTO userDto) throws Exception{
		return ResponseEntity.ok(employeeService.createStoreEmployee(userDto, storeId));
	}
	
	@PostMapping("/branch/{branchId}")
	@Operation(summary = "Create branch employee")
	public ResponseEntity<UserDTO> createBranchEmployee(@PathVariable Long branchId, @RequestBody UserDTO userDto) throws Exception{
		return ResponseEntity.ok(employeeService.createBranchEmployee(userDto, branchId));
	}
	
	@PutMapping("/{employeeId}")
	@Operation(summary = "Update employee by employee_id")
	public ResponseEntity<UserDTO> updateEmployee(@PathVariable Long employeeId, @RequestBody UserDTO userDto) throws Exception{
		return ResponseEntity.ok(employeeService.updateEmployee(employeeId, userDto));
	}
	
	@DeleteMapping("/{employeeId}")
	@Operation(summary = "Delete employee by employee_id")
	public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable Long employeeId) throws Exception{
		employeeService.deleteEmployee(employeeId);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Employee deleted successfully");
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/store/{id}")
	@Operation(summary = "Get store employee")
	public ResponseEntity<List<UserDTO>> getStoreEmployee(@PathVariable Long id, @RequestParam(required = false) UserRole userRole) throws Exception{
		return ResponseEntity.ok(employeeService.findStoreEmployee(id, userRole));
	}
	
	@GetMapping("/branch/{id}")
	@Operation(summary = "Get branch employee")
	public ResponseEntity<List<UserDTO>> getBranchEmployee(@PathVariable Long id, @RequestParam(required = false) UserRole userRole) throws Exception{
		return ResponseEntity.ok(employeeService.findBranchEmployee(id, userRole));
	}
}
