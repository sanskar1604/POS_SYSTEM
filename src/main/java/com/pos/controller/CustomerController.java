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

import com.pos.model.Customer;
import com.pos.payload.response.ApiResponse;
import com.pos.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customer", description = "API for managing customer")
public class CustomerController {

	private final CustomerService customerService;
	
	@PostMapping
	@Operation(summary = "Create customer")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
		return ResponseEntity.ok(customerService.createCustomer(customer));
	}
	
	@PutMapping("/{customerId}")
	@Operation(summary = "Update customer by customer_id")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customer) throws Exception{
		return ResponseEntity.ok(customerService.updateCustomer(customerId, customer));
	}
	
	@GetMapping("/{customerId}")
	@Operation(summary = "Get customer by customer_id")
	public ResponseEntity<Customer> getCustomer(@PathVariable Long customerId) throws Exception{
		return ResponseEntity.ok(customerService.getCustomer(customerId));
	}
	
	@GetMapping
	@Operation(summary = "Get all customer")
	public ResponseEntity<List<Customer>> getAllCustomer() {
		return ResponseEntity.ok(customerService.getAllCustomer());
	}
	
	@DeleteMapping("{customerId}")
	@Operation(summary = "Delete customer by customer_id")
	public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable Long customerId) throws Exception {
		customerService.deleteCustomer(customerId);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Customer deleted successfully");
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/search")
	@Operation(summary = "Search customer")
	public ResponseEntity<List<Customer>> searchCustomer(@RequestParam String q) {
		return ResponseEntity.ok(customerService.serachCustomer(q));
	}
}
