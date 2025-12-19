package com.pos.payload.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.pos.model.Store;
import com.pos.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchDTO {

	private Long id;
	private String name;
	private String email;
	private String address;
	private String phone;
	private List<String> workingDays;
	private LocalTime openTime;
	private LocalTime closeTime;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private StoreDTO store;
	private Long storeId;
	private UserDTO manager;
}
