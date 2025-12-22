package com.pos.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pos.mapper.RefundMapper;
import com.pos.model.Branch;
import com.pos.model.Order;
import com.pos.model.Refund;
import com.pos.model.User;
import com.pos.payload.dto.RefundDTO;
import com.pos.repository.OrderRepository;
import com.pos.repository.RefundRepository;
import com.pos.service.RefundService;
import com.pos.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {
	
	private final RefundRepository refundRepository;
	private final UserService userService;
	private final OrderRepository orderRepository;

	@Override
	public RefundDTO createRefund(RefundDTO refundDto) throws Exception {
		User cashier = userService.getCurrentUser();
		
		Order order = orderRepository.findById(refundDto.getOrderId()).orElseThrow(() -> new Exception("Order not found..."));
		
		Branch branch = order.getBranch();
		
		Refund createdRefund = Refund.builder()
				.order(order)
				.cashier(cashier)
				.branch(branch)
				.reason(refundDto.getReason())
				.amount(refundDto.getAmount())
				.createdAt(refundDto.getCreatedAt())
				.build();
		
		return RefundMapper.toDTO(createdRefund);
	}

	@Override
	public List<RefundDTO> getAllRefunds() {
		List<Refund> refunds = refundRepository.findAll();
		return refunds.stream().map(RefundMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public List<RefundDTO> getRefundByCashier(Long cashierId) {
		return refundRepository.findByCashierId(cashierId).stream().map(RefundMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public List<RefundDTO> getRefundByShiftReport(Long shiftReportId) {
		return refundRepository.findByShiftReportId(shiftReportId).stream().map(RefundMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public List<RefundDTO> getRefundByCashierAndDateRange(Long cashierId, LocalDateTime startDate,
			LocalDateTime endDate) {
		return refundRepository.findByCashierIdAndCreatedAtBetween(cashierId, startDate, endDate).stream().map(RefundMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public List<RefundDTO> getRefundByBranch(Long branchId) {
		return refundRepository.findByBranchId(branchId).stream().map(RefundMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public RefundDTO getRefundById(Long refundId) throws Exception {
		Refund refund = refundRepository.findById(refundId).orElseThrow(() -> new Exception("Refund not found with id: " + refundId));
		
		return RefundMapper.toDTO(refund);
	}

	@Override
	public void deleteRefund(Long refundId) throws Exception {
		Refund refund = refundRepository.findById(refundId).orElseThrow(() -> new Exception("Refund not found with id: " + refundId));
		
		refundRepository.delete(refund);
	}

}
