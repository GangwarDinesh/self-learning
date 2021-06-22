package com.demo.app.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.app.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
