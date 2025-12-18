package com.pos.service;

import com.pos.payload.dto.UserDTO;
import com.pos.payload.response.AuthResponse;

public interface AuthService {

	AuthResponse signup(UserDTO userDto) throws Exception;
	AuthResponse login(UserDTO userDto);
}
