package com.springJwtApp.HRManagment.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springJwtApp.HRManagment.entity.User;
import com.springJwtApp.HRManagment.payload.ApiResponse;
import com.springJwtApp.HRManagment.payload.LoginDto;
import com.springJwtApp.HRManagment.payload.RegisterDto;
import com.springJwtApp.HRManagment.repository.UserRepository;
import com.springJwtApp.HRManagment.security.JwtProvider;
import com.springJwtApp.HRManagment.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {
	
	@Autowired
	UserService userService;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtProvider jwtProvider;
	
	@PostMapping("/register/manager")
	public ResponseEntity<?> addManager(@RequestBody RegisterDto registerDto){
		ApiResponse response = userService.addManager(registerDto);
		if(response.isResult()) {
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(401).body(response);
	}
	
	@PostMapping("/register/worker")
	public ResponseEntity<?> addWorker(@RequestBody RegisterDto registerDto){
		ApiResponse response = userService.addWorker(registerDto);
		if(response.isResult()) {
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(401).body(response);
	}
	
	@PostMapping("/register/director")
	public ResponseEntity<?> addDirector(@RequestBody RegisterDto registerDto){
		ApiResponse response = userService.addDirector(registerDto);
		if(response.isResult()) {
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(401).body(response);
	}
	
	@GetMapping("/verifyEmail")
	public ResponseEntity<?> verifyEmail(@RequestParam String emailCode , @RequestParam String sendingEmail){
		return ResponseEntity.ok("OK");
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
			Optional<User> optionalUser = userRepository.findByEmail(loginDto.getUsername());
			if(optionalUser.isEmpty()) {
				return ResponseEntity.status(404).body("Not Found");
			}
			
			String token = jwtProvider.generateToken(loginDto.getUsername(), optionalUser.get().getRole());
			return ResponseEntity.ok(token);
			
		}catch (BadCredentialsException e) {
			return ResponseEntity.status(401).body("BadCredentialsException");
		}
		
	}
}
