package com.pos.service;

import java.util.List;

import com.pos.domain.OrderStatus;
import com.pos.domain.PaymentType;
import com.pos.payload.dto.OrderDTO;

public interface OrderService {

	OrderDTO createOrder(OrderDTO orderDto);
	
	OrderDTO getOrderById(Long orderId);
	
	List<OrderDTO> getOrderByBranch(Long branchId, Long customerId, Long cashierId, PaymentType paymentType, OrderStatus status);
	
	List<OrderDTO> getOrderByCashier(Long cashierId);
	
	void deleteOrder(Long orderId);
	
	List<OrderDTO> getTodayOrdersByBranch(Long branchId);
	
	List<OrderDTO> getOrdersByCustomerId(Long customerId);
	
	List<OrderDTO> getTop5RecentOrdersByBranchId(Long branchId);
}
