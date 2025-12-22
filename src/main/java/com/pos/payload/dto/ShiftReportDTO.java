package com.pos.payload.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.pos.model.Branch;
import com.pos.model.Order;
import com.pos.model.PaymentSummary;
import com.pos.model.Product;
import com.pos.model.Refund;
import com.pos.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShiftReportDTO {

	private Long id;
	private LocalDateTime shiftStart;
	private LocalDateTime shiftEnd;
	private Double totalSales;
	private Double totalRefunds;
	private Double netSale;
	private int totalOrders;
	private UserDTO cashier;
	private Long cashierId;
	private Long branchId;
	private BranchDTO branch;
	private List<PaymentSummary> paymentSummaries;
	private List<ProductDTO> topSellingProduct;
	private List<OrderDTO> recentOrders;
	private List<RefundDTO> refunds;
}
