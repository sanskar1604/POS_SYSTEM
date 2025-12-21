package com.pos.service;

import java.util.List;

import com.pos.domain.UserRole;
import com.pos.exception.UserException;
import com.pos.model.User;
import com.pos.payload.dto.UserDTO;

public interface EmployeeService {

	UserDTO createStoreEmployee(UserDTO employee, Long storeId) throws Exception;
	
	UserDTO createBranchEmployee(UserDTO employee, Long branchId) throws Exception;
	
	UserDTO updateEmployee(Long employeeId, UserDTO employeeDetails) throws Exception;
	
	void deleteEmployee(Long employeeId) throws Exception;
	
	List<UserDTO> findStoreEmployee(Long storeId, UserRole role) throws Exception;
	
	List<UserDTO> findBranchEmployee(Long branchId, UserRole role) throws Exception;
}
