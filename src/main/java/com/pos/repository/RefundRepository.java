package com.pos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pos.model.Refund;
import com.pos.model.User;

public interface RefundRepository extends JpaRepository<Refund, Long> {
	
	List<Refund> findByCashierIdAndCreatedAtBetween(Long id, LocalDateTime from, LocalDateTime to);
	
	List<Refund> findByCashierId(Long id);
	
	List<Refund> findByShiftReportId(Long id);
	
	List<Refund> findByBranchId(Long id);

}
