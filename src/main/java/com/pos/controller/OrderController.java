package com.pos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pos.domain.OrderStatus;
import com.pos.domain.PaymentType;
import com.pos.payload.dto.OrderDTO;
import com.pos.service.OrderService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "API for managing orders")
public class OrderController {
	
	private final OrderService orderService;
	
	@PostMapping
	public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDto) throws Exception{
		return ResponseEntity.ok(orderService.createOrder(orderDto));
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) throws Exception{
		return ResponseEntity.ok(orderService.getOrderById(orderId));
	}
	
	@GetMapping("/branch/{branchId}")
	public ResponseEntity<List<OrderDTO>> getOrderByBranch(@PathVariable Long branchId, 
			@RequestParam(required = false) Long cashierId, 
			@RequestParam(required = false) Long customerId,
			@RequestParam(required = false) PaymentType paymentType,
			@RequestParam(required = false) OrderStatus orderStatus){
		return ResponseEntity.ok(orderService.getOrderByBranch(branchId, customerId, cashierId, paymentType, orderStatus));
	}
	
	@GetMapping("/cashier/{cashierId}")
	public ResponseEntity<List<OrderDTO>> getOrderByCashier(@PathVariable Long cashierId){
		return ResponseEntity.ok(orderService.getOrderByCashier(cashierId));
	}
	
	@GetMapping("/today/branch/{branchId}")
	public ResponseEntity<List<OrderDTO>> getTodayOrder(@PathVariable Long branchId){
		return ResponseEntity.ok(orderService.getTodayOrdersByBranch(branchId));
	}
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<OrderDTO>> getCustomersOrder(@PathVariable Long customerId) throws Exception{
		return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
	}
	
	@GetMapping("/recent/{branchId}")
	public ResponseEntity<List<OrderDTO>> getRecentOrder(@PathVariable Long branchId){
		return ResponseEntity.ok(orderService.getTop5RecentOrdersByBranchId(branchId));
	}
}
