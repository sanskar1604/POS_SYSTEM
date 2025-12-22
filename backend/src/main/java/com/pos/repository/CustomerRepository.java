package com.pos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pos.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String fullName, String email);
	
	
}
