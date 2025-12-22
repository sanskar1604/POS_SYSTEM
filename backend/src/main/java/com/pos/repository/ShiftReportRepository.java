package com.pos.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pos.model.ShiftReport;
import com.pos.model.User;

public interface ShiftReportRepository extends JpaRepository<ShiftReport, Long> {

	List<ShiftReport> findByBranchId(Long branchId);
	
	List<ShiftReport> findByCashierId(Long cashierId);
	
	Optional<ShiftReport> findTopByCashierAndShiftEndIsNullOrderByShiftStartDesc(User cashier);
	
	Optional<ShiftReport> findByCashierAndShiftStartBetween(User cashier, LocalDateTime start, LocalDateTime end);
}
