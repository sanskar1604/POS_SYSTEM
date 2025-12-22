package com.pos.payload.dto;

import java.time.LocalDateTime;

import com.pos.domain.PaymentType;
import com.pos.model.ShiftReport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefundDTO {

	private Long id;
	private OrderDTO order;
	private Long orderId;
	private String reason;
	private Double amount;
	private ShiftReport shiftReport;
	private Long shiftReportId;
	private UserDTO cashier;
	private String cashierName;
	private BranchDTO branch;
	private Long branchId;
	private PaymentType paymentType;
	private LocalDateTime createdAt;
}
