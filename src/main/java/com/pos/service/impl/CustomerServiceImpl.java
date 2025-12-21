package com.pos.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pos.model.Customer;
import com.pos.repository.CustomerRepository;
import com.pos.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerRepository customerRepository;

	@Override
	public Customer createCustomer(Customer customer) {
		
		return customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomer(Long customerId, Customer customer) throws Exception {
		Customer existingCustomer = customerRepository.findById(customerId).orElseThrow(() -> new Exception("Customer not found..."));
		
		existingCustomer.setFullName(customer.getFullName());
		existingCustomer.setEmail(customer.getEmail());
		existingCustomer.setPhone(customer.getPhone());
		
		return customerRepository.save(existingCustomer);
	}

	@Override
	public void deleteCustomer(Long customerId) throws Exception {
		Customer existingCustomer = customerRepository.findById(customerId).orElseThrow(() -> new Exception("Customer not found..."));
		
		customerRepository.delete(existingCustomer);

	}

	@Override
	public Customer getCustomer(Long customerId) throws Exception {
		return customerRepository.findById(customerId).orElseThrow(() -> new Exception("Customer not found..."));
	}

	@Override
	public List<Customer> getAllCustomer() {
		return customerRepository.findAll();
	}

	@Override
	public List<Customer> serachCustomer(String keyword) {
		
		return customerRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
	}

}
