package com.pos.payload.dto;

import java.time.LocalDateTime;

import com.pos.domain.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Long id;
	private String fullName;
	private String email;
	private String password;
	private String phone;
	private UserRole role;
	private Long branchId;
	private Long storeId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime lastLogin;
}
