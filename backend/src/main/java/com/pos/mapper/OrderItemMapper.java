package com.pos.mapper;

import com.pos.model.OrderItem;
import com.pos.payload.dto.OrderItemDTO;

public class OrderItemMapper {

	public static OrderItemDTO toDTO(OrderItem item) {
		OrderItemDTO itemDto = new OrderItemDTO();
		
		itemDto.setId(item.getId());
		itemDto.setProductId(item.getProduct().getId());
		itemDto.setQuantity(item.getQuantity());
		itemDto.setPrice(item.getPrice());
		itemDto.setProduct(ProductMapper.toDTO(item.getProduct()));
		
		return itemDto;
	}
}
