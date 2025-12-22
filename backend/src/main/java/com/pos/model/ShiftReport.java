package com.pos.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ShiftReport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private LocalDateTime shiftStart;
	
	private LocalDateTime shiftEnd;
	
	private Double totalSales;
	
	private Double totalRefunds;
	
	private Double netSale;
	
	private int totalOrders;
	
	@ManyToOne
	private User cashier;
	
	@ManyToOne
	private Branch branch;
	
	@Transient
	private List<PaymentSummary> paymentSummaries;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Product> topSellingProduct;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Order> recentOrders;
	
	@OneToMany(mappedBy = "shiftReport", cascade = CascadeType.ALL)
	private List<Refund> refunds;
	
	
}
