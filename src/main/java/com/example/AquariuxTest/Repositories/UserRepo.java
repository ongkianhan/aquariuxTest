package com.example.AquariuxTest.Repositories;


import com.example.AquariuxTest.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface UserRepo extends JpaRepository<User, BigDecimal> {
}
