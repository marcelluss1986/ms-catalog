package com.mdss.mscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdss.mscatalog.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
}
