package com.pos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.pos.payload.response.ApiResponse;
import com.pos.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "API for managing orders")
public class OrderController {
	
	private final OrderService orderService;
	
	@PostMapping
	@Operation(summary = "Create Order")
	public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDto) throws Exception{
		return ResponseEntity.ok(orderService.createOrder(orderDto));
	}

	@GetMapping("/{orderId}")
	@Operation(summary = "Get order by order_id")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) throws Exception{
		return ResponseEntity.ok(orderService.getOrderById(orderId));
	}
	
	@GetMapping("/branch/{branchId}")
	@Operation(summary = "Get order by branch")
	public ResponseEntity<List<OrderDTO>> getOrderByBranch(@PathVariable Long branchId, 
			@RequestParam(required = false) Long cashierId, 
			@RequestParam(required = false) Long customerId,
			@RequestParam(required = false) PaymentType paymentType,
			@RequestParam(required = false) OrderStatus orderStatus){
		return ResponseEntity.ok(orderService.getOrderByBranch(branchId, customerId, cashierId, paymentType, orderStatus));
	}
	
	@GetMapping("/cashier/{cashierId}")
	@Operation(summary = "Get order by cashier")
	public ResponseEntity<List<OrderDTO>> getOrderByCashier(@PathVariable Long cashierId){
		return ResponseEntity.ok(orderService.getOrderByCashier(cashierId));
	}
	
	@GetMapping("/today/branch/{branchId}")
	@Operation(summary = "Get today's order")
	public ResponseEntity<List<OrderDTO>> getTodayOrder(@PathVariable Long branchId){
		return ResponseEntity.ok(orderService.getTodayOrdersByBranch(branchId));
	}
	
	@GetMapping("/customer/{customerId}")
	@Operation(summary = "Get customer order")
	public ResponseEntity<List<OrderDTO>> getCustomersOrder(@PathVariable Long customerId) throws Exception{
		return ResponseEntity.ok(orderService.getOrdersByCustomerId(customerId));
	}
	
	@GetMapping("/recent/{branchId}")
	@Operation(summary = "Get top 5 recent order")
	public ResponseEntity<List<OrderDTO>> getRecentOrder(@PathVariable Long branchId){
		return ResponseEntity.ok(orderService.getTop5RecentOrdersByBranchId(branchId));
	}
	
	@DeleteMapping("/{orderId}")
	@Operation(summary = "Delete order by order_id")
	public ResponseEntity<ApiResponse> deleteOrder(@PathVariable Long orderId) throws Exception{
		orderService.deleteOrder(orderId);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Order deleted successfully");
		return ResponseEntity.ok(apiResponse);
	}
}
