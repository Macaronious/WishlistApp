package com.Ammar.wishlistapp.repositories;

import com.Ammar.wishlistapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}