package com.dgeniust.jwt_project.repository;

import com.dgeniust.jwt_project.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
