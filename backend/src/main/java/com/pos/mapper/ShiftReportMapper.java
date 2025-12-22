package com.pos.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.pos.model.Order;
import com.pos.model.Product;
import com.pos.model.Refund;
import com.pos.model.ShiftReport;
import com.pos.payload.dto.OrderDTO;
import com.pos.payload.dto.ProductDTO;
import com.pos.payload.dto.RefundDTO;
import com.pos.payload.dto.ShiftReportDTO;

public class ShiftReportMapper {

	public static ShiftReportDTO toDTO(ShiftReport shiftReport) {
		return ShiftReportDTO.builder()
				.id(shiftReport.getId())
				.shiftEnd(shiftReport.getShiftEnd())
				.shiftStart(shiftReport.getShiftStart())
				.totalSales(shiftReport.getTotalSales())
				.totalOrders(shiftReport.getTotalOrders())
				.totalRefunds(shiftReport.getTotalRefunds())
				.netSale(shiftReport.getNetSale())
				.totalOrders(shiftReport.getTotalOrders())
				.cashier(UserMapper.toDto(shiftReport.getCashier()))
				.cashierId(shiftReport.getCashier().getId())
				.branchId(shiftReport.getBranch().getId())
				.recentOrders(mapOrders(shiftReport.getRecentOrders()))
				.topSellingProduct(mapProducts(shiftReport.getTopSellingProduct()))
				.refunds(mapRefunds(shiftReport.getRefunds()))
				.paymentSummaries(shiftReport.getPaymentSummaries())
				.build();
	}

	private static List<RefundDTO> mapRefunds(List<Refund> refunds) {
		if(refunds == null || refunds.isEmpty()) {
			return null;
		}
		return refunds.stream().map(RefundMapper::toDTO).collect(Collectors.toList());
	}

	private static List<ProductDTO> mapProducts(List<Product> topSellingProduct) {
		if(topSellingProduct == null || topSellingProduct.isEmpty()) {
			return null;
		}
		return topSellingProduct.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
	}

	private static List<OrderDTO> mapOrders(List<Order> recentOrders) {
		if(recentOrders == null || recentOrders.isEmpty()) {
			return null;
		}
		return recentOrders.stream().map(OrderMapper::toDTO).collect(Collectors.toList());
	}
}
