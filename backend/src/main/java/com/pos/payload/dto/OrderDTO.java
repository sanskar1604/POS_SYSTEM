package com.pos.payload.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.pos.domain.PaymentType;
import com.pos.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

	private Long id;
	private Double totalAmount;
	private LocalDateTime createdAt;
	private BranchDTO branch;
	private UserDTO cashier;
	private Customer customer;
	private Long branchId;
	private Long customerId;
	private List<OrderItemDTO> items;
	private PaymentType paymentType;
}
