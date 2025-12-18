package com.pos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.exception.UserException;
import com.pos.mapper.UserMapper;
import com.pos.model.User;
import com.pos.payload.dto.UserDTO;
import com.pos.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "API for managing users")
public class UserController {

	private final UserService userService;
	
	@GetMapping("/profile")
	@Operation(summary = "Get user profile")
	public ResponseEntity<UserDTO> getUserProfile(@RequestHeader("Authorization") String jwt){
		User user = userService.getUserFromJwtToken(jwt);
		return ResponseEntity.ok(UserMapper.toDto(user));
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get user by user_id")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
		User user = userService.getUserById(id);
		return ResponseEntity.ok(UserMapper.toDto(user));
	}
}
