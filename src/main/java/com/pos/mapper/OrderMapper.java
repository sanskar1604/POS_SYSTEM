package com.pos.mapper;

import java.util.stream.Collectors;

import com.pos.model.Order;
import com.pos.payload.dto.OrderDTO;
import com.stripe.param.treasury.TransactionEntryListParams.OrderBy;

public class OrderMapper {

	public static OrderDTO toDTO(Order order) {
		OrderDTO orderDto = new OrderDTO();
		
		orderDto.setId(order.getId());
		orderDto.setBranchId(order.getBranch().getId());
		orderDto.setTotalAmount(order.getTotalAmount());
		orderDto.setCashier(UserMapper.toDto(order.getCashier()));
		orderDto.setCustomer(order.getCustomer());
		orderDto.setPaymentType(order.getPaymentType());
		orderDto.setCreatedAt(order.getCreatedAt());
		orderDto.setItems(order.getItems().stream()
				.map(OrderItemMapper::toDTO).collect(Collectors.toList()));
		
		return orderDto;
		
	}
}
