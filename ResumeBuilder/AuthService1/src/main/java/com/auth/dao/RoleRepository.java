package com.auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import com.auth.entity.Role;
import com.auth.entity.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
}


