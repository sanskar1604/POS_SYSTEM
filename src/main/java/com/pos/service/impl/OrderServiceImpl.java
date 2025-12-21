package com.pos.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pos.domain.OrderStatus;
import com.pos.domain.PaymentType;
import com.pos.payload.dto.OrderDTO;
import com.pos.repository.OrderRepository;
import com.pos.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
	
	private final OrderRepository orderRepository;

	@Override
	public OrderDTO createOrder(OrderDTO orderDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderDTO getOrderById(Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDTO> getOrderByBranch(Long branchId, Long customerId, Long cashierId, PaymentType paymentType,
			OrderStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDTO> getOrderByCashier(Long cashierId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOrder(Long orderId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<OrderDTO> getTodayOrdersByBranch(Long branchId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDTO> getTop5RecentOrdersByBranchId(Long branchId) {
		// TODO Auto-generated method stub
		return null;
	}

}
