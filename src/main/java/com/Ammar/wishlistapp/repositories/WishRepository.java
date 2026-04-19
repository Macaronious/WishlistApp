package com.Ammar.wishlistapp.repositories;

import com.Ammar.wishlistapp.models.User;
import com.Ammar.wishlistapp.models.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findByUser(User user);
    List<Wish> findByUserOrderByPosition(User user);
}
