package com.pos.mapper;

import com.pos.model.User;
import com.pos.payload.dto.UserDTO;

public class UserMapper {

	public static UserDTO toDto(User user) {
		UserDTO userDto = new UserDTO();
		
		userDto.setEmail(user.getEmail());
		userDto.setFullName(user.getFullName());
		userDto.setRole(user.getRole());
		userDto.setId(user.getId());
		userDto.setCreatedAt(user.getCreatedAt());
		userDto.setUpdatedAt(user.getUpdatedAt());
		userDto.setPhone(user.getPhone());
		userDto.setLastLogin(user.getLastLogin());
		userDto.setBranchId(user.getBranch()!=null?user.getBranch().getId():null);
		userDto.setStoreId(user.getStore()!=null?user.getStore().getId():null);
		
		return userDto;
	}
	
	public static User toEntity(UserDTO userDto) {
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setFullName(userDto.getFullName());
		user.setRole(userDto.getRole());
		user.setCreatedAt(userDto.getCreatedAt());
		user.setUpdatedAt(userDto.getUpdatedAt());
		user.setLastLogin(userDto.getLastLogin());
		user.setPassword(userDto.getPassword());
		
		return user;
	}
}
