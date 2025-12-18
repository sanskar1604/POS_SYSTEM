package com.pos.payload.dto;

import java.time.LocalDateTime;

import com.pos.domain.StoreStatus;
import com.pos.model.StoreContact;
import com.pos.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {

private Long id;
	
	private String brand;
	private UserDTO storeAdmin;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String descriptiom;
	private String storeType;
	private StoreStatus status;
	private StoreContact contact;
}
