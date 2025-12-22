package com.pos.service;

import java.util.List;

import com.pos.model.Customer;

public interface CustomerService {

	Customer createCustomer(Customer customer);
	
	Customer updateCustomer(Long customerId, Customer customer) throws Exception;
	
	void deleteCustomer(Long customerId) throws Exception;
	
	Customer getCustomer(Long customerId) throws Exception;
	
	List<Customer> getAllCustomer();
	
	List<Customer> serachCustomer(String keyword);
}
