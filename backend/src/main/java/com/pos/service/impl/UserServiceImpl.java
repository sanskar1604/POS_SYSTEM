package com.pos.service.impl;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pos.configuration.JwtProvider;
import com.pos.exception.UserException;
import com.pos.model.User;
import com.pos.repository.UserRepository;
import com.pos.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final JwtProvider jwtProvider;

	@Override
	public User getUserFromJwtToken(String token) {
		String email = jwtProvider.getEmailFromToken(token);
		User user = userRepository.findByEmail(email);
		
		if(user == null) {
			throw new UserException("Invalid token");
		}
		return user;
	}

	@Override
	public User getCurrentUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByEmail(email);
		
		if(user == null) {
			throw new UserException("User not found");
		}
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		
		User user = userRepository.findByEmail(email);
		
		if(user == null) {
			throw new UserException("User not found");
		}
		return user;
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new UserException("User not exist"));
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

}
