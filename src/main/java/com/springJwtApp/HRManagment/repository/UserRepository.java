package com.springJwtApp.HRManagment.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springJwtApp.HRManagment.entity.Role;
import com.springJwtApp.HRManagment.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	boolean existsByEmail(String email);
	List<User> findAllByRole(Role role);
	
	Optional<User> findByEmail(String email);
}
