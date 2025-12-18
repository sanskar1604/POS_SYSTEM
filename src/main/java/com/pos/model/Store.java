package com.pos.model;

import java.time.LocalDateTime;

import com.pos.domain.StoreStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String brand;
	
	@OneToOne
	private User storeAdmin;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private String descriptiom;
	
	private String storeType;
	
	private StoreStatus status;
	
	@Embedded
	private StoreContact contact = new StoreContact();
	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		status = StoreStatus.PENDING;
	}
	
	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
	
	
}
