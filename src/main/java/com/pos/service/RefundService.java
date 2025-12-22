package com.pos.service;

import java.time.LocalDateTime;
import java.util.List;

import com.pos.payload.dto.RefundDTO;

public interface RefundService {

	RefundDTO createRefund(RefundDTO refundDto) throws Exception;
	
	List<RefundDTO> getAllRefunds();
	
	List<RefundDTO> getRefundByCashier(Long cashierId);
	
	List<RefundDTO> getRefundByShiftReport(Long shiftReportId);
	
	List<RefundDTO> getRefundByCashierAndDateRange(Long cashierId, LocalDateTime startDate, LocalDateTime endDate);
	
	List<RefundDTO> getRefundByBranch(Long branchId);
	
	RefundDTO getRefundById(Long refundId) throws Exception;
	
	void deleteRefund(Long refundId) throws Exception;
	
}
