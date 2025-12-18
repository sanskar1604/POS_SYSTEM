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
		
		return userDto;
	}
}
