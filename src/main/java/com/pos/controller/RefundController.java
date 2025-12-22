package com.pos.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pos.payload.dto.RefundDTO;
import com.pos.payload.response.ApiResponse;
import com.pos.service.RefundService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refunds")
@Tag(name = "Refund", description = "API for managing orders")
public class RefundController {
	
	private final RefundService refundService;
	
	@PostMapping
	@Operation(summary = "Create refund")
	public ResponseEntity<RefundDTO> createRefund(@RequestBody RefundDTO refundDto) throws Exception{
		return ResponseEntity.ok(refundService.createRefund(refundDto));
	}
	
	@GetMapping
	@Operation(summary = "Get all refunds")
	public ResponseEntity<List<RefundDTO>> getAllRefunds() throws Exception{
		return ResponseEntity.ok(refundService.getAllRefunds());
	}
	
	@GetMapping("/cashier/cashierId")
	@Operation(summary = "Get refunds by cashier")
	public ResponseEntity<List<RefundDTO>> getRefundByCashier(@PathVariable Long cashierId){
		return ResponseEntity.ok(refundService.getRefundByCashier(cashierId));
	}
	
	@GetMapping("/branch/{branchId}")
	@Operation(summary = "Get refunds by branch")
	public ResponseEntity<List<RefundDTO>> getRefundByBranch(@PathVariable Long branchId) throws Exception{
		return ResponseEntity.ok(refundService.getRefundByBranch(branchId));
	}
	
	@GetMapping("/shift/{shiftId}")
	@Operation(summary = "Get refunds by shift report")
	public ResponseEntity<List<RefundDTO>> getRefundByShiftReport(@PathVariable Long shiftId) throws Exception{
		return ResponseEntity.ok(refundService.getRefundByShiftReport(shiftId));
	}
	
	@GetMapping("/cashier/{cashierId}/range")
	@Operation(summary = "Get refunds by cashier and date range")
	public ResponseEntity<List<RefundDTO>> getRefundByCashierAndDateRange(@PathVariable Long cashierId,
			@RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
			@RequestParam @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) throws Exception{
		return ResponseEntity.ok(refundService.getRefundByCashierAndDateRange(cashierId, startDate, endDate));
	}
	
	@GetMapping("/{refundId}")
	@Operation(summary = "Get refund by refund_id")
	public ResponseEntity<RefundDTO> getRefundById(@PathVariable Long refundId) throws Exception{
		return ResponseEntity.ok(refundService.getRefundById(refundId));
	}
	
	@DeleteMapping("/{refundId}")
	@Operation(summary = "Delete refund by refund_id")
	public ResponseEntity<ApiResponse> deleteRefund(@PathVariable Long refundId) throws Exception{
		refundService.deleteRefund(refundId);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Refund deleted successfully");
		return ResponseEntity.ok(apiResponse);
	}

}
