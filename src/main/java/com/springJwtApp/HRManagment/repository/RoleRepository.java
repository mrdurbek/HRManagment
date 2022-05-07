package com.springJwtApp.HRManagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springJwtApp.HRManagment.entity.Role;
import com.springJwtApp.HRManagment.entity.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByRoleName(RoleName roleName);
}
