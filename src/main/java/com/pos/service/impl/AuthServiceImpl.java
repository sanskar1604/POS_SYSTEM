package com.pos.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pos.configuration.JwtProvider;
import com.pos.domain.UserRole;
import com.pos.exception.UserException;
import com.pos.mapper.UserMapper;
import com.pos.model.User;
import com.pos.payload.dto.UserDTO;
import com.pos.payload.response.AuthResponse;
import com.pos.repository.UserRepository;
import com.pos.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtProvider jwtProvider;
	
	private final CustomUserImplementation customUserImplementation;
	
	@Override
	public AuthResponse signup(UserDTO userDto) throws Exception {
		User user = userRepository.findByEmail(userDto.getEmail());
		
		if(user != null) {
			throw new UserException("Email id already registered");
		}
		
		if(userDto.getRole().equals(UserRole.ROLE_ADMIN)) {
			throw new UserException("Role admin is not allowed !");
		}
		
		User newUser = new User();
		newUser.setEmail(userDto.getEmail());
		newUser.setFullName(userDto.getFullName());
		newUser.setRole(userDto.getRole());
		newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
		newUser.setPhone(userDto.getPhone());
		newUser.setCreatedAt(LocalDateTime.now());
		newUser.setLastLogin(LocalDateTime.now());
		
		User savedUser = userRepository.save(newUser);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Registered successfully");
		authResponse.setUser(UserMapper.toDto(savedUser));
		return authResponse;
	}

	@Override
	public AuthResponse login(UserDTO userDto) {
		String email = userDto.getEmail();
		String password = userDto.getPassword();
		Authentication authentication = authenticate(email, password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		String role = authorities.iterator().next().getAuthority();
		
		String jwt = jwtProvider.generateToken(authentication);
		
		User user = userRepository.findByEmail(email);
		
		user.setLastLogin(LocalDateTime.now());
		
		userRepository.save(user);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Login successfully");
		authResponse.setUser(UserMapper.toDto(user));
		
		return authResponse;
	}
	
	private Authentication authenticate(String email, String password) {
		
		UserDetails userDetails = customUserImplementation.loadUserByUsername(email);
		if(userDetails == null) {
			throw new UserException("Email does not exist" + email);
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new UserException("Password does not match");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
