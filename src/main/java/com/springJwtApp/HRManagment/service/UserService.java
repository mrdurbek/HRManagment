package com.springJwtApp.HRManagment.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.springJwtApp.HRManagment.entity.Role;
import com.springJwtApp.HRManagment.entity.User;
import com.springJwtApp.HRManagment.entity.enums.RoleName;
import com.springJwtApp.HRManagment.payload.ApiResponse;
import com.springJwtApp.HRManagment.payload.LoginDto;
import com.springJwtApp.HRManagment.payload.RegisterDto;
import com.springJwtApp.HRManagment.repository.RoleRepository;
import com.springJwtApp.HRManagment.repository.UserRepository;
import com.springJwtApp.HRManagment.security.JwtProvider;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	public ApiResponse addManager(RegisterDto registerDto) {
		boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
		if(existsByEmail) {
			return new ApiResponse("This email already existed" , false);
		}
		User user = new User();
		user.setFirstname(registerDto.getFirstname());
		user.setLastname(registerDto.getLastname());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		Role role = roleRepository.findByRoleName(RoleName.ROLE_MANAGER);
		user.setRole(Collections.singleton(role));
		String emailCode = UUID.randomUUID().toString();
		user.setEmailCode(emailCode);
		verifyEmail(registerDto.getEmail(), emailCode);
		userRepository.save(user);
		return new ApiResponse("Succesfullay added" , true);
	}
	
	public ApiResponse addWorker(RegisterDto registerDto) {
		boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
		if(existsByEmail) {
			return new ApiResponse("This email already existed" , false);
		}
		User user = new User();
		user.setFirstname(registerDto.getFirstname());
		user.setLastname(registerDto.getLastname());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		Role role = roleRepository.findByRoleName(RoleName.ROLE_WORKER);
		user.setRole(Collections.singleton(role));
		String emailCode = UUID.randomUUID().toString();
		user.setEmailCode(emailCode);
		verifyEmail(registerDto.getEmail(), emailCode);
		userRepository.save(user);
		return new ApiResponse("Succesfullay added" , true);
	}
	
	public ApiResponse addDirector(RegisterDto registerDto) {
		boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
		if(existsByEmail) {
			return new ApiResponse("This email already existed" , false);
		}
		User user = new User();
		user.setFirstname(registerDto.getFirstname());
		user.setLastname(registerDto.getLastname());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		Role role = roleRepository.findByRoleName(RoleName.ROLE_DIRECTOR);
		user.setRole(Collections.singleton(role));
		String emailCode = UUID.randomUUID().toString();
		user.setEmailCode(emailCode);
		verifyEmail(registerDto.getEmail(), emailCode);
		userRepository.save(user);
		
		return new ApiResponse("Succesfullay added" , true);
	}
	
	public List<User> getManagers(){
		Role role = roleRepository.findByRoleName(RoleName.ROLE_MANAGER);
		return userRepository.findAllByRole(role);
	}
	
	public List<User> getWorker(){
		Role role = roleRepository.findByRoleName(RoleName.ROLE_WORKER);
		return userRepository.findAllByRole(role);
	}
	
	public boolean sendEmail(String sendingEmail , String emailCode) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("test@gmail.com");
			message.setSubject("auth of user");
			message.setTo(sendingEmail);
			message.setText("Emailni tasdiqlang <a href='http://localhost:8080/api/auth/verifyEmail?emailCode="+emailCode+"&sendingEmail"+sendingEmail+">");
			javaMailSender.send(message);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public ApiResponse verifyEmail(String emailCode , String sendingEmail) {
		Optional<User> optionalUser = userRepository.findByEmail(sendingEmail);
		if(optionalUser.isEmpty()) {
			return new ApiResponse("User is not found" , false);
		}
		User user = optionalUser.get();
		user.setEnabled(true);
		user.setEmailCode(null);
		userRepository.save(user);
		return new ApiResponse("Succesfully" , true);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByEmail(username);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}
	    throw new UsernameNotFoundException(username+" not found") ;
	}
	
}
