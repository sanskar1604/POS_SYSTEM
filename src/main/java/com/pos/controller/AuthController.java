package com.pos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.payload.dto.UserDTO;
import com.pos.payload.response.AuthResponse;
import com.pos.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for authentication")
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/signup")
	@Operation(summary = "Signup handler")
	public ResponseEntity<AuthResponse> signupHandler(@RequestBody UserDTO userDto) throws Exception{
		return ResponseEntity.ok(authService.signup(userDto));
	}
	
	@PostMapping("/login")
	@Operation(summary = "Login handler")
	public ResponseEntity<AuthResponse> loginHandler(@RequestBody UserDTO userDto){
		return ResponseEntity.ok(authService.login(userDto));
	}
	
	
}
