package com.pos.service;

import java.time.LocalDateTime;
import java.util.List;

import com.pos.payload.dto.ShiftReportDTO;

public interface ShiftReportService {

	ShiftReportDTO startShift() throws Exception;
	
	ShiftReportDTO endShift() throws Exception;
	
	ShiftReportDTO getShiftReportById(Long shiftReportId) throws Exception;
	
	List<ShiftReportDTO> getAllShiftReport();
	
	List<ShiftReportDTO> getShiftReportByBranchId(Long branchId);
	
	List<ShiftReportDTO> getShiftReportByCashierId(Long cashierId);
	
	ShiftReportDTO getCurrentShiftProgress() throws Exception;
	
	ShiftReportDTO getShiftByCashierAndDate(Long cashierId, LocalDateTime date) throws Exception;
}
