package com.pos.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pos.domain.PaymentType;
import com.pos.mapper.ShiftReportMapper;
import com.pos.model.Branch;
import com.pos.model.Order;
import com.pos.model.OrderItem;
import com.pos.model.PaymentSummary;
import com.pos.model.Product;
import com.pos.model.Refund;
import com.pos.model.ShiftReport;
import com.pos.model.User;
import com.pos.payload.dto.ShiftReportDTO;
import com.pos.repository.OrderRepository;
import com.pos.repository.RefundRepository;
import com.pos.repository.ShiftReportRepository;
import com.pos.repository.UserRepository;
import com.pos.service.ShiftReportService;
import com.pos.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShiftReportServiceImpl implements ShiftReportService {
	
	private final ShiftReportRepository shiftReportRepository;
	private final UserService userService;
	private final RefundRepository refundRepository;
	private final OrderRepository orderRepository;
	private final UserRepository userRepository;

	@Override
	public ShiftReportDTO startShift() throws Exception {
		
		User currentUser = userService.getCurrentUser();
		LocalDateTime shiftStart = LocalDateTime.now();
		LocalDateTime startOfDay = shiftStart.withHour(0).withMinute(0).withSecond(0);
		LocalDateTime endOfDay = shiftStart.withHour(23).withMinute(59).withSecond(59);
		
		Optional<ShiftReport> existing = shiftReportRepository.findByCashierAndShiftStartBetween(currentUser, startOfDay, endOfDay);
		
		if(existing.isPresent()) {
			throw new Exception("Shift already start");
		}
		
		Branch branch = currentUser.getBranch();
		
		ShiftReport shiftReport = ShiftReport.builder()
				.cashier(currentUser)
				.shiftStart(shiftStart)
				.branch(branch)
				.build();
		
		return ShiftReportMapper.toDTO(shiftReportRepository.save(shiftReport));
	}

	@Override
	public ShiftReportDTO endShift() throws Exception {
		
		User currentUser = userService.getCurrentUser();
		LocalDateTime shiftEnd = LocalDateTime.now();
		ShiftReport shiftReport = shiftReportRepository
				.findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(currentUser)
				.orElseThrow(() -> new Exception("Shift not found"));
		
		shiftReport.setShiftEnd(shiftEnd);
		
		List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(currentUser.getId(), 
				shiftReport.getShiftStart(), shiftReport.getShiftEnd());
		
		double totalRefunds = refunds.stream()
				.mapToDouble(refund -> refund.getAmount() != null?
						refund.getAmount():0.0).sum();
		
		List<Order> orders = orderRepository.findByCashierAndCreatedAtBetween(currentUser, 
				shiftReport.getShiftStart(), shiftReport.getShiftStart());
		
		double totalSales = orders.stream().mapToDouble(Order::getTotalAmount).sum();
		
		int totalOrder = orders.size();
		
		double netSales = totalSales-totalRefunds;
		
		shiftReport.setTotalSales(totalSales);
		shiftReport.setTotalRefunds(totalRefunds);
		shiftReport.setTotalOrders(totalOrder);
		shiftReport.setNetSale(netSales);
		shiftReport.setRecentOrders(getRecentOrders(orders));
		shiftReport.setTopSellingProduct(getTopSellingProducts(orders));
		shiftReport.setPaymentSummaries(getPaymentSummaries(orders, totalSales));
		shiftReport.setRefunds(refunds);
		
		return ShiftReportMapper.toDTO(shiftReportRepository.save(shiftReport));
	}

	@Override
	public ShiftReportDTO getShiftReportById(Long shiftReportId) throws Exception {
		ShiftReport report = shiftReportRepository.findById(shiftReportId).orElseThrow(() -> new Exception("Shift report not found...."));
		return ShiftReportMapper.toDTO(report);
	}

	@Override
	public List<ShiftReportDTO> getAllShiftReport() {
		List<ShiftReport> reports = shiftReportRepository.findAll();
		return reports.stream().map(ShiftReportMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public List<ShiftReportDTO> getShiftReportByBranchId(Long branchId) {
		List<ShiftReport> reports = shiftReportRepository.findByBranchId(branchId);
		return reports.stream().map(ShiftReportMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public List<ShiftReportDTO> getShiftReportByCashierId(Long cashierId) {
		List<ShiftReport> reports = shiftReportRepository.findByCashierId(cashierId);
		return reports.stream().map(ShiftReportMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public ShiftReportDTO getCurrentShiftProgress() throws Exception {
		User currentUser = userService.getCurrentUser();
		ShiftReport shiftReport = shiftReportRepository.findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(currentUser).orElseThrow(() -> new Exception("No active shift found for cashier"));
		
		LocalDateTime now = LocalDateTime.now();
		List<Order> orders = orderRepository.findByCashierAndCreatedAtBetween(currentUser, shiftReport.getShiftStart(), now);
		
		List<Refund> refunds = refundRepository.findByCashierIdAndCreatedAtBetween(currentUser.getId(), 
				shiftReport.getShiftStart(), now);
		
		double totalRefunds = refunds.stream()
				.mapToDouble(refund -> refund.getAmount() != null?
						refund.getAmount():0.0).sum();
		
		double totalSales = orders.stream().mapToDouble(Order::getTotalAmount).sum();
		
		int totalOrder = orders.size();
		
		double netSales = totalSales-totalRefunds;
		
		shiftReport.setTotalSales(totalSales);
		shiftReport.setTotalRefunds(totalRefunds);
		shiftReport.setTotalOrders(totalOrder);
		shiftReport.setNetSale(netSales);
		shiftReport.setRecentOrders(getRecentOrders(orders));
		shiftReport.setTopSellingProduct(getTopSellingProducts(orders));
		shiftReport.setPaymentSummaries(getPaymentSummaries(orders, totalSales));
		shiftReport.setRefunds(refunds);
		
		return ShiftReportMapper.toDTO(shiftReportRepository.save(shiftReport));
	}

	@Override
	public ShiftReportDTO getShiftByCashierAndDate(Long cashierId, LocalDateTime date) throws Exception {
		
		User cashier = userRepository.findById(cashierId).orElseThrow(() -> new Exception("Cashier not found with given id: " + cashierId));
		
		LocalDateTime start = date.withHour(0).withMinute(0).withSecond(0);
		LocalDateTime end = date.withHour(23).withMinute(59).withSecond(59);
		
		ShiftReport report = shiftReportRepository.findByCashierAndShiftStartBetween(cashier, start, end)
				.orElseThrow(() -> new Exception("Shift report not found for date"));
		
		return ShiftReportMapper.toDTO(report);
	}
	
	// Helper Methods
	
	private List<PaymentSummary> getPaymentSummaries(List<Order> orders, double totalSales) {
		Map<PaymentType, List<Order>> grouped = orders.stream().collect(
				Collectors.groupingBy(order -> order.getPaymentType()!=null?order.getPaymentType():PaymentType.CASH));
		
		List<PaymentSummary> summaries = new ArrayList<>();
		
		for(Map.Entry<PaymentType, List<Order>> entry: grouped.entrySet()) {
			double amount = entry.getValue().stream().mapToDouble(Order::getTotalAmount).sum();
			
			int transactions = entry.getValue().size();
			
			double percentage = (amount/totalSales)*100;
			
			PaymentSummary ps = new PaymentSummary();
			ps.setType(entry.getKey());
			ps.setTotalAmount(amount);
			ps.setTransactionCount(transactions);
			ps.setPercentage(percentage);
			
			summaries.add(ps);
		}
		return summaries;
	}

	private List<Product> getTopSellingProducts(List<Order> orders) {
		Map<Product, Integer> productSalesMap = new HashMap<>();
		
		for(Order order: orders) {
			for(OrderItem item: order.getItems()) {
				Product product = item.getProduct();
				productSalesMap.put(product, productSalesMap.getOrDefault(product, 0)+item.getQuantity());
			}
		}
		return productSalesMap.entrySet().stream()
				.sorted((a,b) -> b.getValue().compareTo(a.getValue()))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}

	private List<Order> getRecentOrders(List<Order> orders) {
		return orders.stream()
				.sorted(Comparator.comparing(Order::getCreatedAt).reversed())
				.limit(5)
				.collect(Collectors.toList());
	}

}
