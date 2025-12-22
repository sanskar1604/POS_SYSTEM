package com.pos.model;

import com.pos.domain.PaymentType;

import lombok.Data;

@Data
public class PaymentSummary {

	
	private PaymentType type;
	private Double totalAmount;
	private int transactionCount;
	private double percentage;
	
}
